package com.alliancedata.workforceanalytics.controllers;

import com.alliancedata.workforceanalytics.Constants;
import com.alliancedata.workforceanalytics.Enums;
import com.alliancedata.workforceanalytics.LinkedListValueFactory;
import com.alliancedata.workforceanalytics.models.DataImportModel;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
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
	// endregion

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
	    titledPane_data.expandedProperty().setValue(true);
	    this.dataBind();
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

	    // TODO: offload to separate thread:
	    this.placeDataFiles(files);
	    this.createColumns();
	    this.getData();
    }

	private void dataBind()
	{
		// Bind compontent content to data model:
		vbox_data.visibleProperty().bindBidirectional(this.dataImportModel.hasDataProperty());
		tableView_headcountData.itemsProperty().bindBidirectional(this.dataImportModel.headcountDataProperty());
		tableView_activityData.itemsProperty().bindBidirectional(this.dataImportModel.activityDataProperty());
		listView_fileList.itemsProperty().bindBidirectional(this.dataImportModel.allFilesProperty());
		Bindings.bindContentBidirectional(tableView_headcountData.getColumns(), this.dataImportModel.getHeadcountColumns());
		Bindings.bindContentBidirectional(tableView_activityData.getColumns(), this.dataImportModel.getActivityColumns());
	}

	private void placeDataFiles(@NotNull ArrayList<File> files)
	{
		this.dataImportModel.addFiles(files);
	}

	private void createColumns()
	{
		// Create headcount columns:
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
					this.dataImportModel.addColumn(column, Enums.FileType.Headcount);
				}
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}

		// Create activity columns:
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
					this.dataImportModel.addColumn(column, Enums.FileType.Activity);
				}
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
	}

	private void getData()
	{
		// Get headcount data:
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

					this.dataImportModel.addRow(row, Enums.FileType.Headcount);
				}
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}

		// Get activity data:
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

					this.dataImportModel.addRow(row, Enums.FileType.Activity);
				}
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}

		this.dataImportModel.hasDataProperty().setValue(this.dataImportModel.getHeadcountData().size() > 0
			|| this.dataImportModel.getActivityData().size() > 0);
	}
}
