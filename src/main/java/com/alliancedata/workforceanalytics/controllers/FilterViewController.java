package com.alliancedata.workforceanalytics.controllers;

import com.alliancedata.workforceanalytics.Constants;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class FilterViewController implements Initializable
{
	// region Fields
	private ListProperty<String> tableList = new SimpleListProperty<>(FXCollections.observableArrayList());
	private LinkedHashMap<String, LinkedList<HBox>> columnHBoxes = new LinkedHashMap<>();
	// endregion

	// region View components
	@FXML public Button button_cancel;
	@FXML public Button button_generateReport;
	@FXML public ScrollPane scrollPane_columns;
	@FXML public ChoiceBox<String> choiceBox_tables;
	@FXML public VBox vbox_target;
	// endregion

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		this.dataBind();
		this.tableList.getValue().add("All tables");
		this.tableList.getValue().addAll(Constants.SESSION_MANAGER.getCurrentSession().getDatabaseHandler().getTableNames());

		double minCheckboxWidth = 150;
		for (String tableName : this.tableList)
		{
			if (!tableName.equalsIgnoreCase("All tables"))
			{
				LinkedHashSet<String> columnNames = Constants.SESSION_MANAGER.getCurrentSession().getDatabaseHandler().getColumnNames(tableName);
				LinkedList<HBox> hBoxes = new LinkedList<>();

				for (String columnName : columnNames)
				{
					HBox hBox = new HBox(5);
					CheckBox checkBox = new CheckBox(columnName);
					Node editableNode;

					// Create DatePicker nodes for date-related columns and TextField nodes
					// for all others:
					if (columnName.toLowerCase().contains("date"))
					{
						editableNode = new DatePicker(LocalDate.now());
					}
					else
					{
						editableNode =  new TextField();
					}

					editableNode.disableProperty().bind(checkBox.selectedProperty().not());
					hBox.getChildren().addAll(checkBox, editableNode);
					HBox.setHgrow(editableNode, Priority.ALWAYS);

					minCheckboxWidth = Math.max(minCheckboxWidth, checkBox.getLayoutBounds().getWidth());
					if (checkBox.getMinWidth() < minCheckboxWidth)
					{
						checkBox.setMinWidth(minCheckboxWidth);
						checkBox.setPrefWidth(minCheckboxWidth);
					}

					hBoxes.add(hBox);
				}

				columnHBoxes.put(tableName, hBoxes);
			}
		}

		if (choiceBox_tables.getItems().size() > 0)
		{
			choiceBox_tables.getSelectionModel().selectFirst();
		}
	}

	private void dataBind()
	{
		// Bind table list to list of tables:
		this.tableList.addListener(new ChangeListener<ObservableList<String>>() {
			@Override
			public void changed(ObservableValue<? extends ObservableList<String>> observable, ObservableList<String> oldValue, ObservableList<String> newValue) {
				choiceBox_tables.getItems().setAll(newValue);
			}
		});

		// Add selection change listener for tables choice box:
		this.choiceBox_tables.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				loadHBoxes(newValue);
			}
		});
	}

	private void loadHBoxes(String tableName)
	{
		vbox_target.getChildren().clear();

		if (tableName.equalsIgnoreCase("All tables"))
		{
			LinkedHashSet<HBox> uniqueHBoxes = new LinkedHashSet<>();

			for (String table : this.tableList)
			{
				if (!table.equalsIgnoreCase("All tables"))
				{
					uniqueHBoxes.addAll(columnHBoxes.get(table));
				}
			}

			vbox_target.getChildren().addAll(uniqueHBoxes);
		}
		else
		{
			vbox_target.getChildren().addAll(columnHBoxes.get(tableName));
		}
	}

    public void button_cancel_onAction(ActionEvent event)
    {
        Stage stage = (Stage)button_cancel.getScene().getWindow();
        stage.close();
    }
}