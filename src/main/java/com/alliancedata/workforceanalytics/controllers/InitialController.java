package com.alliancedata.workforceanalytics.controllers;

import com.alliancedata.workforceanalytics.Constants;
import com.alliancedata.workforceanalytics.models.Session;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Controller class for the Initial view.
 */
public class InitialController implements Initializable
{
	// region Fields
	public static Stage initialStage;
	private static Session previousSession;
	private final StringProperty previousSessionTextProperty = new SimpleStringProperty("");
	// endregion

	// region View components
	@FXML public GridPane gridPane_main;
	@FXML public Label label_lastSession;
	@FXML public Button button_usePreviousSession;
	@FXML public Button button_startNewSession;
	@FXML public Button button_exit;
	// endregion

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		label_lastSession.textProperty().bind(previousSessionTextProperty);

		// Update previousSessionTextProperty with previous session's date:
		Date previousSessionDate = Constants.SESSION_MANAGER.getPreviousSession().getDate();
		DateFormat dateFormat = DateFormat.getDateTimeInstance();
		String previousSessionDateString = dateFormat.format(previousSessionDate);
		String previousSessionText = String.format("It looks like your most recent session was started on %s.",
			previousSessionDateString);

		previousSessionTextProperty.setValue(previousSessionText);
	}

	public void button_startNewSession_onAction(ActionEvent event)
	{
		// Create a new session:
		Session newSession = Constants.SESSION_MANAGER.createSession();
		Constants.SESSION_MANAGER.setCurrentSession(newSession);

		this.loadMainView();
	}

	public void button_usePreviousSession_onAction(ActionEvent event)
	{
		// Use previous session as current session:
		Constants.SESSION_MANAGER.setCurrentSession(Constants.SESSION_MANAGER.getPreviousSession());

		this.loadMainView();
	}

	public void button_exit_onAction(ActionEvent event)
	{
		Platform.exit();
	}

	/**
	 * Closes the Initial view and opens the Main view in a new stage.
	 */
	public void loadMainView()
	{
		FXMLLoader loader = new FXMLLoader();

		try
		{
			Stage mainStage = new Stage();
			Parent rootNode = (Parent)loader.load(getClass().getResourceAsStream(Constants.MAIN_VIEW));
			Scene scene = new Scene(rootNode);

			mainStage.setTitle(Constants.APPLICATION_NAME);
			mainStage.setOnCloseRequest(Constants.CLOSE_EVENT_HANDLER);
			mainStage.setScene(scene);
			mainStage.show();
			initialStage.close();
		}
		catch (IOException ex)
		{
			// TODO: Can't find Constants.MAIN_VIEW file.
			ex.printStackTrace();
		}
	}
}
