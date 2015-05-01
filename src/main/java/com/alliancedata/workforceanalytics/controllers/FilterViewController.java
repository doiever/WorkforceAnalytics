package com.alliancedata.workforceanalytics.controllers;

import com.alliancedata.workforceanalytics.Constants;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.LinkedHashSet;
import java.util.List;
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
    public static final String JOB_CODE = "Job Code";
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
	@FXML public DatePicker datePicker_endDate;
    @FXML public DatePicker datePicker_startDate;
	@FXML public Button grid_button_Cancel;
	@FXML public Button grid_button_Gen;

    @FXML public CheckBox ChkBox_AllDates;


    //::////////////////////////////////////////////////////////////////////////////////
    //::Class Data
    //::////////////////////////////////////////////////////////////////////////////////

    //Dynamically generated list of parameters for comboboxes
    public ObservableList<String> ParameterListGroupPayPosition = FXCollections.observableArrayList();
    public ObservableList<String> ParameterListGroupDepartmentAndCode = FXCollections.observableArrayList();
    public ObservableList<String> ParameterListGroupLocality = FXCollections.observableArrayList();
    public ObservableList<String> ParameterListGroupJob = FXCollections.observableArrayList();
    public ObservableList<String> ParameterListGroupHireAndTermination = FXCollections.observableArrayList();
    public ObservableList<String> ParameterListGroupAcquisitions = FXCollections.observableArrayList();
    public ObservableList<String> ParameterListGroupEffectivePeriod = FXCollections.observableArrayList();
    public ObservableList<String> ParameterListGroupYOS = FXCollections.observableArrayList();
    public ObservableList<String> ParameterListGroupTenure = FXCollections.observableArrayList();
    public ObservableList<String> ParameterListGroupSupervisor = FXCollections.observableArrayList();
    public ObservableList<String> ParameterListGroupDirectReports = FXCollections.observableArrayList();
    public ObservableList<String> ParameterListGroupTotalReports = FXCollections.observableArrayList();
    public ObservableList<String> ParameterListGroupDepartmentLevels = FXCollections.observableArrayList();
    public ObservableList<String> ParameterListGroupProducts = FXCollections.observableArrayList();

    // Comboboxes generated for each group
    public CheckComboBox<String> PayPositionComboBox;
    public CheckComboBox<String> DepartmentAndCodeComboBox;
    public CheckComboBox<String> LocalityComboBox;
    public CheckComboBox<String> JobComboBox;
    public CheckComboBox<String> HireAndTerminationComboBox;
    public CheckComboBox<String> AcquisitionComboBox;
    public CheckComboBox<String> EffectivePeriodComboBox;
    public CheckComboBox<String> YOSComboBox;
    public CheckComboBox<String> TenureComboBox;
    public CheckComboBox<String> SupervisorComboBox;
    public CheckComboBox<String> DirectReportsComboBox;
    public CheckComboBox<String> TotalReportsComboBox;
    public CheckComboBox<String> DepartmentLevelComboBox;
    public CheckComboBox<String> ProductsComboBox;

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

        PayPositionComboBox.setVisible(false);
        DepartmentAndCodeComboBox.setVisible(false);
        LocalityComboBox.setVisible(false);
        JobComboBox.setVisible(false);
        HireAndTerminationComboBox.setVisible(false);
        AcquisitionComboBox.setVisible(false);
        EffectivePeriodComboBox.setVisible(false);
        YOSComboBox.setVisible(false);
        TenureComboBox.setVisible(false);
        SupervisorComboBox.setVisible(false);
        DirectReportsComboBox.setVisible(false);
        TotalReportsComboBox.setVisible(false);
        DepartmentLevelComboBox.setVisible(false);
        ProductsComboBox.setVisible(false);

    }

    @FXML
    public void CheckAllDateStatus(){

        if(ChkBox_AllDates.isSelected()){

            datePicker_startDate.setVisible(false);
            datePicker_endDate.setVisible(false);

        }
        else{

            datePicker_startDate.setVisible(true);
            datePicker_endDate.setVisible(true);

        }

    }

    private void GenerateComboCheckBox(){

        /*
        Generated dynamically.

        ParameterListGroupPayPosition.add(PAY_TYPE_WFA);
        ParameterListGroupPayPosition.add(PAY_STATUS);
        ParameterListGroupPayPosition.add(REG_TEMP);

        ParameterListGroupDepartmentAndCode.add(DEPT_CODE);
        ParameterListGroupDepartmentAndCode.add(DEPARTMENT);

        ParameterListGroupLocality.add(COUNTRY);
        ParameterListGroupLocality.add(STATE);
        ParameterListGroupLocality.add(WORK_LOCATION);
        ParameterListGroupLocality.add(REGION_WFA);

        ParameterListGroupJob.add(JOB_CODE);
        ParameterListGroupJob.add(JOB_LEVEL_WFA);

        ParameterListGroupHireAndTermination.add(START_DATE);
        ParameterListGroupHireAndTermination.add(REHIRE_DATE);
        ParameterListGroupHireAndTermination.add(SERVICE_DATE);
        ParameterListGroupHireAndTermination.add(TERM_DATE);
        ParameterListGroupHireAndTermination.add(LAST_DATE_WORKED);
        ParameterListGroupHireAndTermination.add(TERMINATION_CATEGORY_WFA);
        ParameterListGroupHireAndTermination.add(HIRE_CATEGORY);

        ParameterListGroupAcquisitions.add(ACQUISITION_DATE);
        ParameterListGroupAcquisitions.add(ACQUISITION_NAME);

        ParameterListGroupProducts.add(PRODUCT_LOB_WFA);
        ParameterListGroupProducts.add(PRODUCT_GROUP_WFA);
        ParameterListGroupProducts.add(PRODUCT_WFA);

        ParameterListGroupHireAndTermination.add(TERMINATION_CATEGORY_WFA);
        ParameterListGroupHireAndTermination.add(HIRE_CATEGORY);

        ParameterListGroupEffectivePeriod.add(EFF_YEAR);
        ParameterListGroupEffectivePeriod.add(EFF_YEAR_QUARTER);
        ParameterListGroupEffectivePeriod.add(EFF_YEAR_MONTH);

        ParameterListGroupYOS.add(YOS);
        ParameterListGroupYOS.add(YOS_WFA);

        ParameterListGroupTenure.add(ADS_TENURE);
        ParameterListGroupTenure.add(ADS_TENURE_WFA);

        ParameterListGroupSupervisor.add(L0_SUPV_ID);
        ParameterListGroupSupervisor.add(L1_SUPV_ID);
        ParameterListGroupSupervisor.add(L2_SUPV_ID);
        ParameterListGroupSupervisor.add(L3_SUPV_ID);
        ParameterListGroupSupervisor.add(L4_SUPV_ID);
        ParameterListGroupSupervisor.add(L5_SUPV_ID);
        ParameterListGroupSupervisor.add(L6_SUPV_ID);
        ParameterListGroupSupervisor.add(L7_SUPV_ID);
        ParameterListGroupSupervisor.add(L8_SUPV_ID);
        ParameterListGroupSupervisor.add(L9_SUPV_ID);
        ParameterListGroupSupervisor.add(L10_SUPV_ID);
        ParameterListGroupSupervisor.add(L11_SUPV_ID);
        ParameterListGroupSupervisor.add(SUPV_ID_CHAIN);

        ParameterListGroupDirectReports.add(DIRECT_REPORTS);
        ParameterListGroupDirectReports.add(DIRECT_REPORTS_WFA);

        ParameterListGroupTotalReports.add(TOTAL_REPORTS);
        ParameterListGroupTotalReports.add(TOTAL_REPORTS_WFA);

        ParameterListGroupDepartmentLevels.add(L0_DEPARTMENT_NAME);
        ParameterListGroupDepartmentLevels.add(L1_DEPARTMENT_NAME);
        ParameterListGroupDepartmentLevels.add(L2_DEPARTMENT_NAME);
        ParameterListGroupDepartmentLevels.add(L3_DEPARTMENT_NAME);
        ParameterListGroupDepartmentLevels.add(L4_DEPARTMENT_NAME);
        ParameterListGroupDepartmentLevels.add(L5_DEPARTMENT_NAME);
        ParameterListGroupDepartmentLevels.add(L6_DEPARTMENT_NAME);
        */

        PayPositionComboBox = new CheckComboBox<String>(ParameterListGroupPayPosition);
        DepartmentAndCodeComboBox = new CheckComboBox<String>(ParameterListGroupDepartmentAndCode);
        LocalityComboBox = new CheckComboBox<String>(ParameterListGroupLocality);
        JobComboBox = new CheckComboBox<String>(ParameterListGroupJob);
        HireAndTerminationComboBox = new CheckComboBox<String>(ParameterListGroupHireAndTermination);
        AcquisitionComboBox = new CheckComboBox<String>(ParameterListGroupAcquisitions);
        ProductsComboBox = new CheckComboBox<String>(ParameterListGroupProducts);
        EffectivePeriodComboBox = new CheckComboBox<String>(ParameterListGroupDepartmentAndCode);
        YOSComboBox = new CheckComboBox<String>(ParameterListGroupYOS);
        TenureComboBox = new CheckComboBox<String>(ParameterListGroupTenure);
        SupervisorComboBox = new CheckComboBox<String>(ParameterListGroupSupervisor);
        DirectReportsComboBox = new CheckComboBox<String>(ParameterListGroupDirectReports);
        TotalReportsComboBox = new CheckComboBox<String>(ParameterListGroupTotalReports);
        DepartmentLevelComboBox = new CheckComboBox<String>(ParameterListGroupDepartmentLevels);

    }

    private boolean GetComboBoxCheckBoxChecked(String CheckBoxName, CheckComboBox<String> ComboBox){

        return ComboBox.getItemBooleanProperty(CheckBoxName).getValue();

    }

    private void SetComboBoxCheckBoxCheckedValue(String CheckBoxName, CheckComboBox<String> ComboBox, boolean Value){

        ComboBox.getItemBooleanProperty(CheckBoxName).setValue(Value);

    }

    private void SetComboBoxCheckBoxVisibility(String CheckBoxName, CheckComboBox<String> ComboBox, boolean Visible){

        if(Visible)     ComboBox.getItems().add(CheckBoxName);
        else            ComboBox.getItems().remove(CheckBoxName);

    }

    private void SetGroupLocality(String ColumnName){

        if(!grid_Local.isVisible())         grid_Local.setVisible(true);
        if(!LocalityComboBox.isVisible())   LocalityComboBox.setVisible(true);

        switch(ColumnName){

            case COUNTRY:           SetComboBoxCheckBoxVisibility(COUNTRY, LocalityComboBox, true);         break;
            case STATE:             SetComboBoxCheckBoxVisibility(STATE, LocalityComboBox, true);           break;
            case WORK_LOCATION:     SetComboBoxCheckBoxVisibility(WORK_LOCATION, LocalityComboBox, true);   break;
            case REGION_WFA:        SetComboBoxCheckBoxVisibility(REGION_WFA, LocalityComboBox, true);      break;

        }

    }

    private void SetGroupDepartmentAndCode(String ColumnName){

        if(!grid_Dept.isVisible())                      grid_Dept.setVisible(true);
        if(!DepartmentAndCodeComboBox.isVisible())      DepartmentAndCodeComboBox.setVisible(true);

        switch(ColumnName){

            case DEPT_CODE:     SetComboBoxCheckBoxVisibility(DEPT_CODE, DepartmentAndCodeComboBox, true);      break;
            case DEPARTMENT:    SetComboBoxCheckBoxVisibility(DEPARTMENT, DepartmentAndCodeComboBox, true);     break;

        }

    }

    private void SetGroupPayPosition(String ColumnName){

        if(!grid_Pay.isVisible())                   grid_Pay.setVisible(true);
        if(!PayPositionComboBox.isVisible())        PayPositionComboBox.setVisible(true);

        switch(ColumnName){

            case PAY_STATUS:    SetComboBoxCheckBoxVisibility(PAY_STATUS, PayPositionComboBox, true);       break;
            case REG_TEMP:      SetComboBoxCheckBoxVisibility(REG_TEMP, PayPositionComboBox, true);         break;
            case PAY_TYPE_WFA:  SetComboBoxCheckBoxVisibility(PAY_TYPE_WFA, PayPositionComboBox, true);     break;

        }

    }

    private void SetGroupJob(String ColumnName){

        if(!grid_Job.isVisible())           grid_Job.setVisible(true);
        if(!JobComboBox.isVisible())        JobComboBox.setVisible(true);

        switch(ColumnName) {

            case JOB_CODE:          SetComboBoxCheckBoxVisibility(JOB_CODE, JobComboBox, true);             break;
            case JOB_LEVEL_WFA:     SetComboBoxCheckBoxVisibility(JOB_LEVEL_WFA, JobComboBox, true);        break;

        }

    }

    private void SetGroupHireAndTermination(String ColumnName){

        if(!grindHandT.isVisible())                         grindHandT.setVisible(true);
        if(!HireAndTerminationComboBox.isVisible())         HireAndTerminationComboBox.setVisible(true);

        switch(ColumnName){

            case TERMINATION_CATEGORY_WFA:                  SetComboBoxCheckBoxVisibility(TERMINATION_CATEGORY_WFA, HireAndTerminationComboBox, true);      break;
            case HIRE_CATEGORY:                             SetComboBoxCheckBoxVisibility(HIRE_CATEGORY, HireAndTerminationComboBox, true);                 break;
            case START_DATE:                                SetComboBoxCheckBoxVisibility(START_DATE, HireAndTerminationComboBox, true);                    break;
            case REHIRE_DATE:                               SetComboBoxCheckBoxVisibility(REHIRE_DATE, HireAndTerminationComboBox, true);                   break;
            case SERVICE_DATE:                              SetComboBoxCheckBoxVisibility(SERVICE_DATE, HireAndTerminationComboBox, true);                  break;
            case TERM_DATE:                                 SetComboBoxCheckBoxVisibility(TERM_DATE, HireAndTerminationComboBox, true);                     break;
            case LAST_DATE_WORKED:                          SetComboBoxCheckBoxVisibility(LAST_DATE_WORKED, HireAndTerminationComboBox, true);              break;

        }

    }

    private void SetGroupAcquisition(String ColumnName){

        if(!grid_Acq.isVisible())                       grid_Acq.setVisible(true);
        if(!AcquisitionComboBox.isVisible())            AcquisitionComboBox.setVisible(true);

        switch(ColumnName){

            case ACQUISITION_DATE:                      SetComboBoxCheckBoxVisibility(ACQUISITION_DATE, AcquisitionComboBox, true);     break;
            case ACQUISITION_NAME:                      SetComboBoxCheckBoxVisibility(ACQUISITION_NAME, AcquisitionComboBox, true);     break;

        }

    }

    private void SetGroupProducts(String ColumnName){

        if(!grid_Product.isVisible())                   grid_Product.setVisible(true);
        if(!ProductsComboBox.isVisible())               ProductsComboBox.setVisible(true);

        switch(ColumnName){

            case PRODUCT_LOB_WFA:                       SetComboBoxCheckBoxVisibility(PRODUCT_LOB_WFA, ProductsComboBox, true);         break;
            case PRODUCT_GROUP_WFA:                     SetComboBoxCheckBoxVisibility(PRODUCT_GROUP_WFA, ProductsComboBox, true);       break;
            case PRODUCT_WFA:                           SetComboBoxCheckBoxVisibility(PRODUCT_WFA, ProductsComboBox, true);             break;

        }

    }

    private void SetGroupEffectivePeriod(String ColumnName){

        if(!grid_EffPer.isVisible())                    grid_EffPer.setVisible(true);
        if(!EffectivePeriodComboBox.isVisible())        EffectivePeriodComboBox.setVisible(true);

        switch(ColumnName){

            case EFF_YEAR:                              SetComboBoxCheckBoxVisibility(EFF_YEAR, EffectivePeriodComboBox, true);             break;
            case EFF_YEAR_QUARTER:                      SetComboBoxCheckBoxVisibility(EFF_YEAR_QUARTER, EffectivePeriodComboBox, true);     break;
            case EFF_YEAR_MONTH:                        SetComboBoxCheckBoxVisibility(EFF_YEAR_MONTH, EffectivePeriodComboBox, true);       break;

        }

    }

    private void SetGroupYOS(String ColumnName){

        if(!grid_YOS.isVisible())           grid_YOS.setVisible(true);
        if(!YOSComboBox.isVisible())        YOSComboBox.setVisible(true);

        switch(ColumnName){

            case YOS:                       SetComboBoxCheckBoxVisibility(YOS, YOSComboBox, true);          break;
            case YOS_WFA:                   SetComboBoxCheckBoxVisibility(YOS_WFA, YOSComboBox, true);      break;

        }

    }

    private void SetGroupTenure(String ColumnName){

        if(!grid_Tenure.isVisible())            grid_Tenure.setVisible(true);
        if(!TenureComboBox.isVisible())         TenureComboBox.setVisible(true);

        switch(ColumnName){

            case ADS_TENURE:                    SetComboBoxCheckBoxVisibility(ADS_TENURE, TenureComboBox, true);            break;
            case ADS_TENURE_WFA:                SetComboBoxCheckBoxVisibility(ADS_TENURE_WFA, TenureComboBox, true);        break;

        }

    }

    private void SetGroupSupervisor(String ColumnName){

        if(!grid_Super.isVisible())                 grid_Super.setVisible(true);
        if(!SupervisorComboBox.isVisible())         SupervisorComboBox.setVisible(true);

        switch(ColumnName){

            case L0_SUPV_ID:                SetComboBoxCheckBoxVisibility(L0_SUPV_ID, SupervisorComboBox, true);        break;
            case L1_SUPV_ID:                SetComboBoxCheckBoxVisibility(L1_SUPV_ID, SupervisorComboBox, true);        break;
            case L2_SUPV_ID:                SetComboBoxCheckBoxVisibility(L2_SUPV_ID, SupervisorComboBox, true);        break;
            case L3_SUPV_ID:                SetComboBoxCheckBoxVisibility(L3_SUPV_ID, SupervisorComboBox, true);        break;
            case L4_SUPV_ID:                SetComboBoxCheckBoxVisibility(L4_SUPV_ID, SupervisorComboBox, true);        break;
            case L5_SUPV_ID:                SetComboBoxCheckBoxVisibility(L5_SUPV_ID, SupervisorComboBox, true);        break;
            case L6_SUPV_ID:                SetComboBoxCheckBoxVisibility(L6_SUPV_ID, SupervisorComboBox, true);        break;
            case L7_SUPV_ID:                SetComboBoxCheckBoxVisibility(L7_SUPV_ID, SupervisorComboBox, true);        break;
            case L8_SUPV_ID:                SetComboBoxCheckBoxVisibility(L8_SUPV_ID, SupervisorComboBox, true);        break;
            case L9_SUPV_ID:                SetComboBoxCheckBoxVisibility(L9_SUPV_ID, SupervisorComboBox, true);        break;
            case L10_SUPV_ID:               SetComboBoxCheckBoxVisibility(L10_SUPV_ID, SupervisorComboBox, true);       break;
            case L11_SUPV_ID:               SetComboBoxCheckBoxVisibility(L11_SUPV_ID, SupervisorComboBox, true);       break;
            case SUPV_ID_CHAIN:             SetComboBoxCheckBoxVisibility(SUPV_ID_CHAIN, SupervisorComboBox, true);     break;

        }

    }

    private void SetGroupDirectReports(String ColumnName){

        if(!grid_Direct.isVisible())                    grid_Direct.setVisible(true);
        if(!DirectReportsComboBox.isVisible())          DirectReportsComboBox.setVisible(true);

        switch(ColumnName){


            case DIRECT_REPORTS:                        SetComboBoxCheckBoxVisibility(DIRECT_REPORTS, DirectReportsComboBox, true);         break;
            case DIRECT_REPORTS_WFA:                    SetComboBoxCheckBoxVisibility(DIRECT_REPORTS_WFA, DirectReportsComboBox, true);     break;

        }

    }

    private void SetGroupTotalReports(String ColumnName){

        if(!grid_TotalReport.isVisible())               grid_TotalReport.setVisible(true);
        if(!TotalReportsComboBox.isVisible())           TotalReportsComboBox.setVisible(true);

        switch(ColumnName){

            case TOTAL_REPORTS:                         SetComboBoxCheckBoxVisibility(TOTAL_REPORTS, TotalReportsComboBox, true);           break;
            case TOTAL_REPORTS_WFA:                     SetComboBoxCheckBoxVisibility(TOTAL_REPORTS_WFA, TotalReportsComboBox, true);       break;

        }

    }

    private void SetGroupDeptLevel(String ColumnName){

        if(!grid_DeptLevel.isVisible())                 grid_DeptLevel.setVisible(true);
        if(!DepartmentLevelComboBox.isVisible())        DepartmentLevelComboBox.setVisible(true);

        switch(ColumnName){

            case L0_DEPARTMENT_NAME:                    SetComboBoxCheckBoxVisibility(L0_DEPARTMENT_NAME, DepartmentLevelComboBox, true);       break;
            case L1_DEPARTMENT_NAME:                    SetComboBoxCheckBoxVisibility(L1_DEPARTMENT_NAME, DepartmentLevelComboBox, true);       break;
            case L2_DEPARTMENT_NAME:                    SetComboBoxCheckBoxVisibility(L2_DEPARTMENT_NAME, DepartmentLevelComboBox, true);       break;
            case L3_DEPARTMENT_NAME:                    SetComboBoxCheckBoxVisibility(L3_DEPARTMENT_NAME, DepartmentLevelComboBox, true);       break;
            case L4_DEPARTMENT_NAME:                    SetComboBoxCheckBoxVisibility(L4_DEPARTMENT_NAME, DepartmentLevelComboBox, true);       break;
            case L5_DEPARTMENT_NAME:                    SetComboBoxCheckBoxVisibility(L5_DEPARTMENT_NAME, DepartmentLevelComboBox, true);       break;
            case L6_DEPARTMENT_NAME:                    SetComboBoxCheckBoxVisibility(L6_DEPARTMENT_NAME, DepartmentLevelComboBox, true);       break;

        }

    }

    public void FilterViewParameters(String TableName) {

        try {

            //Generates combo checkboxes for grouped parameters.
            GenerateComboCheckBox();

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
                    case ID:                          grid_ID.setVisible(true);                     break;
                    case HEADCOUNT_ID:                grid_Headcount.setVisible(true);              break;
                    case PAY_STATUS:                  SetGroupPayPosition(columnName);              break;
                    case REG_TEMP:                    SetGroupPayPosition(columnName);              break;
                    case ORGANIZATION_RELATION:       grid_Org.setVisible(true);                    break;
                    case COMP_TYPE:                   grid_CompType.setVisible(true);               break;
                    case STD_HOURS_PER_WEEK:          grid_STD.setVisible(true);                    break;
                    case FTE_STD_WFA:                 grid_FTE.setVisible(true);                    break;
                    case EFFECTIVE_DATE:              grid_Eff.setVisible(true);                    break;
                    case COSTCENTER:                  grid_CostCenter.setVisible(true);             break;
                    case LOB_WFA:                     grid_LOB.setVisible(true);                    break;
                    case DEPT_CODE:                   SetGroupDepartmentAndCode(columnName);        break;
                    case DEPARTMENT:                  SetGroupDepartmentAndCode(columnName);        break;
                    case COUNTRY:                     SetGroupLocality(columnName);                 break;
                    case STATE:                       SetGroupLocality(columnName);                 break;
                    case WORK_LOCATION:               SetGroupLocality(columnName);                 break;
                    case REGION_WFA:                  SetGroupLocality(columnName);                 break;
                    case JOB_CODE:                    SetGroupJob(columnName);                      break;
                    case JOB_LEVEL_WFA:               SetGroupJob(columnName);                      break;
                    case START_DATE:                  SetGroupHireAndTermination(columnName);       break;
                    case REHIRE_DATE:                 SetGroupHireAndTermination(columnName);       break;
                    case SERVICE_DATE:                SetGroupHireAndTermination(columnName);       break;
                    case TERM_DATE:                   SetGroupHireAndTermination(columnName);       break;
                    case LAST_DATE_WORKED:            SetGroupHireAndTermination(columnName);       break;
                    case TERMINATION_CATEGORY_WFA:    SetGroupHireAndTermination(columnName);       break;
                    case HIRE_CATEGORY:               SetGroupHireAndTermination(columnName);       break;
                    case ACQUISITION_DATE:            SetGroupAcquisition(columnName);              break; //two acquisition
                    case ACQUISITION_NAME:            SetGroupAcquisition(columnName);              break; //two acquisition
                    case PRODUCT_LOB_WFA:             SetGroupProducts(columnName);                 break; // two products
                    case PRODUCT_GROUP_WFA:           SetGroupProducts(columnName);                 break; // two products
                    case PRODUCT_WFA:                 SetGroupProducts(columnName);                 break; // two products
                    case AREA_OF_EXPERTISE_WFA:       grid_AreaExper.setVisible(true);              break;
                    case SLT_INDICATOR:               grid_SLT.setVisible(true);                    break;
                    case PAY_TYPE_WFA:                SetGroupPayPosition(columnName);              break;
                    case HEADCOUNT:                   grid_Headcount.setVisible(true);              break;
                    case ACTION_DESCRIPTION:          grid_Action.setVisible(true);                 break;
                    case REASON_DESCRIPTION:          grid_Reason.setVisible(true);                 break;
                    case JOB_CHANGE_CATEGORY:         grid_JobChange.setVisible(true);              break;
                    case SKILLSET_WFA:                grid_Skillset.setVisible(true);               break;
                    case EFF_YEAR:                    SetGroupEffectivePeriod(columnName);          break; // three dates
                    case EFF_YEAR_QUARTER:            SetGroupEffectivePeriod(columnName);          break; // three dates
                    case EFF_YEAR_MONTH:              SetGroupEffectivePeriod(columnName);          break; // three dates
                    case YOS:                         SetGroupYOS(columnName);                      break; //two YOS
                    case YOS_WFA:                     SetGroupYOS(columnName);                      break; //two YOS
                    case ADS_TENURE:                  SetGroupTenure(columnName);                   break; //two tenure
                    case ADS_TENURE_WFA:              SetGroupTenure(columnName);                   break; //two tenure
                    case L0_SUPV_ID:                  SetGroupSupervisor(columnName);               break;
                    case L1_SUPV_ID:                  SetGroupSupervisor(columnName);               break;
                    case L2_SUPV_ID:                  SetGroupSupervisor(columnName);               break;
                    case L3_SUPV_ID:                  SetGroupSupervisor(columnName);               break;
                    case L4_SUPV_ID:                  SetGroupSupervisor(columnName);               break;
                    case L5_SUPV_ID:                  SetGroupSupervisor(columnName);               break;
                    case L6_SUPV_ID:                  SetGroupSupervisor(columnName);               break;
                    case L7_SUPV_ID:                  SetGroupSupervisor(columnName);               break;
                    case L8_SUPV_ID:                  SetGroupSupervisor(columnName);               break;
                    case L9_SUPV_ID:                  SetGroupSupervisor(columnName);               break;
                    case L10_SUPV_ID:                 SetGroupSupervisor(columnName);               break;
                    case L11_SUPV_ID:                 SetGroupSupervisor(columnName);               break;
                    case SUPV_ID_CHAIN:               SetGroupSupervisor(columnName);               break;
                    case RpT_LEVEL:                   grid_RptLevel.setVisible(true);               break;
                    case DIRECT_REPORTS:              SetGroupDirectReports(columnName);            break;//two directs
                    case DIRECT_REPORTS_WFA:          SetGroupDirectReports(columnName);            break;//two directs
                    case TOTAL_REPORTS:               SetGroupTotalReports(columnName);             break;//two total reports
                    case TOTAL_REPORTS_WFA:           SetGroupTotalReports(columnName);             break;//two total reports
                    case L0_DEPARTMENT_NAME:          SetGroupDeptLevel(columnName);                break;
                    case L1_DEPARTMENT_NAME:          SetGroupDeptLevel(columnName);                break;
                    case L2_DEPARTMENT_NAME:          SetGroupDeptLevel(columnName);                break;
                    case L3_DEPARTMENT_NAME:          SetGroupDeptLevel(columnName);                break;
                    case L4_DEPARTMENT_NAME:          SetGroupDeptLevel(columnName);                break;
                    case L5_DEPARTMENT_NAME:          SetGroupDeptLevel(columnName);                break;
                    case L6_DEPARTMENT_NAME:          SetGroupDeptLevel(columnName);                break;
                    case ORG_UNIT_WFA:                grid_OrgUnit.setVisible(true);                break;
                    case WFH_WFA:                     grid_WFH.setVisible(true);                    break;
                    default:                                                                        break;

                }

            }

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public void hyperlink_Cancel(ActionEvent actionEvent) {
        Stage stage = (Stage) grid_button_Cancel.getScene().getWindow();
        stage.close();
    }
}