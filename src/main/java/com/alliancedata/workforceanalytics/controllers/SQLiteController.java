package com.alliancedata.workforceanalytics.controllers;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteConstants;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Allen on 4/20/2015.
 * This is a SQLite controller to implement various processes between the application
 * and the database.
 */

public class SQLiteController {

	//:://///////////////////////////////////////////////////////
	//::DATABASE CONSTANTS
	//:://///////////////////////////////////////////////////////

	//:://///////////////////////////////////////////////////////
	//::CLASS DATA
	//:://///////////////////////////////////////////////////////

	private SQLiteConnection _db;
	private String _delimiter = "[,]";

	public SQLiteController(String inputFile) throws SQLiteException {

		_db = new SQLiteConnection(new File(inputFile));
		_db.open(true);

	}

	public String selectSingle(String query, String args) throws SQLiteException {

		SQLiteStatement st = _db.prepare(query);
		String[] parameters = args.split(_delimiter);
		String value = null;

		try {
			for (int i = 0; i < parameters.length; i++) {
				st.bind(i + 1, parameters[i]);
			}
			int columnsCount = st.columnCount();
			if (columnsCount > 1) {
				System.err.println("Error: More than a single column!");
				return null;
			}

			if (st.step()) {
				int type = st.columnType(0);

				switch (type) {
					case SQLiteConstants.SQLITE_INTEGER:
						value = String.valueOf(st.columnInt(0));

					case SQLiteConstants.SQLITE_TEXT:
						value = st.columnString(0);

					default:
						break;
				}
			}
		} catch (Exception e) {

			System.err.println(e.getMessage());

		} finally {

			st.dispose();

		}

		return value;

	}

	public ArrayList selectMany(String query, String args) throws SQLiteException {

		SQLiteStatement st = _db.prepare(query);
		String[] parameters;

		if (!args.isEmpty() && args != null) {

			parameters = args.split(_delimiter);

			for (int i = 0; i < parameters.length; i++) {

				st.bind(i + 1, parameters[i]);

			}
		}

		ArrayList list = new ArrayList();

		try {

			int columnsCount = st.columnCount();

			if (columnsCount > 1) {

				System.err.println("Error: More than a single column!");
				return null;

			}

			while (st.step()) {

				int type = st.columnType(0);

				switch (type) {

					case SQLiteConstants.SQLITE_INTEGER:
						list.add(String.valueOf(st.columnInt(0)));
						break;

					case SQLiteConstants.SQLITE_TEXT:
						list.add(st.columnString(0));
						break;

					default:
						break;

				}

			}

		} catch (Exception e) {

			System.err.println(e.getMessage());

		} finally {

			st.dispose();

		}

		return list;

	}

	public boolean updateSingleField(String query, String args) throws SQLiteException {

		_db.exec("BEGIN TRANSACTION; ");
		SQLiteStatement st = _db.prepare(query);
		String[] parameters = args.split(_delimiter);
		try {

			for (int i = 0; i < parameters.length; i++) {

				st.bind(i + 1, parameters[i]);

			}

			st.step();
			_db.exec("COMMIT;");

		} catch (SQLiteException e) {

			System.err.println(e.getMessage());

		} finally {

			st.dispose();

		}

		return true;

	}

}