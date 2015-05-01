package com.alliancedata.workforceanalytics.controllers;

import com.alliancedata.workforceanalytics.Constants;
import com.alliancedata.workforceanalytics.models.DatabaseHandler;
import java.util.LinkedList;

public class TrendsViewController {

    //:://////////////////////////////////////////////////////
    //::DEFINITIONS
    //:://////////////////////////////////////////////////////

    public void FindTrendQuitting(){

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

        LinkedList<LinkedList<String>> rawData = DatabaseHandler.executeQuery(Constants.SESSION_MANAGER.getCurrentSession(), SQL);

        for(int i=0; i<rawData.size(); i++){

            for(int j=0; k<rawData.get(i).size(); j++){

                int CurrentValue = Integer.parseInt(rawData.get(i+1).get(j));
                String CurrentLocation = rawData.get(i).get(j);

                if(HighestTerminationAtLocation < CurrentValue){

                    HighestTerminationAtLocation = CurrentValue;
                    HighestTerminationLocation = CurrentLocation;

                }

            }

        }

        //SQL = "SELECT COUNT(DISTINCT \"Work Location_WFA\") FROM Activity WHERE \"Action Descr\" IN (\"Termination\")";
        //int WorkLocationAmount = Integer.parseInt(DatabaseHandler.executeQuery(Constants.SESSION_MANAGER.getCurrentSession(), SQL).get(0).get(0));


    }

}
