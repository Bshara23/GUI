package Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import ClientLogic.Client;
import Controllers.Logic.CommonEffects;
import Controllers.Logic.ControllerManager;
import Controllers.Logic.FxmlNames;
import Protocol.Command;
import Utility.ControllerSwapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class RequestDetailsExaminerGUIController implements Initializable {
	
	@FXML
	private VBox vbLoadRequestDetails;

	@FXML
	private HBox hbPressToConfirm;

	@FXML
	private TextArea taFailureDetails;

	@FXML
	private HBox hbPressToSendFailureDetails;

	@FXML
	private Canvas canvasRight;

	@FXML
	private Canvas canvasLeft;

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
			onPressConfirm();
		});

		hbPressToSendFailureDetails.setCursor(Cursor.HAND);

		ControllerManager.setOnHoverEffect(hbPressToSendFailureDetails, CommonEffects.REQUEST_DETAILS_BUTTON_RED,
				CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);

		hbPressToSendFailureDetails.setOnMousePressed(event -> {
			onPressToSendFailure();
		});

		ControllerSwapper.loadAnchorContent(vbLoadRequestDetails, FxmlNames.REQUEST_DETAILS);

	}

	private void onPressToSendFailure() {
	//	Client.getInstance().request(Command.);
		
	}

	private void onPressConfirm() {

		
	}

}
