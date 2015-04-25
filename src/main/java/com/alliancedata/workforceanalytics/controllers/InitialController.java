package com.alliancedata.workforceanalytics.controllers;

import com.alliancedata.workforceanalytics.Constants;
import com.alliancedata.workforceanalytics.SessionManager;
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
import java.text.SimpleDateFormat;
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
	private final StringProperty lastSessionTextProperty = new SimpleStringProperty("");
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
		label_lastSession.textProperty().bind(lastSessionTextProperty);

		// TODO: Load previous session
		// SessionManager.previousSessionProperty().setValue(null);

		// Update lastSessionTextProperty with previous session's date:
		Date previousSessionDate = new Date(); // SessionManager.previousSessionProperty().getValue().getDate();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String previousSessionDateString = dateFormat.format(previousSessionDate);
		String previousSessionText = String.format("It looks like your last session was on %s.", previousSessionDateString);
		lastSessionTextProperty.setValue(previousSessionText);
    }

	public void button_startNewSession_onAction(ActionEvent event)
	{
		// Create a new session:
		Session newSession = SessionManager.createSession();
		SessionManager.currentSessionProperty().setValue(newSession);

		this.loadMainView();
	}

	public void button_usePreviousSession_onAction(ActionEvent event)
	{
		// Use previous session as current session:
		SessionManager.currentSessionProperty().setValue(previousSession);

		this.loadMainView();
	}

	public void button_exit_onAction(ActionEvent event)
	{
		Platform.exit();
	}

	/**
	 * Closes the Initial view and opens the Main view as a new stage.
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
			mainStage.setScene(scene);
			mainStage.show();
			initialStage.close();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
			// TODO
		}
	}
}
