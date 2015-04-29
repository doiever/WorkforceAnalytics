package com.alliancedata.workforceanalytics;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import java.io.File;

/**
 * Contains static, application-wide, non-changing data.
 */
public final class Constants
{
	// region UI Properties
	public static final String APPLICATION_NAME = "Workforce Analytics";
	public static final int DEFAULT_SCENE_WIDTH = 1024;
	public static final int DEFAULT_SCENE_HEIGHT = 768;
	// endregion

	// region Views
	public static final String INITIAL_VIEW = "/views/InitialView.fxml";
	public static final String MAIN_VIEW = "/views/MainView.fxml";
	// endregion

	// region Sessions
	public static final String CONFIGURATION_DIRECTORY = System.getProperty("user.home") + "/.wfa";
	public static final String SESSIONS_DIRECTORY = CONFIGURATION_DIRECTORY + "/sessions";
	public static final String SESSION_MANAGER_FILE = CONFIGURATION_DIRECTORY + "/SessionManager.bin";
	public static final SessionManager SESSION_MANAGER = SessionManager.getInstance();
	// endregion

	// region Miscellaneous
	public static final String RUNTIME_LIBRARY_PATH = "target/lib";
	public static final File INITIAL_IMPORT_DATA_DIRECTORY = new File(System.getProperty("user.home") + "/Desktop");
	public static final EventHandler<WindowEvent> CLOSE_EVENT_HANDLER = new EventHandler<WindowEvent>() {
		@Override
		public void handle(WindowEvent event) {
			// Move current session to previous session:
			SESSION_MANAGER.setPreviousSession(Constants.SESSION_MANAGER.getCurrentSession());
			SESSION_MANAGER.setCurrentSession(null);

			// Serialize session manager:
			SESSION_MANAGER.serialize();

			Platform.exit();
		}
	};
	public static final String PRIMARY_KEY_ATTRIBUTES = "PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE";
	// endregion
}
