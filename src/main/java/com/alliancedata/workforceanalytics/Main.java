package com.alliancedata.workforceanalytics;

import com.alliancedata.workforceanalytics.controllers.InitialController;
import com.alliancedata.workforceanalytics.models.Session;
import com.almworks.sqlite4java.SQLite;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;

/**
 * Handles application initialization and displays the first View.
 */
public class Main extends Application
{
    public static void main(String[] args) throws Exception
    {
	    String s = Constants.SESSION_MANAGER_FILE;

	    // Ensure SESSIONS_DIRECTORY exists and is writable:
	    if (!new File(Constants.SESSIONS_DIRECTORY).exists())
	    {
			File sessionsDirectory = new File(Constants.SESSIONS_DIRECTORY);

		    try
		    {
			    sessionsDirectory.mkdirs();
		    }
		    catch (SecurityException ex)
		    {
			    StringBuilder messageBuilder = new StringBuilder("[FATAL] Unable to write to configuration directory located at \"");
			    messageBuilder.append(sessionsDirectory.getAbsolutePath());
			    messageBuilder.append("\".");

			    Alert alert = new Alert(Alert.AlertType.ERROR);
			    alert.setTitle("Security error");
			    alert.setHeaderText(null);
			    alert.setContentText(messageBuilder.toString());
			    alert.initStyle(StageStyle.UTILITY);
			    alert.showAndWait();

			    ex.printStackTrace();
			    Platform.exit();
		    }
	    }

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        // Load external libraries:
        loadExternalLibraries();

	    FXMLLoader loader = new FXMLLoader();

	    // If no previous session exists, skip prompt to use previous session and show Main view:
	    if (Constants.SESSION_MANAGER.getPreviousSession() == null)
	    {
		    // Create a new session:
		    Session newSession = Constants.SESSION_MANAGER.createSession();
		    Constants.SESSION_MANAGER.setCurrentSession(newSession);

		    // Load Main view in a new stage and show it:
		    try
		    {
			    Stage mainStage = new Stage();
			    Parent rootNode = (Parent)loader.load(getClass().getResourceAsStream(Constants.MAIN_VIEW));
			    Scene scene = new Scene(rootNode);

			    mainStage.setTitle(Constants.APPLICATION_NAME);
			    mainStage.setOnCloseRequest(Constants.CLOSE_EVENT_HANDLER);
			    mainStage.setScene(scene);
			    mainStage.show();
		    }
		    catch (IOException ex)
		    {
			    // Can't find Constants.MAIN_VIEW file or initialize Main View; definitely fatal.
			    Alert alert = new Alert(Alert.AlertType.ERROR);
			    alert.setTitle("Load Error");
			    alert.setHeaderText(null);
			    alert.setContentText("[FATAL] Unable to load Main View!");
			    alert.initStyle(StageStyle.UTILITY);
			    alert.showAndWait();

			    ex.printStackTrace();
			    Platform.exit();
		    }
	    }
	    else
	    {
		    // Load Initial view in a new stage and show it:
		    try
		    {
			    Parent rootNode = (Parent)loader.load(getClass().getResourceAsStream(Constants.INITIAL_VIEW));
			    Scene scene = new Scene(rootNode);

			    stage.setTitle(Constants.APPLICATION_NAME);
			    stage.setResizable(false);
			    stage.setScene(scene);
			    InitialController.initialStage = stage;

			    stage.show();
		    }
		    catch (IOException ex)
		    {
			    // Can't find Constants.INITIAL_VIEW file or initialize Main View; definitely fatal.
			    Alert alert = new Alert(Alert.AlertType.ERROR);
			    alert.setTitle("Load Error");
			    alert.setHeaderText(null);
			    alert.setContentText("[FATAL] Unable to load Initial View!");
			    alert.initStyle(StageStyle.UTILITY);
			    alert.showAndWait();

			    ex.printStackTrace();
			    Platform.exit();
		    }
	    }
    }

    private static void loadExternalLibraries()
    {
        SQLite.setLibraryPath(Constants.RUNTIME_LIBRARY_PATH);
    }
}
