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

public class EvaluationReportReviewPage2Controller implements Initializable {

	@FXML
	private AnchorPane vbEvalReportPage2Container;

	@FXML
	private HBox hbBackPage;

	@FXML
	private HBox hbNextPage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		hbBackPage.setCursor(Cursor.HAND);
		ControllerManager.setEffect(hbBackPage, CommonEffects.REQUESTS_TABLE_ELEMENT_GRAY);
		ControllerManager.setOnHoverEffect(hbBackPage, CommonEffects.REQUESTS_TABLE_ELEMENT_BLUE,
				CommonEffects.REQUESTS_TABLE_ELEMENT_GRAY);
		
		hbNextPage.setCursor(Cursor.HAND);
		ControllerManager.setEffect(hbNextPage, CommonEffects.REQUESTS_TABLE_ELEMENT_GRAY);
		ControllerManager.setOnHoverEffect(hbNextPage, CommonEffects.REQUESTS_TABLE_ELEMENT_BLUE,
				CommonEffects.REQUESTS_TABLE_ELEMENT_GRAY);
		

		hbBackPage.setOnMousePressed(event -> {
			ControllerSwapper.loadAnchorContent(vbEvalReportPage2Container, FxmlNames.EVALUATION_REPORT_REVIEW_PAGE_1);
		});
		hbNextPage.setOnMousePressed(event -> {
			ControllerSwapper.loadAnchorContent(vbEvalReportPage2Container, FxmlNames.EVALUATION_REPORT_REVIEW_PAGE_3);
		});

	}

}
