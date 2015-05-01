package com.alliancedata.workforceanalytics.controllers;

import com.alliancedata.workforceanalytics.Constants;
import com.alliancedata.workforceanalytics.models.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.TextArea;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class TrendsViewController implements Initializable {

    @FXML public TextArea textflow_trendreporting;
    @FXML public javafx.scene.control.Button button_termination_cancel;
    @FXML public Button button_termination_analyze;

    //:://////////////////////////////////////////////////////
    //::DEFINITIONS
    //:://////////////////////////////////////////////////////

    public void FindTrendTermination(){

        String TableName = "Activity";
        String SQL;

        SQL = "SELECT AVG(\"Std Hours/Week\") FROM "+TableName+" WHERE \"Action Descr\" = \"Termination\"";
        int WorkWeekAvg = Integer.parseInt(DatabaseHandler.executeQuery(Constants.SESSION_MANAGER.getCurrentSession(), SQL).get(0).get(0));

        SQL = "\"SELECT COUNT(*) FROM \"+TableName+\" WHERE \"Action Descr\" = \"Termination\"";
        int TerminatedAmount = Integer.parseInt(DatabaseHandler.executeQuery(Constants.SESSION_MANAGER.getCurrentSession(), SQL).get(0).get(0));

        SQL = "\"SELECT COUNT(*) FROM \"+TableName+\" WHERE \"Action Descr\" = \"Termination\" AND \"Org Relation\" = \"EMP\"";
        int EMP = Integer.parseInt(DatabaseHandler.executeQuery(Constants.SESSION_MANAGER.getCurrentSession(), SQL).get(0).get(0));

        SQL = "\"SELECT COUNT(*) FROM \"+TableName+\" WHERE \"Action Descr\" = \"Termination\" AND \"Org Relation\" = \"CWR\"";
        int CWR = Integer.parseInt(DatabaseHandler.executeQuery(Constants.SESSION_MANAGER.getCurrentSession(), SQL).get(0).get(0));

        SQL = "SELECT COUNT(DISTINCT \"Work Location_WFA\") FROM Activity WHERE \"Action Descr\" IN (\"Termination\")";
        int WorkLocationAmount = Integer.parseInt(DatabaseHandler.executeQuery(Constants.SESSION_MANAGER.getCurrentSession(), SQL).get(0).get(0));

        SQL = "SELECT \"Work Location_WFA\", COUNT(\"Work Location_WFA\") FROM Activity WHERE \"Action Descr\" IN (\"Termination\") GROUP BY \"Work Location_WFA\"";
        int HighestTerminationAtLocation = 0;
        String HighestTerminationLocation = "";

        LinkedList<LinkedList<String>> rawData;
        rawData = DatabaseHandler.executeQuery(Constants.SESSION_MANAGER.getCurrentSession(), SQL);

        for(int i=0; i<rawData.size(); i++){

            for(int j=0; j<rawData.get(i).size(); j++){

                int CurrentValue = Integer.parseInt(rawData.get(i+1).get(j));
                String CurrentLocation = rawData.get(i).get(j);

                if(HighestTerminationAtLocation < CurrentValue){

                    HighestTerminationAtLocation = CurrentValue;
                    HighestTerminationLocation = CurrentLocation;

                }

            }

        }

        SQL = "SELECT \"Reason Descr\", COUNT(\"Reason Descr\") FROM Activity WHERE \"Action Descr\" IN (\"Termination\") GROUP BY \"Reason Descr\"";
        rawData = DatabaseHandler.executeQuery(Constants.SESSION_MANAGER.getCurrentSession(), SQL);
        String ReasonDescription = "";
        int HighestAmountReason = 0;

        for(int i=0; i<rawData.size(); i++){

            for(int j=0; j<rawData.get(i).size(); j++){

                int CurrentValue = Integer.parseInt(rawData.get(i+1).get(j));
                String CurrentReason = rawData.get(i).get(j);

                if(HighestAmountReason < CurrentValue){

                    HighestAmountReason = CurrentValue;
                    ReasonDescription = CurrentReason;

                }

            }

        }

        String Report = "";

        Report += "Workforce Analytic's Trend: Termination\n";
        Report += "There was "+TerminatedAmount+" termination(s) found.\n";
        Report += EMP+" of them were classified as EMP\n";
        Report += CWR+" of them were classified as CWR\n";
        Report += "The average work week hours for those that were terminated was "+WorkWeekAvg+"\n";
        Report += "The highest amount of termination was "+HighestTerminationAtLocation+" that occurred at location classified as "+HighestTerminationLocation+"\n";
        Report += "The most common reason for termination was "+ReasonDescription+", the amount of employees terminated for this reason was "+HighestAmountReason+"\n";

        textflow_trendreporting.setText(Report);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void Button_Trends_Termination_Cancel(ActionEvent actionEvent) {

        Stage stage = (Stage)button_termination_cancel.getScene().getWindow();
        stage.close();

    }

    public void Button_Trends_Termination_Analyze(ActionEvent actionEvent) {

        FindTrendTermination();

    }

}
