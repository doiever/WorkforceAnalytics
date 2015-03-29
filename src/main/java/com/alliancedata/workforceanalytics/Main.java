package com.alliancedata.workforceanalytics;

import com.almworks.sqlite4java.SQLite;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Handles application initialization and displays the first View.
 */
public class Main extends Application
{
    public static void main(String[] args) throws Exception
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        // Load external libraries:
        loadExternalLibraries();

        // Load view into stage and show main window:
        FXMLLoader loader = new FXMLLoader();
        Parent rootNode = (Parent)loader.load(getClass().getResourceAsStream(Constants.MAIN_VIEW));
        Scene scene = new Scene(rootNode, Constants.DEFAULT_SCENE_WIDTH, Constants.DEFAULT_SCENE_HEIGHT);

        stage.setTitle(Constants.APPLICATION_NAME);
        stage.setScene(scene);
        stage.show();
    }

    private static void loadExternalLibraries()
    {
        SQLite.setLibraryPath(Constants.LIBRARY_PATH);
    }
}
