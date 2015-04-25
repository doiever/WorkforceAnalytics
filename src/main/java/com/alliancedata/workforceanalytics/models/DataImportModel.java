package com.alliancedata.workforceanalytics.models;

import com.alliancedata.workforceanalytics.Enums;
import com.alliancedata.workforceanalytics.Utility;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.Serializable;
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
	private final ListProperty<File> headcountFilesProperty = new SimpleListProperty<>(FXCollections.observableArrayList());
	private final ListProperty<File> activityFilesProperty  = new SimpleListProperty<>(FXCollections.observableArrayList());
	private final ObservableList<TableColumn<LinkedList<String>, ?>> headcountColumns = FXCollections.observableArrayList();
	private final ObservableList<TableColumn<LinkedList<String>, ?>> activityColumns  = FXCollections.observableArrayList();;
	private final Property<ObservableList<LinkedList<String>>> headcountDataProperty = new SimpleObjectProperty<>(FXCollections.observableArrayList());
	private final Property<ObservableList<LinkedList<String>>> activityDataProperty  = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    //                     Rows           Per-row values
	// endregion

	// region Properties
	// Use these for databinding.
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
	// Use these to maintain property integrity while getting/setting data.
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
	public ObservableList<TableColumn<LinkedList<String>, ?>> getHeadcountColumns()
	{
		return this.headcountColumns;
	}

	@NotNull
	public ObservableList<TableColumn<LinkedList<String>, ?>> getActivityColumns()
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
	 * Clears the headcount and activity file list, then places each file into the appropriate file list.
	 * @param files The list containing files to use.
	 */
	public void setFiles(List<File> files)
	{
		// Clear file lists:
		this.headcountFilesProperty.clear();
		this.activityFilesProperty.clear();

		// Place each file according to its type:
		for (File file : files)
		{
			this.addFile(file);
		}
	}

	/**
	 * Appends files to the appropriate file list.
	 * @param files The list containing files to append.
	 */
	public void addFiles(List<File> files)
	{
		// Place each file according to its type:
		for (File file : files)
		{
			this.addFile(file);
		}
	}

	/**
	 * Removes files from the appropriate file list.
	 * @param files The list containing files to remove.
	 */
	public void removeFiles(List<File> files)
	{
		// Remove each file:
		for (File file : files)
		{
			this.removeFile(file);
		}
	}

	/**
	 * Appends a file to the appropriate file list.
	 * @param file The file to add.
	 */
	public void addFile(File file)
	{
		if (file != null && file.length() > 0 && Utility.getFileExtension(file.getName()).equalsIgnoreCase("csv"))
		{
			if (file.getName().toLowerCase().startsWith("headcount"))
			{
				headcountFilesProperty.add(file);
			}
			else if (file.getName().toLowerCase().startsWith("activity"))
			{
				activityFilesProperty.add(file);
			}
			else
			{
				// TODO: Log unknown file type
			}
		}

		this.allFilesProperty.getValue().add(file);
	}

	/**
	 * Removes a file from the appropriate file list. If the file list does not contains {@code file}, no changes are made.
	 * @param file The file to remove.
	 */
	public void removeFile(File file)
	{
		if (file != null && file.length() > 0 && Utility.getFileExtension(file.getName()).equalsIgnoreCase("csv"))
		{
			if (file.getName().toLowerCase().startsWith("headcount"))
			{
				headcountFilesProperty.remove(file);
			}
			else if (file.getName().toLowerCase().startsWith("activity"))
			{
				activityFilesProperty.remove(file);
			}
			else
			{
				// TODO: Log unknown file type
			}
		}

		this.allFilesProperty.getValue().remove(file);
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
	// endregion
}
