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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EvaluationReportController implements Initializable {
	@FXML
	private HBox hbBackPage;

	@FXML
	private HBox hbSendEvalReport;

	@FXML
	private HBox hbNextPage;

	@FXML
	private VBox vbEvalReportPage1Container;
	
	@FXML
	private VBox vbEvalReportPage2Container;

	private ArrayList<Node> nodes;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

//		nodes = new ArrayList<Node>();
//		nodes.add(hbBackPage);
//		nodes.add(hbNextPage);
//		nodes.add(hbSendEvalReport);
//
//		for (Node node : nodes) {
//
//			node.setCursor(Cursor.HAND);
//			ControllerManager.setEffect(node, CommonEffects.REQUESTS_TABLE_ELEMENT_GRAY);
//			ControllerManager.setOnHoverEffect(node, CommonEffects.REQUESTS_TABLE_ELEMENT_BLUE,
//					CommonEffects.REQUESTS_TABLE_ELEMENT_GRAY);
//
//		}
//
//		hbBackPage.setOnMousePressed(event -> {
//			ControllerSwapper.loadAnchorContent(vbEvalReportPage1Container, FxmlNames.EVALUATION_REPORT_PAGE_1);
//		});
//
//		hbNextPage.setOnMousePressed(event -> {
//			ControllerSwapper.loadAnchorContent(vbEvalReportPage1Container, FxmlNames.EVALUATION_REPORT_PAGE_2);
//
//		});
//
//		hbSendEvalReport.setOnMousePressed(event -> {
//
//		});

	}

}
