package com.alliancedata.workforceanalytics.models;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.LinkedList;

public class Report
{
	private final ListProperty<String> columnNames = new SimpleListProperty<>(FXCollections.observableArrayList());
	private final ListProperty<LinkedList<String>> data = new SimpleListProperty<>(FXCollections.observableArrayList());

	public ListProperty columnNamesProperty()
	{
		return this.columnNames;
	}

	public ListProperty dataProperty()
	{
		return this.data;
	}

	public ObservableList<String> getColumnNames()
	{
		return this.columnNames.getValue();
	}

	public ObservableList<LinkedList<String>> getData()
	{
		return this.data.getValue();
	}

	public void setColumnNames(ObservableList<String> columnNames)
	{
		this.columnNames.set(columnNames);
	}

	public void setData(ObservableList<LinkedList<String>> data)
	{
		this.data.set(data);
	}
}
