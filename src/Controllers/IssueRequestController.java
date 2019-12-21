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
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class IssueRequestController implements Initializable {

    @FXML
    private HBox hbAttachFiles;

    @FXML
    private HBox hbIssueRequest1;

    @FXML
    private Canvas canvasRight;

    @FXML
    private Canvas canvasLeft;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Apply the effects for the canvas
		RequestDetailsUserController.applyCanvasEffects(canvasRight, canvasLeft);

		hbIssueRequest1.setCursor(Cursor.HAND);
		ControllerManager.setEffect(hbIssueRequest1, CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		ControllerManager.setOnHoverEffect(hbIssueRequest1, CommonEffects.REQUESTS_TABLE_ELEMENT_BLUE,
				CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		
		
		hbAttachFiles.setCursor(Cursor.HAND);
		ControllerManager.setEffect(hbAttachFiles, CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		ControllerManager.setOnHoverEffect(hbAttachFiles, CommonEffects.REQUESTS_TABLE_ELEMENT_BLUE,
				CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
	
	}

}



