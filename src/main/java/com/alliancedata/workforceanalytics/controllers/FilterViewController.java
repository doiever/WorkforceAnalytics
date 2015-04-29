package com.alliancedata.workforceanalytics.controllers;

import java.sql.*;
import java.io.File;
import java.util.ArrayList;
import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteConstants;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class FilterViewController {

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

    SQLiteConnection db;

    public FilterViewController(SQLiteConnection database){

        db = database;

    }

    public void FilterViewParameters(String TableName){

        String SQL;



        grid_JobChange.setVisible(false);

    }

}
