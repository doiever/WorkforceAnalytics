package com.alliancedata.workforceanalytics;

import com.alliancedata.workforceanalytics.controllers.InitialController;
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

/**
 * Handles application initialization and displays the first View.
 */
public class Main extends Application
{
    public static void main(String[] args) throws Exception
    {
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
			    StringBuilder messagebuilder = new StringBuilder("Unable to write to configuration directory located at \"");
			    messagebuilder.append(sessionsDirectory.getAbsolutePath());
			    messagebuilder.append("\".");

			    Alert alert = new Alert(Alert.AlertType.ERROR);
			    alert.setTitle("Security error");
			    alert.setHeaderText(null);
			    alert.setContentText(messagebuilder.toString());
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

        // Load view into stage and show main window:
        FXMLLoader loader = new FXMLLoader();
        Parent rootNode = (Parent)loader.load(getClass().getResourceAsStream(Constants.INITIAL_VIEW));
        Scene scene = new Scene(rootNode);

	    InitialController.initialStage = stage;

        stage.setTitle(Constants.APPLICATION_NAME);
	    stage.setResizable(false);
	    stage.initStyle(StageStyle.UTILITY);
	    stage.setScene(scene);
        stage.show();
    }

    private static void loadExternalLibraries()
    {
        SQLite.setLibraryPath(Constants.RUNTIME_LIBRARY_PATH);
    }
}
