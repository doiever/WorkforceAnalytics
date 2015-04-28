package com.alliancedata.workforceanalytics.controllers;

import com.alliancedata.workforceanalytics.Constants;
import com.alliancedata.workforceanalytics.Enums;
import com.alliancedata.workforceanalytics.LinkedListValueFactory;
import com.alliancedata.workforceanalytics.Utilities;
import com.alliancedata.workforceanalytics.models.DataImportModel;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Controller class for the Main view.
 */
public class MainController implements Initializable
{
	// region Fields
    private DataImportModel dataImportModel = new DataImportModel();
	private StringProperty statusTextProperty = new SimpleStringProperty("Idle");
	private BooleanProperty isLoadingData = new SimpleBooleanProperty(false);
	// endregion

    // region View components
    @FXML public GridPane gridPane_main;
    @FXML public MenuBar menuBar_main;
    @FXML public Menu menu_file;
    @FXML public Menu menu_menu;
    @FXML public Menu menu_help;
    @FXML public ToolBar toolBar_main;
    @FXML public Button button_generateReport;
    @FXML public HBox hbox_statusBar;
    @FXML public Label label_status;
    @FXML public Hyperlink hyperlink_importData;
    @FXML public Hyperlink hyperlink_filterData;
    @FXML public TableView<LinkedList<String>> tableView_headcountData;
    @FXML public TableView<LinkedList<String>> tableView_activityData;
	@FXML public VBox vbox_data;
    @FXML public TitledPane titledPane_data;
	@FXML public ListView<File> listView_fileList;
	@FXML public ProgressBar progressBar;
	// endregion

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
	    // Set up bindings, listeners, and event handlers:
	    dataBind();
	    this.titledPane_data.expandedProperty().setValue(true);
    }

    public void hyperlink_importData_OnAction(ActionEvent event)
    {
        // Get data files from user:
        Window owner = ((Node)event.getTarget()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import data files");
        fileChooser.setInitialDirectory(Constants.INITIAL_IMPORT_DATA_DIRECTORY);

	    List<File> selectedFiles = fileChooser.showOpenMultipleDialog(owner);

	    if (selectedFiles == null || selectedFiles.size() <= 0) return;

	    ArrayList<File> files = new ArrayList<>(selectedFiles);
		this.dataImportModel.setFiles(files);

	    this.isLoadingData.setValue(true);
	    this.statusTextProperty.setValue("Reading data...");

	    // Load data in the background:
	    Task<Void> getDataTask = new Task<Void>() {
		    @Override
		    protected Void call() throws Exception {
			    dataImportModel.setColumns(createHeadcountColumns(), Enums.FileType.Headcount);
			    dataImportModel.setColumns(createActivityColumns(), Enums.FileType.Activity);
			    dataImportModel.setData(getHeadcountData(), Enums.FileType.Headcount);
			    dataImportModel.setData(getActivityData(), Enums.FileType.Activity);

			    return null;
		    }
	    };

	    this.progressBar.progressProperty().bind(getDataTask.progressProperty());

	    // Update UI once background tasks finish:
	    getDataTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
		    @Override
		    public void handle(WorkerStateEvent event) {
			    Platform.runLater(new Runnable() {
				    @Override
				    public void run() {
					    int headcountRowCount = dataImportModel.getHeadcountData().size();
					    int activityRowCount = dataImportModel.getActivityData().size();
					    int headcountFileCount = dataImportModel.getHeadcountFiles().size();
					    int activityFileCount = dataImportModel.getActivityFiles().size();
					    String statusText = String.format("Read %d rows from %d headcount file%s and %d rows from %d activity file%s.",
						    headcountRowCount, headcountFileCount, headcountFileCount > 1 ? "s" : "",
						    activityRowCount, activityFileCount, activityFileCount > 1 ? "s" : "");
					    statusTextProperty.setValue(statusText);
					    bindColumns();
					    isLoadingData.setValue(false);
				    }
			    });
		    }
	    });

	    // Failure while reading data:
	    getDataTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
		    @Override
		    public void handle(WorkerStateEvent event) {
			    String text = getDataTask.getException() == null ? getDataTask.getMessage() : getDataTask.getException().getMessage();
				Utilities.showTextAreaDialog(Alert.AlertType.ERROR, "Data Error", null, "An error occurred while reading data.", text);
		    }
	    });

	    // getDataTask.run();
	    new Thread(getDataTask).start();
    }

	private void dataBind()
	{
		// Bind compontent content to data model:
		this.vbox_data.visibleProperty().bindBidirectional(new SimpleBooleanProperty(true));
		this.tableView_headcountData.itemsProperty().bindBidirectional(this.dataImportModel.headcountDataProperty());
		this.tableView_activityData.itemsProperty().bindBidirectional(this.dataImportModel.activityDataProperty());
		this.listView_fileList.itemsProperty().bindBidirectional(this.dataImportModel.allFilesProperty());
		this.label_status.textProperty().bindBidirectional(this.statusTextProperty);
		this.progressBar.visibleProperty().bindBidirectional(isLoadingData);
	}

	private void bindColumns()
	{
		// Since we can't bind a TableView's columns in the traditional JavaFX way (there is no
		// ColumnsProperty), we have to bind each TableView's list of columns manually *after*
		// the column data has been read from the files and TableColumn objects are created.

		Bindings.bindContentBidirectional(tableView_headcountData.getColumns(), this.dataImportModel.getHeadcountColumns());
		Bindings.bindContentBidirectional(tableView_activityData.getColumns(), this.dataImportModel.getActivityColumns());
	}

	@NotNull
	private ObservableList<TableColumn<LinkedList<String>, ?>> createHeadcountColumns()
	{
		ObservableList<TableColumn<LinkedList<String>, ?>> columns = new ObservableListWrapper<>(FXCollections.observableArrayList());

		for (File file : this.dataImportModel.getHeadcountFiles())
		{
			int columnIndex = 0;

			// Read column names and types:
			try (BufferedReader reader = new BufferedReader(new FileReader(file)))
			{
				String line = reader.readLine();
				StringTokenizer tokenizer = new StringTokenizer(line, ",");

				while (tokenizer.hasMoreTokens())
				{
					String columnName = tokenizer.nextToken().trim();
					TableColumn<LinkedList<String>, String> column = new TableColumn<>(columnName);
					column.setCellValueFactory(new LinkedListValueFactory(columnIndex++));
					columns.add(column);
				}
			}
			catch (IOException ex)
			{
				// TODO: Unable to read from a file
				ex.printStackTrace();
			}
		}

		return columns;
	}

	@NotNull
	private ObservableList<TableColumn<LinkedList<String>, ?>> createActivityColumns()
	{
		ObservableList<TableColumn<LinkedList<String>, ?>> columns = new ObservableListWrapper<>(FXCollections.observableArrayList());

		for (File file : this.dataImportModel.getActivityFiles())
		{
			int columnIndex = 0;

			// Read column names and types:
			try (BufferedReader reader = new BufferedReader(new FileReader(file)))
			{
				String line = reader.readLine();
				StringTokenizer tokenizer = new StringTokenizer(line, ",");

				while (tokenizer.hasMoreTokens())
				{
					String columnName = tokenizer.nextToken().trim();
					TableColumn<LinkedList<String>, String> column = new TableColumn<>(columnName);
					column.setCellValueFactory(new LinkedListValueFactory(columnIndex++));
					columns.add(column);
				}
			}
			catch (IOException ex)
			{
				// TODO: Unable to read from a file
				ex.printStackTrace();
			}
		}

		return columns;
	}

	@NotNull
	private ObservableList<LinkedList<String>> getHeadcountData()
	{
		ObservableList<LinkedList<String>> data = new ObservableListWrapper<>(FXCollections.observableArrayList());

		for (File file : this.dataImportModel.getHeadcountFiles())
		{
			try (BufferedReader reader = new BufferedReader(new FileReader(file)))
			{
				String line;

				// Discard column names:
				reader.readLine();

				// Add each line's data items to fileData:
				while ((line = reader.readLine()) != null && line.length() > 0)
				{
					LinkedList<String> row = new LinkedList<>();
					StringTokenizer tokenizer = new StringTokenizer(line, ",");

					while (tokenizer.hasMoreTokens())
					{
						String token = tokenizer.nextToken();
						row.add(token);
					}

					data.add(row);
				}
			}
			catch (IOException ex)
			{
				// TODO: Unable to read from a file
				ex.printStackTrace();
			}
		}

		return data;
	}

	@NotNull
	private ObservableList<LinkedList<String>> getActivityData()
	{
		ObservableList<LinkedList<String>> data = new ObservableListWrapper<>(FXCollections.observableArrayList());

		for (File file : this.dataImportModel.getActivityFiles())
		{
			try (BufferedReader reader = new BufferedReader(new FileReader(file)))
			{
				String line;

				// Discard column names:
				reader.readLine();

				// Add each line's data items to fileData:
				while ((line = reader.readLine()) != null && line.length() > 0)
				{
					LinkedList<String> row = new LinkedList<>();
					StringTokenizer tokenizer = new StringTokenizer(line, ",");

					while (tokenizer.hasMoreTokens())
					{
						String token = tokenizer.nextToken();
						row.add(token);
					}

					data.add(row);
				}
			}
			catch (IOException ex)
			{
				// TODO: Unable to read from a file
				ex.printStackTrace();
			}
		}

		return data;
	}
}
