package com.alliancedata.workforceanalytics.controllers;

import com.alliancedata.workforceanalytics.Constants;
import com.alliancedata.workforceanalytics.Utilities;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class DataSummaryController implements Initializable
{
	// region Fields
	private StringProperty fileCount = new SimpleStringProperty();
	private StringProperty tableCount = new SimpleStringProperty();
	private StringProperty databaseFileSize = new SimpleStringProperty();
	private ListProperty<File> fileList  = new SimpleListProperty<>(FXCollections.observableArrayList());
	// endregion

	// region View components
	@FXML public Label label_fileCount;
	@FXML public Label label_tableCount;
	@FXML public Label label_databaseSize;
	@FXML public ListView<String> listView_files;
	// endregion

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		this.dataBind();

		// Get metrics:
		this.fileCount.setValue(String.valueOf(Constants.SESSION_MANAGER.getCurrentSession().getFiles().size()));
		this.tableCount.setValue(String.valueOf(Constants.SESSION_MANAGER.getCurrentSession().getDatabaseHandler().getTableNames().size()));
		this.databaseFileSize.setValue(Utilities.getFileSize(Constants.SESSION_MANAGER.getCurrentSession().getDatabaseHandler().getDatabaseFile().length()));
		this.fileList.setValue(new ObservableListWrapper<File>(Constants.SESSION_MANAGER.getCurrentSession().getFiles()));
	}

	private void dataBind()
	{
		this.label_fileCount.textProperty().bind(fileCount);
		this.label_tableCount.textProperty().bind(tableCount);
		this.label_databaseSize.textProperty().bind(databaseFileSize);

		this.fileList.addListener(new ChangeListener<ObservableList<File>>() {
			@Override
			public void changed(ObservableValue<? extends ObservableList<File>> observable, ObservableList<File> oldValue, ObservableList<File> newValue) {
				ObservableList<String> fileNames = FXCollections.observableArrayList();
				newValue.forEach(file -> fileNames.add(file.getName()));
				listView_files.getItems().clear();
				listView_files.itemsProperty().set(fileNames);
			}
		});
	}

	public void button_OK_onAction(ActionEvent event)
	{
		Stage stage = (Stage)label_fileCount.getScene().getWindow();
		stage.close();
	}
}
