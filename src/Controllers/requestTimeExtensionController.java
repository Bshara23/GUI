package Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import ClientLogic.Client;
import Controllers.Logic.CommonEffects;
import Controllers.Logic.ControllerManager;
import Entities.ChangeRequest;
import Entities.Phase;
import Entities.PhaseTimeExtensionRequest;
import Protocol.Command;
import Utility.ControllerSwapper;
import Utility.DateUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class requestTimeExtensionController implements Initializable {

	private static final String INSERT_TIME_EXTENSION324 = "insertTimeExtension324";

	@FXML
	private VBox vbEvaluationReport;

	@FXML
	private Text txtRequestID;

	@FXML
	private TextField tfNumberOfDays;

	@FXML
	private TextField tfNumberOfHours;

	@FXML
	private Text txtNewDeadline;

	@FXML
	private Text txtOldDeadline;

	@FXML
	private HBox hbSendTimeExtensionRequest;

	@FXML
	private TextArea txtDescription;

	private Phase phase;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		hbSendTimeExtensionRequest.setCursor(Cursor.HAND);
		ControllerManager.setEffect(hbSendTimeExtensionRequest, CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		ControllerManager.setOnHoverEffect(hbSendTimeExtensionRequest, CommonEffects.REQUESTS_TABLE_ELEMENT_BLUE,
				CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);

		hbSendTimeExtensionRequest.setOnMousePressed(event -> {

			if (tfNumberOfDays.getText().length() == 0) {
				ControllerManager.showErrorMessage("Error", "Number of days not set", "Please select a number of days",
						null);
				return;
			}

			if (tfNumberOfHours.getText().length() == 0) {
				ControllerManager.showErrorMessage("Error", "Number of hours not set",
						"Please select a number of hours", null);
				return;
			}

			int requestedDays = Integer.parseInt(tfNumberOfDays.getText());
			int requestedHours = Integer.parseInt(tfNumberOfHours.getText());

			if (requestedDays < 0) {
				ControllerManager.showErrorMessage("Error", "Negative number of days",
						"Please select a positive number of days", null);
				return;
			}

			if (requestedHours < 0) {
				ControllerManager.showErrorMessage("Error", "Negative number of hours",
						"Please select a positive number of hours", null);
				return;
			}

			if (requestedDays + requestedHours == 0) {
				ControllerManager.showErrorMessage("Error", "Request time is 0",
						"Please select a valid number of hours and/or days", null);
				return;
			}

			String description = txtDescription.getText();

			PhaseTimeExtensionRequest phaseTimeExtensionRequest = new PhaseTimeExtensionRequest(phase.getPhaseID(),
					requestedDays, requestedHours, description);

			Client.getInstance().requestWithListener(Command.insertTimeExtension, srMsg -> {

				if (srMsg.getCommand() == Command.insertTimeExtension) {

					if ((boolean) srMsg.getAttachedData()[0]) {

						int days = (int) srMsg.getAttachedData()[1];
						int hours = (int) srMsg.getAttachedData()[2];

						ControllerManager.showInformationMessage("Success", "Time Extension",
								"Your request time extension of " + days + " days and " + hours
										+ " hours has been send to the supervisor, please wait for a resonse in the coming days",
								null);

					} else {
						System.err.println("returned false from Command.insertTimeExtension");
					}

					Client.removeMessageRecievedFromServer(INSERT_TIME_EXTENSION324);
				}

			}, INSERT_TIME_EXTENSION324, phaseTimeExtensionRequest);
		});

		ControllerManager.setTextFieldToNumbersOnly(tfNumberOfDays);
		ControllerManager.setTextFieldToNumbersOnly(tfNumberOfHours);

	}

	public void setPhase(Phase lastPhase) {
		phase = lastPhase;
		txtRequestID.setText("[" + phase.getRequestID() + "]");
		txtOldDeadline.setText(DateUtil.toString(phase.getDeadline()));
		txtNewDeadline.setText("Please select a date");
	}

}
