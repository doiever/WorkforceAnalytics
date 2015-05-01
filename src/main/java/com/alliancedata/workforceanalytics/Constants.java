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
	public static final String FILTER_VIEW = "/views/FilterView.fxml";
	public static final String ABOUT_VIEW = "/views/AboutView.fxml";
	public static final String GUIDE_VIEW = "/views/UserGuideView.fxml";
	public static final String DATA_SUMMARY_VIEW = "/views/DataSummaryView.fxml";
	public static final String OPTIONS_VIEW = "/views/OptionsView.fxml";
	public static final String TREND_VIEW = "/views/TrendView.fxml";
	// endregion

	// region Sessions
	public static final String CONFIGURATION_DIRECTORY = getConfigurationDirectory();
	public static final String SESSIONS_DIRECTORY = CONFIGURATION_DIRECTORY + "/sessions";
	public static final String SESSION_MANAGER_FILE = CONFIGURATION_DIRECTORY + "/SessionManager.bin";
	public static final SessionManager SESSION_MANAGER = SessionManager.getInstance();
	// endregion

	// region Miscellaneous
	public static final String RUNTIME_LIBRARY_PATH = "target/lib";
	public static final String PRIMARY_KEY_ATTRIBUTES = "PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE";
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
	// endregion

	/**
	 * Gets a safe, writable, platform-independent configuration directory.
	 * @return A string containing the absolute path to the platform's configuration directory.
	 */
	private static String getConfigurationDirectory()
	{
		String home = System.getenv("APPDATA");

		if (home == null || home.length() == 0)
		{
			home = System.getProperty("user.home");
		}

		File CONFIG_HOME = new File(home, ".wfa").getAbsoluteFile();
		CONFIG_HOME.mkdirs();

		return CONFIG_HOME.getAbsolutePath();
	}
}
