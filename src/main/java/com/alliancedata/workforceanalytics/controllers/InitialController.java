package com.alliancedata.workforceanalytics.controllers;

import com.alliancedata.workforceanalytics.Constants;
import com.alliancedata.workforceanalytics.SessionManager;
import com.alliancedata.workforceanalytics.models.Session;
import javafx.application.Platform;
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
import java.util.ResourceBundle;

/**
 * Controller class for the Initial view.
 */
public class InitialController implements Initializable
{
	// region Fields
	public static Stage previousStage;
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
		// TODO: Put stuff here that needs to run when the view is loaded
    }

	public void button_startNewSession_onAction(ActionEvent event)
	{
		Session newSession = SessionManager.createSession("test session", "nope");
		SessionManager.currentSessionProperty().setValue(newSession);
		this.loadMainView(event);
	}

	public void button_usePreviousSession_onAction(ActionEvent event)
	{
		// TODO: Load previous session
		// Session previousSession = herp derp doo
		// SessionManager.currentSessionProperty().setValue(previousSession);
		this.loadMainView(event);
	}

	public void button_exit_onAction(ActionEvent event)
	{
		Platform.exit();
	}

	public void loadMainView(ActionEvent event)
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
			previousStage.close();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
			// TODO
		}
	}
}
