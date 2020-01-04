package Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import ClientLogic.Client;
import Controllers.Logic.ControllerManager;
import Entities.ChangeRequest;
import Protocol.Command;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class MyIssuedRequestDetailsController implements Initializable {
	
	@FXML
	private Canvas canvasRight;

	@FXML
	private HBox hbInformationContainer;

	@FXML
	private Text txtRequestID;

	@FXML
	private Text txtRelatedInfoSystem;

	@FXML
	private Text txtIssueDate;

	@FXML
	private Text txtRequestDescription;

	@FXML
	private Text txtDescriptionOfCurrentChange;

	@FXML
	private Text txtDescriptionOfRequestedChange;

	@FXML
	private Text txtComments;

	@FXML
	private Text txtAttachedFilesNames;

	@FXML
	private Canvas canvasLeft;

	private ChangeRequest changeRequest;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		RequestDetailsUserController.applyCanvasEffects(canvasRight, canvasLeft);
		changeRequest = ListOfRequestsController.lastSelectedRequest;
		loadFields();
	}

	private void loadFields() {
		if (changeRequest != null) {

			txtRequestID.setText(changeRequest.getRequestID() + "");
			txtComments.setText(changeRequest.getCommentsLT());
			txtDescriptionOfCurrentChange.setText(changeRequest.getDescriptionOfCurrentStateLT());
			txtIssueDate.setText(ControllerManager.getDateTime(changeRequest.getDateOfRequest()));
			txtDescriptionOfRequestedChange.setText(changeRequest.getDescriptionOfRequestedChangeLT());
			txtRelatedInfoSystem.setText(changeRequest.getRelatedInformationSystem());
			txtRequestDescription.setText(changeRequest.getRequestDescriptionLT());

		}
	}

}
