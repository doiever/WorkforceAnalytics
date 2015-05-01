package com.alliancedata.workforceanalytics.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class AboutController implements Initializable
{
	// region View components
	@FXML public Button button_ok;
	// endregion

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
	}

	public void button_ok_onAction(ActionEvent event)
	{
		Stage stage = (Stage)button_ok.getScene().getWindow();
		stage.close();
	}

}
