package Controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Controllers.Logic.CommonEffects;
import Controllers.Logic.ControllerManager;
import Controllers.Logic.FxmlNames;
import Utility.ControllerSwapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class EvaluationReportReviewPage3Controller implements Initializable {

	@FXML
	private AnchorPane vbEvalReportPage3Container;

	@FXML
	private HBox hbRequestData;

	@FXML
	private HBox hbAccept;

	@FXML
	private HBox hbDecline;

	@FXML
	private HBox hbBackPage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		hbRequestData.setCursor(Cursor.HAND);
		hbAccept.setCursor(Cursor.HAND);
		hbDecline.setCursor(Cursor.HAND);
		hbBackPage.setCursor(Cursor.HAND);

		ControllerManager.setEffect(hbRequestData, CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		ControllerManager.setOnHoverEffect(hbRequestData, CommonEffects.REQUEST_DETAILS_BUTTON_BLUE,
				CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		
		ControllerManager.setEffect(hbAccept, CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		ControllerManager.setOnHoverEffect(hbAccept, CommonEffects.REQUEST_DETAILS_BUTTON_GREEN,
				CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		
		ControllerManager.setEffect(hbDecline, CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		ControllerManager.setOnHoverEffect(hbDecline, CommonEffects.REQUEST_DETAILS_BUTTON_RED,
				CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		
		ControllerManager.setEffect(hbBackPage, CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		ControllerManager.setOnHoverEffect(hbBackPage, CommonEffects.REQUEST_DETAILS_BUTTON_BLUE,
				CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);

	
		// TODO: set it to the static anchor of the main page
		hbBackPage.setOnMousePressed(event -> {
			ControllerSwapper.loadAnchorContent(vbEvalReportPage3Container, FxmlNames.EVALUATION_REPORT_REVIEW_PAGE_2);

		});

	}

}
