package com.alliancedata.workforceanalytics;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * Contains static application-wide helper methods.
 */
public class Utilities
{
	/**
	 * Gets a file's extension as a String.
	 * @param fileName The full path of the file to inspect.
	 * @return A {@code String} object containing the file's extension (without leading period).
	 */
	public static String getFileExtension(String fileName)
	{
		int dotIndex = fileName.lastIndexOf('.');
		int slashIndex = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
		String extension = "";

		if (dotIndex > slashIndex)
		{
			extension = fileName.substring(dotIndex + 1);
		}

		return extension;
	}

	/**
	 * Shows a JavaFX {@code Dialog} with an expandable {@code TextArea} node; useful for display stack traces and other long messages.
	 * @param alertType     Determines the dialog's icon.
	 * @param title         The title of the dialog window.
	 * @param headerText    The header text; displayed in larger-sized font at the top of the window.
	 * @param contentText   The content text; display in normal-sized font after the header.
	 * @param bodyText      The long text to be displayed in the Dialog's {@code TextArea}.
	 */
	public static void showTextAreaDialog(Alert.AlertType alertType, String title, String headerText, String contentText, String bodyText)
	{
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);

		Label label = new Label("Details:");

		TextArea textArea = new TextArea(bodyText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

//		textArea.setMaxWidth(Double.MAX_VALUE);
//		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane content = new GridPane();
		content.setMaxWidth(Double.MAX_VALUE);
		content.add(label, 0, 0);
		content.add(textArea, 0, 1);

		// Set expandable Exception into the dialog pane:
		alert.getDialogPane().setExpandableContent(content);

		alert.showAndWait();
	}
}
