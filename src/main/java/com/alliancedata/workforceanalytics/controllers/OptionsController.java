package com.alliancedata.workforceanalytics.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.application.Application.*;

/**
 * Created by Jake on 5/1/2015.
 */
public class OptionsController implements Initializable
{
	@FXML public Button op_ok;
	@FXML public Button op_nv;
	@FXML public Button op_hc;

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
	}

	public void button_okclose(ActionEvent actionEvent)
	{
		Stage stage = (Stage)op_ok.getScene().getWindow();
		stage.close();
	}

	public void button_normal(ActionEvent actionEvent)
	{
		setUserAgentStylesheet(STYLESHEET_MODENA);
	}

	public void button_greycontrast(ActionEvent actionEvent)
	{
		setUserAgentStylesheet(STYLESHEET_CASPIAN);
	}
}
