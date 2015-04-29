package com.alliancedata.workforceanalytics.controllers;

import java.io.IOException;
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

    private void SetOptionsAllInvisible(){

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

            //Set all options in the view to invisible
            //We will turn them on as we find them.
            SetOptionsAllInvisible();

            if (!db.isOpen())   db.open();

            //Initial query statement
            //String SQL = "PRAGMA table_info(" + TableName + ")";
            String SQL = "SELECT * FROM "+TableName;
            SQLiteStatement stmt = db.prepare(SQL);
            int ColumnAmount = stmt.columnCount();

            //Set options in view to visible as we find them.
            for (int i = 0; i < ColumnAmount; i++) {

                String CurrentColumnName = stmt.getColumnName(i).toString();

                switch (CurrentColumnName) {

                    case:   grid_Acq.setVisible(false);
                    break;
                    case:grid_Action.setVisible(false);
                    break;
                    case:
                    default: {

                        UnsupportedOperationException e = new UnsupportedOperationException();
                        throw e;

                    }

                }

            }

            stmt.dispose();

        } catch (Exception e) {

            System.out.println(e);

        }

    }

}