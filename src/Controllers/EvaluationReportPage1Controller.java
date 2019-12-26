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

public class EvaluationReportPage1Controller implements Initializable {

	@FXML
    private AnchorPane vbEvalReportPage1Container;

    @FXML
    private HBox hbNextPage;



	@Override
	public void initialize(URL location, ResourceBundle resources) {

		
		hbNextPage.setCursor(Cursor.HAND);
		ControllerManager.setEffect(hbNextPage, CommonEffects.REQUESTS_TABLE_ELEMENT_GRAY);
		ControllerManager.setOnHoverEffect(hbNextPage, CommonEffects.REQUESTS_TABLE_ELEMENT_BLUE,
				CommonEffects.REQUESTS_TABLE_ELEMENT_GRAY);
		

		hbNextPage.setOnMousePressed(event -> {
			ControllerSwapper.loadAnchorContent(vbEvalReportPage1Container, FxmlNames.EVALUATION_REPORT_PAGE_2);

		});


	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		
	}

}
