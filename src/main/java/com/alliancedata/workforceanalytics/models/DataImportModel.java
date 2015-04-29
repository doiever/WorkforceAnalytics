package com.alliancedata.workforceanalytics.models;

import com.alliancedata.workforceanalytics.Enums;
import com.alliancedata.workforceanalytics.Utilities;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.scene.control.TableColumn;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Internal intermediate model used for importing data.
 */
public class DataImportModel implements Serializable
{
    // region Fields
    private final BooleanProperty hasDataProperty = new SimpleBooleanProperty(false);
	private final Property<ObservableList<File>> allFilesProperty = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    private ObservableSet<TableColumn<LinkedList<String>, ?>> headcountColumns = FXCollections.observableSet();
	private ObservableSet<TableColumn<LinkedList<String>, ?>> activityColumns  = FXCollections.observableSet();
	private final ListProperty<File> headcountFilesProperty = new SimpleListProperty<>(FXCollections.observableArrayList());
	private final ListProperty<File> activityFilesProperty  = new SimpleListProperty<>(FXCollections.observableArrayList());
	private final Property<ObservableList<LinkedList<String>>> headcountDataProperty = new SimpleObjectProperty<>(FXCollections.observableArrayList());
	private final Property<ObservableList<LinkedList<String>>> activityDataProperty  = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    //                     Rows           Per-row values
	// endregion

	// region Properties
	@NotNull
	public BooleanProperty hasDataProperty()
	{
		return this.hasDataProperty;
	}

	@NotNull
	public Property<ObservableList<File>> allFilesProperty()
	{
		return this.allFilesProperty;
	}

    @NotNull
    public ListProperty headcountFilesProperty()
    {
        return this.headcountFilesProperty;
    }

	@NotNull
	public ListProperty activityFilesProperty()
	{
		return this.activityFilesProperty;
	}

	@NotNull
	public Property<ObservableList<LinkedList<String>>> headcountDataProperty()
	{
		return this.headcountDataProperty;
	}

	@NotNull
	public Property<ObservableList<LinkedList<String>>> activityDataProperty()
	{
		return this.activityDataProperty;
	}
	// endregion

    // region Methods
    @NotNull
    public Boolean hasData()
    {
	    return this.hasDataProperty.getValue();
    }

	@NotNull
	public ObservableList<File> getAllFiles()
	{
		return allFilesProperty.getValue();
	}

	@NotNull
	public List<File> getHeadcountFiles()
	{
		return this.headcountFilesProperty.getValue();
	}

	@NotNull
	public List<File> getActivityFiles()
	{
		return this.activityFilesProperty.getValue();
	}

	@NotNull
	public ObservableSet<TableColumn<LinkedList<String>, ?>> getHeadcountColumns()
	{
		return this.headcountColumns;
	}

	@NotNull
	public ObservableSet<TableColumn<LinkedList<String>, ?>> getActivityColumns()
	{
		return this.activityColumns;
	}

	@NotNull
	public ObservableList<LinkedList<String>> getHeadcountData()
	{
		return this.headcountDataProperty.getValue();
	}

	@NotNull
	public ObservableList<LinkedList<String>> getActivityData()
	{
		return this.activityDataProperty.getValue();
	}

	/**
	 * Clears the headcount and activity file lists, then places each file in the {@code files} parameter
	 * into the appropriate file list.
	 * @param files The list containing files to use.
	 */
	public void setFiles(@NotNull List<File> files)
	{
		// Clear file lists:
		this.headcountFilesProperty.clear();
		this.activityFilesProperty.clear();
		this.allFilesProperty.getValue().clear();

		// Place each file according to its type:
		for (File file : files)
		{
			if (file != null && file.length() > 0 && Utilities.getFileExtension(file.getName()).equalsIgnoreCase("csv"))
			{
				if (file.getName().toLowerCase().startsWith("headcount"))
				{
					this.headcountFilesProperty.add(file);
					this.allFilesProperty.getValue().add(file);
				}
				else if (file.getName().toLowerCase().startsWith("activity"))
				{
					this.activityFilesProperty.add(file);
					this.allFilesProperty.getValue().add(file);
				}
				else
				{
					// TODO: Log unknown file type
				}

			}
		}
	}

	/**
	 * Clears the corresponding (to {@code fileType}) column list and sets it to the provided {@code columns} parameter.
	 * @param columns The list of columns to use.
	 * @param fileType The file type for which columns will be cleared.
	 */
	public void setColumns(LinkedHashSet<TableColumn<LinkedList<String>, ?>> columns, Enums.FileType fileType)
	{
		ObservableSet<TableColumn<LinkedList<String>, ?>> observableColumns = FXCollections.observableSet(columns);

		if (fileType == Enums.FileType.Headcount)
		{
			this.headcountColumns = observableColumns;
		}
		else if (fileType == Enums.FileType.Activity)
		{
			this.activityColumns = observableColumns;
		}
	}

	/**
	 * Clears the corresponding (to {@code fileType}) data list and sets it to the provided {@code data} parameter.
	 * @param data The data to use.
	 * @param fileType The file type for which data will be cleared.
	 */
	public void setData(ObservableList<LinkedList<String>> data, Enums.FileType fileType)
	{
		if (fileType == Enums.FileType.Headcount)
		{
			this.headcountDataProperty.setValue(data);
		}
		else if (fileType == Enums.FileType.Activity)
		{
			this.activityDataProperty.setValue(data);
		}
	}

	/**
	 * Appends a row to the corresponding data list.
	 * @param row The {@code LinkedList<String>} containing row data.
	 * @param fileType The {@code FileType} corresponding to the table which contains the row.
	 */
	public void addRow(LinkedList<String> row, Enums.FileType fileType)
	{
		if (fileType == Enums.FileType.Headcount)
		{
			this.headcountDataProperty.getValue().add(row);
		}
		else if (fileType == Enums.FileType.Activity)
		{
			this.activityDataProperty.getValue().add(row);
		}
	}

	/**
	 * Appends a column to the corresponding column list.
	 * @param column The {@code TableColumn<LinkedList<String>, String>} to add.
	 * @param fileType The {@code FileType} corresponding to the table which contains the column.
	 */
	public void addColumn(@NotNull TableColumn<LinkedList<String>, String> column, Enums.FileType fileType)
	{
		if (fileType == Enums.FileType.Headcount)
		{
			this.headcountColumns.add(column);
		}
		else if (fileType == Enums.FileType.Activity)
		{
			this.activityColumns.add(column);
		}
	}
	// endregion
}
