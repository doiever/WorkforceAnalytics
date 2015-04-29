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

	//region Activity Column Categories
	public static final String ID = "ID";
	public static final String HEADCOUNT_ID = "Headcount ID";
	public static final String PAY_STATUS = "Pay Status";
	public static final String REG_TEMP = "Reg/Temp";
	public static final String ORGANIZATION_RELATION = "Org Relation";
	public static final String COMP_TYPE = "Comp Type";
	public static final String STD_HOURS_PER_WEEK = "Std Hours/Week";
	public static final String FTE_STD_WFA = "FTE Std_WFA";;
	public static final String EFFECTIVE_DATE = "Eff Date";
	public static final String COSTCENTER = "CostCenter-PS11";
	public static final String LOB_WFA = "LOB_WFA";
	public static final String DEPT_CODE = "Dept Code";
	public static final String DEPARTMENT = "Department";
	public static final String COUNTRY = "Country";
	public static final String STATE = "State";
	public static final String WORK_LOCATION = "Work_Location_WFA";
	public static final String REGION_WFA = "Region_WFA";
	public static final String JOB_COOE = "Job Code";
	public static final String JOB_LEVEL_WFA = "Job Level_WFA";
	public static final String START_DATE = "Start Date";
	public static final String REHIRE_DATE = "Rehire Date";
	public static final String SERVICE_DATE = "Service Date";
	public static final String TERM_DATE = "Term Date";
	public static final String LAST_DATE_WORKED = "Last Date Worked";
	public static final String ACQUISITION_DATE = "Acquisition Date";
	public static final String ACQUISITION_NAME = "Acquisition Name";
	public static final String PRODUCT_LOB_WFA = "Product LOB_WFA";
	public static final String PRODUCT_GROUP_WFA = "Product Group_WFA";
	public static final String PRODUCT_WFA = "Product_WFA";
	public static final String AREA_OF_EXPERTISE_WFA = "Area of Expertise_WFA";
	public static final String SLT_INDICATOR = "SLT Indicator";
	public static final String PAY_TYPE_WFA = "Pay Type_WFA";
	public static final String HEADCOUNT = "Headcount";
	public static final String ACTION_DESCRIPTION = "Action Descr";
	public static final String REASON_DESCRIPTION = "Reson Descr";
	public static final String TERMINATION_CATEGORY_WFA = "Termination Category_WFA";
	public static final String HIRE_CATEGORY = "Hire Category_WFA";
	public static final String JOB_CHANGE_CATEGORY = "Job Change Category_WFA";
	public static final String SKILLSET_WFA = "Skillset_WFA";
	public static final String EFF_YEAR = "Eff Year";
	public static final String EFF_YEAR_QUARTER = "Eff Year-Qtr";
	public static final String EFF_YEAR_MONTH = "Eff Year-Month";
	public static final String YOS = "YOS";
	public static final String YOS_WFA = "YOS_WFA";
	public static final String ADS_TENURE = "ADS Tenure";
	public static final String ADS_TENURE_WFA = "ADS Tenure_WFA";
	public static final String L0_SUPV_ID = "L0 Supv Id";
	public static final String L1_SUPV_ID = "L1 Supv Id";
	public static final String L2_SUPV_ID = "L2 Supv Id";
	public static final String L3_SUPV_ID = "L3 Supv Id";
	public static final String L4_SUPV_ID = "L4 Supv Id";
	public static final String L5_SUPV_ID = "L5 Supv Id";
	public static final String L6_SUPV_ID = "L6 Supv Id";
	public static final String L7_SUPV_ID = "L7 Supv Id";
	public static final String L8_SUPV_ID = "L8 Supv Id";
	public static final String L9_SUPV_ID = "L9 Supv Id";
	public static final String L10_SUPV_ID = "L10 Supv Id";
	public static final String L11_SUPV_ID = "L11 Supv Id";
	public static final String SUPV_ID_CHAIN = "Supv Id Chain";
	public static final String RpT_LEVEL = "Rpt Level";
	public static final String DIRECT_REPORTS = "Direct Reports";
	public static final String DIRECT_REPORTS_WFA = "Direct Reports_WFA";
	public static final String TOTAL_REPORTS = "Total Reports";
	public static final String TOTAL_REPORTS_WFA = "Total Reports_WFA";
	public static final String L0_DEPARTMENT_NAME = "L0 Dept Name";
	public static final String L1_DEPARTMENT_NAME = "L1 Dept Name";
	public static final String L2_DEPARTMENT_NAME = "L2 Dept Name";
	public static final String L3_DEPARTMENT_NAME = "L3 Dept Name";
	public static final String L4_DEPARTMENT_NAME = "L4 Dept Name";
	public static final String L5_DEPARTMENT_NAME = "L5 Dept Name";
	public static final String L6_DEPARTMENT_NAME = "L6 Dept Name";
	public static final String ORG_UNIT_WFA = "Org Unit_WFA";
	public static final String WFH_WFA = "WFH_WFA";
	//end region
}
