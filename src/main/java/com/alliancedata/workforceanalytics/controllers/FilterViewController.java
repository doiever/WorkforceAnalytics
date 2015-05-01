package com.alliancedata.workforceanalytics.controllers;

import com.alliancedata.workforceanalytics.Constants;
import com.alliancedata.workforceanalytics.models.DatabaseHandler;
import com.alliancedata.workforceanalytics.models.Report;
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
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class FilterViewController implements Initializable
{
	// region Fields
	private ListProperty<String> tableList = new SimpleListProperty<>(FXCollections.observableArrayList());
	private LinkedHashMap<String, LinkedList<HBox>> columnHBoxes = new LinkedHashMap<>();
	public static Report reportModel = new Report();
	public static MainController mainController;
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
		this.tableList.getValue().addAll(Constants.SESSION_MANAGER.getCurrentSession().getDatabaseHandler().getTableNames());

		double minCheckboxWidth = 150;
		for (String tableName : this.tableList)
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
					editableNode = new DatePicker();
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
		vbox_target.getChildren().addAll(columnHBoxes.get(tableName));
	}

	public void button_cancel_onAction(ActionEvent event)
	{
		Stage stage = (Stage)button_cancel.getScene().getWindow();
		stage.close();
	}

	public void button_generate_onAction(ActionEvent event)
	{
		LinkedList<String> selectedColumnNames = new LinkedList<>();
		LinkedList<String> values = new LinkedList<>();
		String selectedTableName = this.choiceBox_tables.getSelectionModel().getSelectedItem();

		// Generate list of selected columns and values:
		for (Node node : vbox_target.getChildren())
		{
			if (node instanceof HBox)
			{
				HBox hbox = (HBox)node;

				if (hbox.getChildren() != null && hbox.getChildren().size() > 1 && hbox.getChildren().get(0) instanceof CheckBox)
				{
					CheckBox checkBox = (CheckBox)hbox.getChildren().get(0);
					Node editableNode = hbox.getChildren().get(1);

					if (checkBox.selectedProperty().getValue())
					{
						selectedColumnNames.add(checkBox.getText());

						if (editableNode instanceof DatePicker)
						{
							DatePicker datePicker = (DatePicker)editableNode;

							if (datePicker.getValue() != null)
							{
								values.add(String.valueOf(datePicker.getValue().toEpochDay()));
							}
							else
							{
								values.add("");
							}
						}
						else
						{
							TextField textField = (TextField)editableNode;
							values.add(textField.getText());
						}
					}
				}
			}
		}

		// Build query:
		int columnIndex = 0;
		LinkedList<String> wheres = new LinkedList<>();
		StringBuilder queryBuilder = new StringBuilder("SELECT \"");
		queryBuilder.append(String.join("\", \"", selectedColumnNames));
		queryBuilder.append("\" FROM \"");
		queryBuilder.append(selectedTableName);
		queryBuilder.append("\"");

		int valuesSum = 0;

		for (String value : values)
		{
			valuesSum += value.length();
		}

		if (valuesSum > 0)
		{
			queryBuilder.append(" WHERE ");

			for (String value : values)
			{
				String columnName = selectedColumnNames.get(values.indexOf(value));

				if (value.length() > 0)
				{
					wheres.add(String.format("\"%s\"=\"%s\"", columnName, value));
				}
			}

			queryBuilder.append(String.join(" AND ", wheres));
		}

		// Execute query and push results to Report model:
		final String query = queryBuilder.toString();
		LinkedList<LinkedList<String>> results = DatabaseHandler.executeQuery(Constants.SESSION_MANAGER.getCurrentSession(),
			query);

		ObservableList<String> observableColumnNames = FXCollections.observableArrayList(selectedColumnNames);
		ObservableList<LinkedList<String>> observableData = FXCollections.observableArrayList(results);

		reportModel.setData(observableData);
		reportModel.setColumnNames(observableColumnNames);

		mainController.bindReportTableView();

		Stage window = (Stage)this.button_generateReport.getScene().getWindow();
		window.close();
	}
}