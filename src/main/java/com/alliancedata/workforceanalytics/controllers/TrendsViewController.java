package com.alliancedata.workforceanalytics.controllers;

import com.alliancedata.workforceanalytics.Constants;
import com.alliancedata.workforceanalytics.models.DatabaseHandler;
import java.util.LinkedList;
import java.util.List;

public class TrendsViewController {

    //:://////////////////////////////////////////////////////
    //::DEFINITIONS
    //:://////////////////////////////////////////////////////

    public void FindTrendQuitting(){

        String TableName = "Activity";

        String SQL = "SELECT * FROM "+TableName+" WHERE \"Action Descr\" = \"Termination\"";

        LinkedList<LinkedList<String>> rawData = DatabaseHandler.executeQuery(Constants.SESSION_MANAGER.getCurrentSession(), SQL);

        int TerminatedAmount = 0;
        int WorkWeekAvg = 0;
        int EMP = 0;
        int CWR = 0;

        for(int i=0; i<rawData.size(); i++){

            String CurrentColumn = rawData.get(i).get(0);
            System.out.println("Column: "+CurrentColumn);

            for(int j=1; j<rawData.get(i).size(); j++){

                String Value = rawData.get(i).get(j);
                System.out.println("Row: "+Value);

                TerminatedAmount++;

                if(CurrentColumn == "Std Hours/Week"){

                    if(WorkWeekAvg == 0)    WorkWeekAvg = Integer.parseInt(Value);
                    else                    WorkWeekAvg = (Integer.parseInt(Value)+WorkWeekAvg)/2;

                }
                else if(CurrentColumn == "Org Relation"){

                    if(Value == "EMP")          EMP++;
                    else if(Value == "CWR")     CWR++;

                }
                else if(CurrentColumn == "Area of Expertise_WFA"){



                }

            }

        }

        SQL = "SELECT COUNT(DISTINCT \"Work Location_WFA\") FROM Activity WHERE \"Action Descr\" IN (\"Termination\")";

        rawData = DatabaseHandler.executeQuery(Constants.SESSION_MANAGER.getCurrentSession(), SQL);

        int WorkLocationAmount = Integer.parseInt(rawData.get(0).get(0));

        SQL = "SELECT \"Work Location_WFA\", COUNT(\"Work Location_WFA\") FROM Activity WHERE \"Action Descr\" IN (\"Termination\") GROUP BY \"Work Location_WFA\"";

        rawData = DatabaseHandler.executeQuery(Constants.SESSION_MANAGER.getCurrentSession(), SQL);

        LinkedList<String> WorkLocations = new LinkedList<String>();
        int WorkLocationAmounts[] = new int[WorkLocationAmount];

        String sHighest;
        int iHighest;
        String sLowest;
        int iLowest;

        for(int i=0; i<rawData.size(); i++){

            String CurrentColumn = rawData.get(i).get(0);
            System.out.println("Column: "+CurrentColumn);

            for(int j=1; j<rawData.get(i).size(); j++){

                String Value = rawData.get(i).get(j);
                System.out.println("Row: "+Value);
                if(CurrentColumn == "Work Location_WFA")    WorkLocations.add(Value);
                if(i == 1)                                  WorkLocationAmounts[j] = Integer.parseInt(Value);

            }

        }



    }

}
