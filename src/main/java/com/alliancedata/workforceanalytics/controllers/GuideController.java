package com.alliancedata.workforceanalytics.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Jake on 4/30/2015.
 */
public class GuideController implements Initializable {

    // region View components
    @FXML
    public Button guide_ok;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void hyperlink_CloseOK(ActionEvent actionEvent) {
        Stage stage = (Stage) guide_ok.getScene().getWindow();
        stage.close();
    }
}
