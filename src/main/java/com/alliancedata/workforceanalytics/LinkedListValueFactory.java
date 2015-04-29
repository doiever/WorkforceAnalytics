package com.alliancedata.workforceanalytics;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import java.util.LinkedList;

/**
 * ValueFactory class for generating cells deriving from a {@code LinkedList<String>} instead of a concrete
 * POJO.
 */
public class LinkedListValueFactory implements Callback<TableColumn.CellDataFeatures<LinkedList<String>, String>, ObservableValue<String>>
{
	private int rowIndex = 0;

	public LinkedListValueFactory(int rowIndex)
	{
		this.rowIndex = rowIndex;
	}

	@Override
	public ObservableValue<String> call(TableColumn.CellDataFeatures<LinkedList<String>, String> features)
	{
		if (this.rowIndex < 0)
		{
			this.rowIndex = 0;
		}

		if (this.rowIndex > features.getValue().size())
		{
			this.rowIndex = features.getValue().size() - 1;
		}

		return new SimpleStringProperty(features.getValue().get(this.rowIndex));
	}
}