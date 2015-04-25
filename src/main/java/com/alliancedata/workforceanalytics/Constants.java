package com.alliancedata.workforceanalytics;

import java.io.File;

/**
 * Contains static, application-wide, non-changing data.
 */
public class Constants
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
	public static final File SESSIONS_DIRECTORY = new File(System.getProperty("user.home") + "/.wfa/sessions");
	public static final SessionManager SESSION_MANAGER = new SessionManager();
	// endregion

    // region Miscellaneous
    public static final String RUNTIME_LIBRARY_PATH = "target/lib";
    public static final File INITIAL_IMPORT_DATA_DIRECTORY = new File(System.getProperty("user.home") + "/Desktop");
	// endregion
}
