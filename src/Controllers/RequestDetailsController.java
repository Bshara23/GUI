package Controllers;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

import ClientLogic.Client;
import Controllers.Logic.CommonEffects;
import Controllers.Logic.ControllerManager;
import Entities.ChangeRequest;
import Protocol.Command;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class RequestDetailsController implements Initializable {

	private static final String GET_FULL_NAME_OF_SYSTEM_USER = "GetFullNameOfSystemUser";

	@FXML
	private AnchorPane apLoadRequestDetails;

	@FXML
	private HBox hbInformationContainer;

	@FXML
	private Text txtRequestID;

	@FXML
	private Text txtRelatedInfoSystem;

	@FXML
	private Text txtIssuedBy;

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
	private HBox hbHide;

	@FXML
	private Text txtShowHide;

	@FXML
	private HBox hbEditRequestDetails;

	private ObservableList<Node> nodes;
	private boolean swapper = false;
	private ChangeRequest changeRequest;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nodes = FXCollections.observableArrayList(hbInformationContainer.getChildren());
		changeRequest = ListOfRequestsForTreatmentController.lastSelectedRequest;

		loadFields();

		hbInformationContainer.setPrefHeight(20);
		hbInformationContainer.getChildren().clear();
		txtShowHide.setText("Show");
		ControllerManager.setEffect(hbHide, CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);

		hbHide.setCursor(Cursor.HAND);

		// ControllerManager.setOnHoverEffect(hbHide,
		// CommonEffects.REQUESTS_TABLE_ELEMENT_BLUE,
		// CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);

		hbEditRequestDetails.setCursor(Cursor.HAND);
		ControllerManager.setEffect(hbEditRequestDetails, CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		ControllerManager.setOnHoverEffect(hbEditRequestDetails, CommonEffects.REQUESTS_TABLE_ELEMENT_BLUE,
				CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);

		hbHide.setOnMousePressed(event -> {

			swapper = !swapper;
			if (swapper) {
				hbInformationContainer.setPrefHeight(289);
				hbInformationContainer.getChildren().setAll(nodes);
				txtShowHide.setText("Hide");
				ControllerManager.setEffect(hbHide, CommonEffects.REQUESTS_TABLE_ELEMENT_BLUE);
			} else {
				hbInformationContainer.setPrefHeight(20);
				hbInformationContainer.getChildren().clear();
				txtShowHide.setText("Show");
				ControllerManager.setEffect(hbHide, CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);

			}
			// hbInformationContainer.getChildren().setAll(nodes);
			// System.out.println("DDDD");

		});

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

			Client.getInstance().requestWithListener(Command.getFullNameByUsername, srMsg -> {

				if (srMsg.getCommand() == Command.getFullNameByUsername) {
					
					String fullName = (String) srMsg.getAttachedData()[0];
					txtIssuedBy.setText(fullName);
					Client.removeMessageRecievedFromServer(GET_FULL_NAME_OF_SYSTEM_USER);
				
				}
				
				
			}, GET_FULL_NAME_OF_SYSTEM_USER, changeRequest.getUsername());
		}
	}

}
