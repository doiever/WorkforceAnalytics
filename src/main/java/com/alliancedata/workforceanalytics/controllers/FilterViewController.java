package com.alliancedata.workforceanalytics.controllers;

import com.alliancedata.workforceanalytics.Constants;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.LinkedHashSet;
import java.util.ResourceBundle;

public class FilterViewController implements Initializable
{

    //::////////////////////////////////////////////////////////////////////////////////
    //::CONSTANTS
    //::////////////////////////////////////////////////////////////////////////////////

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

    //::////////////////////////////////////////////////////////////////////////////////
    //::VIEW COMPONENTS
    //::////////////////////////////////////////////////////////////////////////////////

    @FXML public CheckBox grid_ID;
    @FXML public CheckBox grid_Pay;
    @FXML public CheckBox grid_CompType;
    @FXML public CheckBox grid_STD;
    @FXML public CheckBox grid_FTE;
    @FXML public CheckBox grid_Org;

    @FXML public CheckBox grid_Eff;
    @FXML public CheckBox grid_Dept;
    @FXML public CheckBox grid_CostCenter;
    @FXML public CheckBox grid_Local;
    @FXML public CheckBox grid_Job;
    @FXML public CheckBox grid_LOB;

    @FXML public CheckBox grid_Acq;
    @FXML public CheckBox grid_Product;
    @FXML public CheckBox grid_SLT;
    @FXML public CheckBox grid_Headcount;
    @FXML public CheckBox grid_Action;
    @FXML public CheckBox grid_Reason;

    @FXML public CheckBox grid_Skillset;
    @FXML public CheckBox grid_YOS;
    @FXML public CheckBox grid_EffPer;
    @FXML public CheckBox grid_RptLevel;
    @FXML public CheckBox grid_Super;
    @FXML public CheckBox grid_Tenure;

    @FXML public CheckBox grid_Direct;
    @FXML public CheckBox grid_TotalReport;
    @FXML public CheckBox grid_DeptLevel;
    @FXML public CheckBox grid_OrgUnit;
    @FXML public CheckBox grid_WFH;

    @FXML public CheckBox grid_JobChange;
    @FXML public CheckBox grindHandT;
    @FXML public CheckBox grid_AreaExper;


    //::////////////////////////////////////////////////////////////////////////////////
    //::DEFINITIONS
    //::////////////////////////////////////////////////////////////////////////////////


	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		FilterViewParameters("Activity");
	}

    private void trueOptionsAllInvisible(){

        grid_ID.setVisible(false);
        grid_Pay.setVisible(false);
        grid_CompType.setVisible(false);
        grid_STD.setVisible(false);
        grid_FTE.setVisible(false);
        grid_Org.setVisible(false);

        grid_Eff.setVisible(false);
        grid_Dept.setVisible(false);
        grid_CostCenter.setVisible(false);
        grid_Local.setVisible(false);
        grid_Job.setVisible(false);
        grid_LOB.setVisible(false);

        grid_Acq.setVisible(false);
        grid_Product.setVisible(false);
        grid_SLT.setVisible(false);
        grid_Headcount.setVisible(false);
        grid_Action.setVisible(false);
        grid_Reason.setVisible(false);

        grid_Skillset.setVisible(false);
        grid_YOS.setVisible(false);
        grid_EffPer.setVisible(false);
        grid_RptLevel.setVisible(false);
        grid_Super.setVisible(false);
        grid_Tenure.setVisible(false);

        grid_Direct.setVisible(false);
        grid_TotalReport.setVisible(false);
        grid_DeptLevel.setVisible(false);
        grid_OrgUnit.setVisible(false);
        grid_WFH.setVisible(false);

        grid_JobChange.setVisible(false);
        grindHandT.setVisible(false);
        grid_AreaExper.setVisible(false);

    }

    public void FilterViewParameters(String TableName) {

        try {

            //true all options in the view to invisible
            //We will turn them on as we find them.
            trueOptionsAllInvisible();

            //Initial query statement
	        @NotNull LinkedHashSet<String> columns = Constants.SESSION_MANAGER.getCurrentSession().getDatabaseHandler().getColumnNames(TableName);

            int columnCount = columns.size();

            //true options in view to visible as we find them.
            for (String columnName : columns)
            {
                switch (columnName)
                {
                    case ID:                          grid_ID.setVisible(true);                break;
                    case HEADCOUNT_ID:                grid_Headcount.setVisible(true);         break;
                    case PAY_STATUS:                  grid_Pay.setVisible(true);               break;
                    case REG_TEMP:                                                            continue;
                    case ORGANIZATION_RELATION:       grid_Org.setVisible(true);               break;
                    case COMP_TYPE:                   grid_CompType.setVisible(true);          break;
                    case STD_HOURS_PER_WEEK:          grid_STD.setVisible(true);               break;
                    case FTE_STD_WFA:                 grid_FTE.setVisible(true);               break;
                    case EFFECTIVE_DATE:              grid_Eff.setVisible(true);               break;
                    case COSTCENTER:                  grid_CostCenter.setVisible(true);        break;
                    case LOB_WFA:                     grid_LOB.setVisible(true);               break;
                    case DEPT_CODE:                                                           continue;
                    case DEPARTMENT:                  grid_Dept.setVisible(true);              break;
                    case COUNTRY:                                                             continue;
                    case STATE:                                                               continue;
                    case WORK_LOCATION:                                                       continue;
                    case REGION_WFA:                                                          continue;
                    case JOB_COOE:                    grid_Job.setVisible(true);               break;
                    case JOB_LEVEL_WFA:                                                       continue;
                    case START_DATE:                                                          continue;
                    case REHIRE_DATE:                                                         continue;
                    case SERVICE_DATE:                                                        continue;
                    case TERM_DATE:                                                           continue;
                    case LAST_DATE_WORKED:                                                    continue;
                    case ACQUISITION_DATE:            grid_Acq.setVisible(true);               break; //two acquisition
                    case ACQUISITION_NAME:            grid_Acq.setVisible(true);               break; //two acquisition
                    case PRODUCT_LOB_WFA:             grid_Product.setVisible(true);           break; // two products
                    case PRODUCT_GROUP_WFA:           grid_Product.setVisible(true);           break; // two products
                    case PRODUCT_WFA:                 grid_Product.setVisible(true);           break; // two products
                    case AREA_OF_EXPERTISE_WFA:                                               continue;
                    case SLT_INDICATOR:               grid_SLT.setVisible(true);               break;
                    case PAY_TYPE_WFA:                                                        continue;
                    case HEADCOUNT:                   grid_Headcount.setVisible(true);         break;
                    case ACTION_DESCRIPTION:          grid_Action.setVisible(true);            break;
                    case REASON_DESCRIPTION:          grid_Reason.setVisible(true);            break;
                    case TERMINATION_CATEGORY_WFA:                                            continue;
                    case HIRE_CATEGORY:                                                       continue;
                    case JOB_CHANGE_CATEGORY:         grid_JobChange.setVisible(true);         break;
                    case SKILLSET_WFA:                grid_Skillset.setVisible(true);          break;
                    case EFF_YEAR:                    grid_Eff.setVisible(true);               break; // three dates
                    case EFF_YEAR_QUARTER:            grid_Eff.setVisible(true);               break; // three dates
                    case EFF_YEAR_MONTH:              grid_Eff.setVisible(true);               break; // three dates
                    case YOS:                         grid_YOS.setVisible(true);               break; //two YOS
                    case YOS_WFA:                     grid_YOS.setVisible(true);               break; //two YOS
                    case ADS_TENURE:                  grid_Tenure.setVisible(true);            break; //two tenure
                    case ADS_TENURE_WFA:              grid_Tenure.setVisible(true);            break; //two tenure
                    case L0_SUPV_ID:                                                          continue;
                    case L1_SUPV_ID:                                                          continue;
                    case L2_SUPV_ID:                                                          continue;
                    case L3_SUPV_ID:                                                          continue;
                    case L4_SUPV_ID:                                                          continue;
                    case L5_SUPV_ID:                                                          continue;
                    case L6_SUPV_ID:                                                          continue;
                    case L7_SUPV_ID:                                                          continue;
                    case L8_SUPV_ID:                                                          continue;
                    case L9_SUPV_ID:                                                          continue;
                    case L10_SUPV_ID:                                                         continue;
                    case L11_SUPV_ID:                                                         continue;
                    case SUPV_ID_CHAIN:               grid_Super.setVisible(true);             break;
                    case RpT_LEVEL:                   grid_RptLevel.setVisible(true);          break;
                    case DIRECT_REPORTS:              grid_Direct.setVisible(true);            break;//two directs
                    case DIRECT_REPORTS_WFA:          grid_Direct.setVisible(true);            break;//two directs
                    case TOTAL_REPORTS:               grid_TotalReport.setVisible(true);       break;//two total reports
                    case TOTAL_REPORTS_WFA:           grid_TotalReport.setVisible(true);       break;//two total reports
                    case L0_DEPARTMENT_NAME:          grid_DeptLevel.setVisible(true);         break;//7 dept level
                    case L1_DEPARTMENT_NAME:          grid_DeptLevel.setVisible(true);         break;//7 dept level
                    case L2_DEPARTMENT_NAME:          grid_DeptLevel.setVisible(true);         break;//7 dept level
                    case L3_DEPARTMENT_NAME:          grid_DeptLevel.setVisible(true);         break;//7 dept level
                    case L4_DEPARTMENT_NAME:          grid_DeptLevel.setVisible(true);         break;//7 dept level
                    case L5_DEPARTMENT_NAME:          grid_DeptLevel.setVisible(true);         break;//7 dept level
                    case L6_DEPARTMENT_NAME:          grid_DeptLevel.setVisible(true);         break;//7 dept level
                    case ORG_UNIT_WFA:                grid_OrgUnit.setVisible(true);           break;
                    case WFH_WFA:                     grid_WFH.setVisible(true);               break;
                    default:
                }
            }

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}