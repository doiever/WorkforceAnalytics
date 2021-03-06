package com.alliancedata.workforceanalytics.models;

import com.alliancedata.workforceanalytics.Constants;
import com.alliancedata.workforceanalytics.Utilities;
import com.almworks.sqlite4java.*;
import javafx.scene.control.Alert;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.StringJoiner;

/**
 *  Implementation of a Database Handler.
 *  The Database Handler is a per-Session object which handles database interaction.
 */
public class DatabaseHandler implements Serializable
{
	// region Fields
	private File databaseFile = null;
	private boolean hasHeadcountData = false;
	// endregion

	// region Constructors
	public DatabaseHandler(@NotNull String fileName)
	{
		String databaseFileName = fileName;

		// Append sqlite3 extension if necessary:
		if (!Utilities.getFileExtension(databaseFileName).equalsIgnoreCase("sqlite3"))
		{
			databaseFileName += ".sqlite3";
		}

		File databaseFile = tryCreateFile(databaseFileName);

		if (databaseFile != null && databaseFile.exists())
		{
			this.databaseFile = databaseFile;
		}
		else
		{
			// TODO: databaseFile was unable to be created.
		}
	}
	// endregion

	// region Methods
	@NotNull
	public File getDatabaseFile()
	{
		return this.databaseFile;
	}

	/**
	 * Writes schema and data to the handler's database file.
	 * @param headcountColumns  The list of (column name, column type) pairs representing columns for the Headcount table.
	 * @param activityColumns   The list of (column name, column type) pairs representing columns for the Activity table.
	 * @param headcountData     The list of rows (as lists) representing data for the Headcount table.
	 * @param activityData      The list of rows (as lists) representing data for the Activity table.
	 * @return {@code true} if tables were successfully created and populated; {@code false} otherwise.
	 */
	public boolean createDatabase(@NotNull LinkedList<String> headcountColumns, @NotNull LinkedList<String> activityColumns,
	                              @NotNull LinkedList<LinkedList<String>> headcountData,
	                              @NotNull LinkedList<LinkedList<String>> activityData)
	{
		// Build schema creation and data population queries:
		String createSchemaQuery = buildCreateSchemaQuery(headcountColumns, activityColumns);
		String populateQuery = buildPopulateQuery(headcountData, activityData);

		// Execute queries to create and populate tables:
		boolean schemaCreated = executeLameQuery(createSchemaQuery);
		boolean populated = executeLameQuery(populateQuery);

		return schemaCreated && populated;
	}

	public boolean executeLameQuery(@NotNull String query)
	{
		Boolean success = null;
		SQLiteQueue queue = new SQLiteQueue(this.getDatabaseFile());

		try
		{
			queue.start();

			SQLiteJob<Boolean> queryJob = new SQLiteJob<Boolean>() {
				@Override
				protected Boolean job(SQLiteConnection connection) throws Throwable {
					try
					{
						connection.exec(query);
					}
					catch (SQLiteException ex)
					{
						Utilities.showTextAreaDialog(Alert.AlertType.ERROR, "SQL Error", null,
							null, ex.getMessage());
						return false;
					}

					return true;
				}
			};

			success = queue.execute(queryJob).complete();
		}
		catch (IllegalStateException ex)
		{
			// TODO
			ex.printStackTrace();
			success = false;
		}
		finally
		{
			try
			{
				queue.stop(true).join();
			}
			catch (InterruptedException ex)
			{
				Utilities.showTextAreaDialog(Alert.AlertType.ERROR, "Concurrency Error", null,
					null, ex.getMessage());
				ex.printStackTrace();
			}
			finally
			{
				queue.stop(false);
			}
		}

		return success;
	}

	@NotNull
	public LinkedHashSet<String> getColumnNames(@NotNull String tableName)
	{
		final String query = "PRAGMA table_info(" + tableName + ");";
		LinkedHashSet<String> columns = new LinkedHashSet<>();
		SQLiteQueue queue = null;

		SQLiteJob<LinkedHashSet<String>> getColumnNamesJob = new SQLiteJob<LinkedHashSet<String>>() {
			@Override
			protected LinkedHashSet<String> job(SQLiteConnection connection) throws Throwable {
				final LinkedHashSet<String> finalColumns = new LinkedHashSet<>();
				SQLiteStatement statement = connection.prepare(query);

				while (statement.step())
				{
					for (int i = 0; i < statement.columnCount(); i++)
					{
						finalColumns.add(statement.columnString(1));
					}
				}

				return finalColumns;
			}
		};

		try
		{
			queue = new SQLiteQueue(this.getDatabaseFile());
			queue.start();

			columns = queue.execute(getColumnNamesJob).complete();
		}
		finally
		{
			if (queue != null)
			{
				queue.stop(true);
			}
		}

		return columns;
	}

	@NotNull
	public LinkedHashSet<String> getTableNames()
	{
		final String query = "SELECT name FROM sqlite_master WHERE type='table';";
		LinkedHashSet<String> tableNames = new LinkedHashSet<>();
		SQLiteQueue queue = null;

		SQLiteJob<LinkedHashSet<String>> getTableNamesJob = new SQLiteJob<LinkedHashSet<String>>() {
			@Override
			protected LinkedHashSet<String> job(SQLiteConnection connection) throws Throwable {
				final LinkedHashSet<String> finalTableNames = new LinkedHashSet<>();
				SQLiteStatement statement = connection.prepare(query);

				while (statement.step())
				{
					for (int i = 0; i < statement.columnCount(); i++)
					{
						finalTableNames.add(statement.columnString(0));
					}
				}

				return finalTableNames;
			}
		};

		try
		{
			queue = new SQLiteQueue(this.getDatabaseFile());
			queue.start();

			tableNames = queue.execute(getTableNamesJob).complete();
		}
		finally
		{
			if (queue != null)
			{
				queue.stop(true);
			}
		}

		// Remove sqlite_sequence from table list:
		if (tableNames.contains("sqlite_sequence"))
		{
			tableNames.remove("sqlite_sequence");
		}

		return tableNames;
	}

	/**
	 * Executes a query against the specified session's database and returns the results as a list of lists of Strings.
	 * @param session The session from which the target database is obtained and queried against.
	 * @param query The query to execute.
	 * @return A {@code LinkedList<LinkedList<String>>} containing data.
	 */
	@NotNull
	public static LinkedList<LinkedList<String>> executeQuery(@NotNull Session session, @NotNull String query)
	{
		LinkedList<LinkedList<String>> data = new LinkedList<>();
		SQLiteQueue queue = null;

		SQLiteJob<LinkedList<LinkedList<String>>> queryJob = new SQLiteJob<LinkedList<LinkedList<String>>>() {
			@Override
			protected LinkedList<LinkedList<String>> job(SQLiteConnection connection) throws Throwable {
				final LinkedList<LinkedList<String>> finalData = new LinkedList<>();
				SQLiteStatement statement = connection.prepare(query);

				while (statement.step())
				{
					LinkedList<String> row = new LinkedList<>();

					for (int i = 0; i < statement.columnCount(); i++)
					{
						row.add(statement.columnString(i));
					}

					finalData.add(row);
				}

				return finalData;
			}
		};

		try
		{
			queue = new SQLiteQueue(session.getDatabaseHandler().getDatabaseFile());
			queue.start();

			data = queue.execute(queryJob).complete();
		}
		finally
		{
			if (queue != null)
			{
				queue.stop(true);
			}
		}

		return data;
	}

	/**
	 * Attempts to create the specified file.
	 * @param fileName The full path of the file to create.
	 * @return An {@code File} object at the specified location.
	 */
	private static File tryCreateFile(@NotNull String fileName)
	{
		boolean fileCreated = false;

		try
		{
			File file = new File(fileName);
			fileCreated = file.createNewFile();
			return file;
		}
		catch (IOException ex)
		{
			Utilities.showTextAreaDialog(Alert.AlertType.ERROR, "File Error", null,
				"Unable to write to disk.", ex.getMessage());
			return null;
		}
		catch (SecurityException ex)
		{
			Utilities.showTextAreaDialog(Alert.AlertType.ERROR, "Security Error", null,
				"Unable to write to disk.", ex.getMessage());
			return null;
		}
	}

	/**
	 * Builds a SQL query to create the database's tables. This will build a {@code DROP TABLE/CREATE TABLE} query (instead of an
	 * {@code ALTER TABLE} query), so be sure you're fine with droppping all table data.
	 * @param headcountColumns  The list of (column name, column type) pairs representing columns for the Headcount table.
	 * @param activityColumns   The list of (column name, column type) pairs representing columns for the Activity table.
	 * @return A SQL query which, when executed, will create the Headcount and Activity tables.
	 */
	private String buildCreateSchemaQuery(@NotNull LinkedList<String> headcountColumns, @NotNull LinkedList<String> activityColumns)
	{
		// Activities depend on Headcount data (if present); otherwise Activities will not relate to any other table.
		this.hasHeadcountData = headcountColumns.size() > 0;
		StringBuilder queryBuilder = new StringBuilder("DROP TABLE IF EXISTS Activity;\n");
		queryBuilder.append("DROP TABLE IF EXISTS Headcount;\n");

		// Build Headcount CREATE TABLE query if necessary:
		if (this.hasHeadcountData)
		{
			StringJoiner joiner = new StringJoiner(", ");
			queryBuilder.append("\n\nCREATE TABLE Headcount(");

			for (String column : headcountColumns)
			{
				String columnDefinition = "";

				if (column.equalsIgnoreCase("ID"))
				{
					// Headcount.ID is the unique autoinc primary key:
					columnDefinition = String.format("\n\t\"%s\" %s %s", column, "INTEGER", "" /* Constants.PRIMARY_KEY_ATTRIBUTES */);
				}
				else
				{
					// All other fields are non-unique and nullable:
					columnDefinition = String.format("\n\t\"%s\" %s", column, "TEXT");
				}

				joiner.add(columnDefinition);
			}

			queryBuilder.append(joiner.toString());
			queryBuilder.append(");");
		}

		// Build Activity CREATE TABLE query if necessary:
		if (activityColumns.size() > 0)
		{
			StringJoiner joiner = new StringJoiner(", ");
			queryBuilder.append("\n\nCREATE TABLE Activity(");

			for (String column : activityColumns)
			{
				String columnDefinition = "";

				if (column.equalsIgnoreCase("ID") && this.hasHeadcountData)
				{
					// When Headcount data is present, Activity.ID becomes Activity.HeadcountID,
					// so we need to 1) add a traditional ID field for the Activity table, and
					// 2) create foreign key Activity.Headcount ID -> Headcount.ID:

					columnDefinition = String.format("\n\t\"%s\" %s %s,", "ID", "INTEGER", Constants.PRIMARY_KEY_ATTRIBUTES);

					String foreignKeyAttributes = "REFERENCES Headcount(ID)";
					columnDefinition += String.format("\n\t\"%s\" %s %s", "Headcount ID", "INTEGER", foreignKeyAttributes);
				}
				else
				{
					columnDefinition = String.format("\n\t\"%s\" %s", column, "TEXT");
				}

				joiner.add(columnDefinition);
			}

			queryBuilder.append(joiner.toString());
			queryBuilder.append(");");
		}

		return queryBuilder.toString();
	}

	private String buildPopulateQuery(@NotNull LinkedList<LinkedList<String>> headcountData, @NotNull LinkedList<LinkedList<String>> activityData)
	{
		StringBuilder queryBuilder = new StringBuilder("BEGIN;\n");

		// Build an INSERT query for headcount data:
		if (headcountData.size() > 0)
		{
			queryBuilder.append("\nINSERT INTO Headcount VALUES(");
			StringJoiner rowJoiner = new StringJoiner(");\nINSERT INTO Headcount VALUES(");

			// Collect each data item for each row:
			for (LinkedList<String> row : headcountData)
			{
				StringJoiner valueJoiner = new StringJoiner(", ");

				for (String value : row)
				{
					// Prevent quotes from being added around NULL values:
					if (value.equalsIgnoreCase("null"))
					{
						valueJoiner.add(String.format("%s", value));
					}
					else
					{
						valueJoiner.add(String.format("\"%s\"", value));
					}
				}

				rowJoiner.add(valueJoiner.toString());
			}

			queryBuilder.append(rowJoiner.toString());
			queryBuilder.append(");\n");
		}

		// Build an INSERT query for activity data:
		if (activityData.size() > 0)
		{
			queryBuilder.append("\nINSERT INTO Activity VALUES(");
			StringJoiner rowJoiner = new StringJoiner(");\nINSERT INTO Activity VALUES(");

			// Collect each data item for each row:
			for (LinkedList<String> row : activityData)
			{
				StringJoiner valueJoiner = new StringJoiner(", ");

				if (this.hasHeadcountData)
				{
					// Add ID value since we added Activity's ID column during schema creation:
					valueJoiner.add("null");
				}

				for (String value : row)
				{
					// Prevent quotes from being added around NULL values:
					if (value.equalsIgnoreCase("null"))
					{
						valueJoiner.add(String.format("%s", value));
					}
					else
					{
						valueJoiner.add(String.format("\"%s\"", value));
					}
				}

				rowJoiner.add(valueJoiner.toString());
			}

			queryBuilder.append(rowJoiner.toString());
			queryBuilder.append(");\n");
		}

		// End of query:
		queryBuilder.append("\nCOMMIT;");

		return queryBuilder.toString();
	}
	// endregion
}
