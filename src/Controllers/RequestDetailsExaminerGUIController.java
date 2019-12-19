package Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import Controllers.Logic.CommonEffects;
import Controllers.Logic.ControllerManager;
import Controllers.Logic.FxmlNames;
import Utility.ControllerSwapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class RequestDetailsExaminerGUIController implements Initializable {

	@FXML
	private Canvas canvasRight;

	@FXML
	private Canvas canvasLeft;

	@FXML
	private Text lblExaminationTitle;

	@FXML
	private HBox hbPressToConfirm;

	@FXML
	private HBox hbPressToSendFailureDetails;

	@FXML
	private VBox vbLoadRequestDetails;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Apply the effects for the canvas
		RequestDetailsUserController.applyCanvasEffects(canvasRight, canvasLeft);

		ControllerManager.setEffect(hbPressToConfirm, CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		ControllerManager.setEffect(hbPressToSendFailureDetails, CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);

		hbPressToConfirm.setCursor(Cursor.HAND);

		

		ControllerManager.setOnHoverEffect(hbPressToConfirm, CommonEffects.REQUEST_DETAILS_BUTTON_GREEN,
				CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);

		hbPressToConfirm.setOnMousePressed(event -> {

		});

		hbPressToSendFailureDetails.setCursor(Cursor.HAND);

	
		ControllerManager.setOnHoverEffect(hbPressToSendFailureDetails, CommonEffects.REQUEST_DETAILS_BUTTON_RED,
				CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);

		hbPressToSendFailureDetails.setOnMousePressed(event -> {

		});

		ControllerSwapper.loadAnchorContent(vbLoadRequestDetails, FxmlNames.REQUEST_DETAILS);

	}

}
