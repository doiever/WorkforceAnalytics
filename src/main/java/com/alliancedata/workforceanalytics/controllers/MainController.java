package com.alliancedata.workforceanalytics.controllers;

import com.alliancedata.workforceanalytics.*;
import com.alliancedata.workforceanalytics.models.DataImportModel;
import com.alliancedata.workforceanalytics.models.DatabaseHandler;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.*;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.util.*;

import static com.alliancedata.workforceanalytics.models.DatabaseHandler.executeQuery;

/**
 * Controller class for the Main view.
 */
public class MainController implements Initializable
{
	// region Fields
	private DataImportModel dataImportModel = new DataImportModel();
	private StringProperty statusTextProperty = new SimpleStringProperty("Idle");
	private BooleanProperty isLoadingData = new SimpleBooleanProperty(false);
	public static BooleanProperty isUsingPreviousSession = new SimpleBooleanProperty(false);
	public BooleanProperty hasCreatedDatabase = new SimpleBooleanProperty(false);
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
	@FXML public Hyperlink hyperlink_generateReport;
	@FXML public TableView<LinkedList<String>> tableView_headcountData;
	@FXML public TableView<LinkedList<String>> tableView_activityData;
	@FXML public VBox vbox_data;
	@FXML public TitledPane titledPane_data;
	@FXML public ListView<File> listView_fileList;
	@FXML public Button button_useThisData;
	@FXML public Button button_startOver;
	@FXML public Accordion accordion_tasks;
	@FXML public Button button_print;
	@FXML public TextFlow textFlow_placeholder;
	@FXML public SplitPane splitPane_data;
	@FXML public Hyperlink hyperlink_viewDataSummary;
	@FXML public TabPane tabPane_data;
	@FXML public ProgressIndicator progressIndicator;
	@FXML public ToolBar toolbar_confirmData;
	// endregion

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		// Load previous session's data:
		if (isUsingPreviousSession.getValue())
		{
			loadPreviousSessionData();
		}

		// Set up bindings, listeners, and event handlers:
		this.accordion_tasks.setExpandedPane(this.titledPane_data);
		this.dataBind();
	}

	private void loadPreviousSessionData()
	{
		// Get columns and data from existing database:
		this.hasCreatedDatabase.setValue(true);

		LinkedHashSet<String> headcountColumnNames = Constants.SESSION_MANAGER.getCurrentSession().getDatabaseHandler().getColumnNames("Headcount");
		LinkedHashSet<String> activityColumnsNames = Constants.SESSION_MANAGER.getCurrentSession().getDatabaseHandler().getColumnNames("Activity");
		LinkedList<LinkedList<String>> rawHeadcountData = executeQuery(Constants.SESSION_MANAGER.getCurrentSession(), "SELECT * FROM Headcount;");
		LinkedList<LinkedList<String>> rawActivityData = executeQuery(Constants.SESSION_MANAGER.getCurrentSession(), "SELECT * FROM Activity;");

		LinkedHashSet<TableColumn<LinkedList<String>, ?>> headcountColumns = new LinkedHashSet<>();
		LinkedHashSet<TableColumn<LinkedList<String>, ?>> activityColumns = new LinkedHashSet<>();

		int columnIndex = 0;
		for (String columnName : headcountColumnNames)
		{
			TableColumn<LinkedList<String>, String> column = new TableColumn<>(columnName);
			column.setCellValueFactory(new LinkedListValueFactory(columnIndex++));
			headcountColumns.add(column);
		}

		columnIndex = 0;
		for (String columnName : activityColumnsNames)
		{
			TableColumn<LinkedList<String>, String> column = new TableColumn<>(columnName);
			column.setCellValueFactory(new LinkedListValueFactory(columnIndex++));
			activityColumns.add(column);
		}

		// Add columns and data to the import model:
		ObservableList<LinkedList<String>> headcountData = FXCollections.observableArrayList(rawHeadcountData);
		ObservableList<LinkedList<String>> activityData = FXCollections.observableArrayList(rawActivityData);

		this.dataImportModel.setFiles(Constants.SESSION_MANAGER.getCurrentSession().getFiles());
		this.dataImportModel.setColumns(headcountColumns, Enums.FileType.Headcount);
		this.dataImportModel.setColumns(activityColumns, Enums.FileType.Activity);
		this.dataImportModel.setData(headcountData, Enums.FileType.Headcount);
		this.dataImportModel.setData(activityData, Enums.FileType.Activity);

		// Update UI to a loading state:
		this.isLoadingData.setValue(true);
		this.statusTextProperty.setValue("Reading data...");

		// Load data in the background:
		Task getDataTask = new Task() {
			@Override
			protected Void call() throws Exception {
				dataImportModel.setColumns(createHeadcountColumns(), Enums.FileType.Headcount);
				dataImportModel.setColumns(createActivityColumns(), Enums.FileType.Activity);
				dataImportModel.setData(getHeadcountData(), Enums.FileType.Headcount);
				dataImportModel.setData(getActivityData(), Enums.FileType.Activity);

				return null;
			}
		};

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
							headcountRowCount, headcountFileCount, headcountFileCount != 1 ? "s" : "",
							activityRowCount, activityFileCount, activityFileCount != 1 ? "s" : "");
						statusTextProperty.setValue(statusText);
						populateTableView();
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

		new Thread(getDataTask).start();
	}

	private void dataBind()
	{
		// Bind node properties and content:
		this.statusTextProperty.setValue("Idle");
		this.vbox_data.visibleProperty().bind(this.dataImportModel.hasDataProperty());
		this.listView_fileList.itemsProperty().bind(this.dataImportModel.allFilesProperty());
		this.label_status.textProperty().bind(this.statusTextProperty);
		this.progressIndicator.visibleProperty().bind(this.isLoadingData);
		this.button_print.disableProperty().bind(this.hasCreatedDatabase.not());
		this.button_generateReport.disableProperty().bind(this.hasCreatedDatabase.not());
		this.hyperlink_generateReport.disableProperty().bind(this.hasCreatedDatabase.not());
		this.textFlow_placeholder.visibleProperty().bind(Bindings.or(this.dataImportModel.hasDataProperty(), this.isLoadingData).not());
		this.button_useThisData.disableProperty().bind(this.dataImportModel.hasDataProperty().not());
		this.button_startOver.disableProperty().bind(this.dataImportModel.hasDataProperty().not());
		this.toolbar_confirmData.managedProperty().bind(this.toolbar_confirmData.visibleProperty());
		this.toolbar_confirmData.visibleProperty().bind(this.hasCreatedDatabase.not());
		this.hyperlink_importData.disableProperty().bind(this.hasCreatedDatabase);
	}

	@FXML
	public void hyperlink_importData_OnAction(ActionEvent event)
	{
		// Get data files from user:
		Window owner = ((Node)event.getTarget()).getScene().getWindow();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"));
		fileChooser.setTitle("Import data files");
		fileChooser.setInitialDirectory(Constants.INITIAL_IMPORT_DATA_DIRECTORY);

		List<File> selectedFiles = fileChooser.showOpenMultipleDialog(owner);

		if (selectedFiles == null || selectedFiles.size() <= 0) return;

		ArrayList<File> files = new ArrayList<>(selectedFiles);
		this.dataImportModel.setFiles(files);
		Constants.SESSION_MANAGER.getCurrentSession().setFiles(files);

		// Update UI to a loading state:
		this.isLoadingData.setValue(true);
		this.statusTextProperty.setValue("Reading data...");

		// Load data in the background:
		Task getDataTask = new Task() {
			@Override
			protected Void call() throws Exception {
				dataImportModel.setColumns(createHeadcountColumns(), Enums.FileType.Headcount);
				dataImportModel.setColumns(createActivityColumns(), Enums.FileType.Activity);
				dataImportModel.setData(getHeadcountData(), Enums.FileType.Headcount);
				dataImportModel.setData(getActivityData(), Enums.FileType.Activity);

				return null;
			}
		};

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
								headcountRowCount, headcountFileCount, headcountFileCount != 1 ? "s" : "",
								activityRowCount, activityFileCount, activityFileCount != 1 ? "s" : "");
						statusTextProperty.setValue(statusText);
						populateTableView();
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

		new Thread(getDataTask).start();
	}

	@FXML
	public void button_useThisData_onAction(ActionEvent actionEvent)
	{
		// Create database with data from selected files:
		LinkedList<String> headcountColumns = new LinkedList<String>();
		LinkedList<String> activityColumns = new LinkedList<String>();
		LinkedList<LinkedList<String>> headcountData = new LinkedList<LinkedList<String>>(this.dataImportModel.getHeadcountData());
		LinkedList<LinkedList<String>> activityData = new LinkedList<LinkedList<String>>(this.dataImportModel.getActivityData());

		this.dataImportModel.getHeadcountColumns().forEach(column -> headcountColumns.add(column.getText()));
		this.dataImportModel.getActivityColumns().forEach(column -> activityColumns.add(column.getText()));

		boolean success = Constants.SESSION_MANAGER.getCurrentSession().getDatabaseHandler().createDatabase(headcountColumns,
			activityColumns, headcountData, activityData);

		if (!success)
		{
			Utilities.showTextAreaDialog(Alert.AlertType.ERROR, "Database Error", null, "Unable to write to the database file.",
				Constants.SESSION_MANAGER.getCurrentSession().getDatabaseHandler().getDatabaseFile().getAbsolutePath());
		}

		this.hasCreatedDatabase.setValue(true);
		this.statusTextProperty.setValue("Database created!");
	}

	private void populateTableView()
	{
		ObservableList<TableColumn<LinkedList<String>, ?>> observableHeadcountColumns = FXCollections.observableArrayList();
		observableHeadcountColumns.addAll(this.dataImportModel.getHeadcountColumns());
		ObservableList<TableColumn<LinkedList<String>, ?>> observableActivityColumns = FXCollections.observableArrayList();
		observableActivityColumns.addAll(this.dataImportModel.getActivityColumns());

		Bindings.bindContentBidirectional(tableView_headcountData.getColumns(), observableHeadcountColumns);
		Bindings.bindContentBidirectional(tableView_activityData.getColumns(), observableActivityColumns);

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				tableView_headcountData.getItems().addAll(dataImportModel.getHeadcountData());
			}
		});

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				tableView_activityData.getItems().addAll(dataImportModel.getActivityData());
			}
		});

		isLoadingData.setValue(false);
		dataImportModel.hasDataProperty().setValue(true);
		this.tabPane_data.getSelectionModel().select(1);
	}

	@NotNull
	private LinkedHashSet<TableColumn<LinkedList<String>, ?>> createHeadcountColumns()
	{
		LinkedHashSet<TableColumn<LinkedList<String>, ?>> columns = new LinkedHashSet<>();
		LinkedHashSet<String> columnNames = new LinkedHashSet<>();

		for (File file : this.dataImportModel.getHeadcountFiles())
		{
			int columnIndex = 0;

			// Read column names:
			try (BufferedReader reader = new BufferedReader(new FileReader(file)))
			{
				String line = reader.readLine();
				StringTokenizer tokenizer = new StringTokenizer(line, ",");

				while (tokenizer.hasMoreTokens())
				{
					String columnName = tokenizer.nextToken().trim();
					if (!columnNames.contains(columnName))
					{
						columnNames.add(columnName);
						TableColumn<LinkedList<String>, String> column = new TableColumn<>(columnName);
						column.setCellValueFactory(new LinkedListValueFactory(columnIndex++));
						columns.add(column);
					}
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
	private LinkedHashSet<TableColumn<LinkedList<String>, ?>> createActivityColumns()
	{
		LinkedHashSet<TableColumn<LinkedList<String>, ?>> columns = new LinkedHashSet<TableColumn<LinkedList<String>, ?>>();
		LinkedHashSet<String> columnNames = new LinkedHashSet<>();

		for (File file : this.dataImportModel.getActivityFiles())
		{
			int columnIndex = 0;

			// Read column names:
			try (BufferedReader reader = new BufferedReader(new FileReader(file)))
			{
				String line = reader.readLine();
				StringTokenizer tokenizer = new StringTokenizer(line, ",");

				while (tokenizer.hasMoreTokens())
				{
					String columnName = tokenizer.nextToken().trim();
					if (!columnNames.contains(columnName))
					{
						columnNames.add(columnName);
						TableColumn<LinkedList<String>, String> column = new TableColumn<>(columnName);
						column.setCellValueFactory(new LinkedListValueFactory(columnIndex++));
						columns.add(column);
					}
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

    public void loadFilterView()
    {
        FXMLLoader loader = new FXMLLoader();

        try
        {
	        Window mainWindow = this.gridPane_main.getScene().getWindow();
	        Stage filterStage = new Stage();
	        Parent rootNode = (Parent)loader.load(getClass().getResourceAsStream(Constants.FILTER_VIEW));
	        Scene scene = new Scene(rootNode);

	        filterStage.setTitle(Constants.APPLICATION_NAME);
	        filterStage.initStyle(StageStyle.UTILITY);
	        filterStage.setScene(scene);
	        filterStage.initOwner(mainWindow);
	        filterStage.initModality(Modality.WINDOW_MODAL);
	        filterStage.show();
        }
        catch (IOException ex)
        {
            // TODO: Can't find Constants.FILTER_VIEW file.
            ex.printStackTrace();
        }
	    catch (Exception ex)
	    {
		    ex.printStackTrace();
	    }

    }

	public void loadAboutView()
	{
		FXMLLoader loader = new FXMLLoader();

		try
		{
			Window mainWindow = this.gridPane_main.getScene().getWindow();
			Stage aboutStage = new Stage();
			Parent rootNode = (Parent)loader.load(getClass().getResourceAsStream(Constants.ABOUT_VIEW));
			Scene scene = new Scene(rootNode);

			aboutStage.setTitle(Constants.APPLICATION_NAME);
			aboutStage.initStyle(StageStyle.UTILITY);
			aboutStage.setScene(scene);
			aboutStage.initOwner(mainWindow);
			aboutStage.initModality(Modality.WINDOW_MODAL);
			aboutStage.show();
		}
		catch (IOException ex)
		{
			// TODO: Can't find Constants.ABOUT_VIEW file.
			ex.printStackTrace();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public void loadGuideView()
	{
		FXMLLoader loader = new FXMLLoader();

		try
		{
			Window mainWindow = this.gridPane_main.getScene().getWindow();
			Stage guideStage = new Stage();
			Parent rootNode = (Parent)loader.load(getClass().getResourceAsStream(Constants.GUIDE_VIEW));
			Scene scene = new Scene(rootNode);

			guideStage.setTitle("User Guide");
			guideStage.initStyle(StageStyle.UTILITY);
			guideStage.setScene(scene);
			guideStage.initOwner(mainWindow);
			guideStage.initModality(Modality.WINDOW_MODAL);
			guideStage.show();
		}
		catch (IOException ex)
		{
			// TODO: Can't find Constants.GUIDE_VIEW file.
			ex.printStackTrace();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

	}

    public void hyperlink_filterData_OnAction(ActionEvent event)
    {
        loadFilterView();

    }

    public void hyperlink_ExitSystem(ActionEvent event)
    {
        System.exit(0);
    }

    public void hyperlink_printdoc(ActionEvent event)
    {

        PrinterJob p = PrinterJob.createPrinterJob();
	    Window owner = ((Node)event.getTarget()).getScene().getWindow();

        if (p.showPrintDialog(owner))
        {
	        p.printPage(vbox_data);
        }
    }



    public void hyperlink_SaveCSV(ActionEvent event)
    {

	// Make File to save from user
	Window owner = gridPane_main.getScene().getWindow();
	FileChooser fileChooser = new FileChooser();
	fileChooser.setTitle("Export Report");
	fileChooser.setInitialDirectory(Constants.INITIAL_EXPORT_DATA_DIRECTORY);

	FileChooser.ExtensionFilter EF = new FileChooser.ExtensionFilter("CSV file (*.csv)", "*.csv");
	fileChooser.getExtensionFilters().add(EF);

	File file = fileChooser.showSaveDialog(owner);

	if(file != null){
		LinkedList<LinkedList<String>> SF = DatabaseHandler.executeQuery(Constants.SESSION_MANAGER.getCurrentSession(), "SELECT * FROM Activity;");
		SaveFile(SF, file);
	}
	}


	//Save file method
	private void SaveFile(LinkedList<LinkedList<String>> content,File file){
		try{
			FileWriter fileWriter = null;
			fileWriter = new FileWriter(file);

			LinkedHashSet<String> CN = Constants.SESSION_MANAGER.getCurrentSession().getDatabaseHandler().getColumnNames("Activity");
			String Header = String.join(",", CN);

			fileWriter.write(Header);
			fileWriter.write('\n');

			for(LinkedList<String> row : content){
				String rowString = String.join(",",row);
				fileWriter.write(rowString);
				fileWriter.write('\n');
			}

			fileWriter.flush();
			fileWriter.close();
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}

    public void hyperlink_UserGuide(ActionEvent event)
    {
		loadGuideView();
    }

    public void hyperlink_About(ActionEvent event)
    {
		loadAboutView();
    }

    public void hyperlink_FindTrends(ActionEvent event)
    {

    }

    public void hyperlink_FindSpecificTrend(ActionEvent event)
    {

    }

    public void hyperlink_DataSummary(ActionEvent event)
    {

    }

	public void button_startOver_OnAction(ActionEvent event)
	{
		// Reset to empty session:
		this.dataImportModel = new DataImportModel();
		this.isLoadingData.setValue(false);
		this.dataBind();
	}
}
