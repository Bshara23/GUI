package Controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import ClientLogic.Client;
import Controllers.Logic.CommonEffects;
import Controllers.Logic.ControllerManager;
import Controllers.Logic.FxmlNames;
import Entities.ChangeRequest;
import Entities.Employee;
import Entities.Phase;
import Protocol.Command;
import Protocol.PhaseType;
import Utility.AppManager;
import Utility.ControllerSwapper;
import Utility.Curve;
import Utility.Particle;
import Utility.Graphics.ParticlePlexus;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class RequestDetailsSupervisorController implements Initializable {

	private static final String GET_EMPLOYEE_BY_EMPLOYEE_NUMBER = "GetEmployeeByEmployeeNumber";

	private static final String GET_PHASES_OF_REQUEST_WITH_TIME_EXTENSIONS_IF_POSSIBLE = "getPhasesOfRequestWithTimeExtensionsIfPossible";

	@FXML
	private VBox vbLoadRequestDetails;

	@FXML
	private HBox hbEditPhaseDetails;

	@FXML
	private HBox hbBrowsePhases;

	@FXML
	private Text txtPhaseName;

	@FXML
	private Text txtEstimateTimeOfCompletion;

	@FXML
	private Text txtAssignedByName;

	@FXML
	private Text txtDeadLine;

	@FXML
	private Text txtExtendedTime;

	@FXML
	private Text txtCompletedOnTime;

	@FXML
	private Text txtTimeException;

	@FXML
	private Text txtRequestedTimeExtension;

	@FXML
	private ImageView imgWarningAutoAssign;

	@FXML
	private HBox hbConfirmAutoAssign;

	@FXML
	private HBox hbAssignOtherEmp;

	@FXML
	private ImageView imgWarningRequestedTimeExtension;

	@FXML
	private HBox hbConfirmTimeRequestExtension;

	@FXML
	private HBox hbDeclineRequestTimeExtension;

	@FXML
	private Canvas canvasRight;

	@FXML
	private Canvas canvasLeft;

	@FXML
	private Text txtPhaseOwner;

	private ChangeRequest changeRequest = ListOfRequestsForTreatmentController.lastSelectedRequest;

	private ArrayList<Phase> requestedPhases;
	private int currentPhaseIndex = 0;
	private Employee currentEmployeeOfSelectedPhase;

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO: stopped here
		RequestDetailsUserController.applyCanvasEffects(canvasRight, canvasLeft);

		ControllerSwapper.loadAnchorContent(vbLoadRequestDetails, FxmlNames.REQUEST_DETAILS);
		hbEditPhaseDetails.setCursor(Cursor.HAND);
		ControllerManager.setEffect(hbEditPhaseDetails, CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		ControllerManager.setOnHoverEffect(hbEditPhaseDetails, CommonEffects.REQUESTS_TABLE_ELEMENT_BLUE,
				CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		hbBrowsePhases.setCursor(Cursor.HAND);
		ControllerManager.setEffect(hbBrowsePhases, CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		ControllerManager.setOnHoverEffect(hbBrowsePhases, CommonEffects.REQUESTS_TABLE_ELEMENT_BLUE,
				CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);

		hbBrowsePhases.setOnMousePressed(event -> {

			currentPhaseIndex = (currentPhaseIndex + 1) % requestedPhases.size();
			loadPageDetails();

		});

		// Get current phase

//		AppManager.safeUpdate("awdawfwa4234", ()->{
//			// TODO: intorpelate effect
//			//imgWarningRequestedTimeExtension.setEffect(C);
//		});
//
		Client.getInstance().requestWithListener(Command.getPhasesOfRequestWithTimeExtensionsIfPossible, srMsg -> {
			if (srMsg.getCommand() == Command.getPhasesOfRequestWithTimeExtensionsIfPossible) {
				// Casting error even tho it works and prints the content

				if (srMsg.getAttachedData()[0] instanceof List) {

					requestedPhases = (ArrayList<Phase>) srMsg.getAttachedData()[0];

					for (

					Phase phase : requestedPhases) {
						System.out.println(phase);
						if (phase.getPhaseTimeExtensionRequest() != null)
							System.out.println(phase.getPhaseTimeExtensionRequest());
					}

					loadPageDetails();

				}
			}
		}, GET_PHASES_OF_REQUEST_WITH_TIME_EXTENSIONS_IF_POSSIBLE,

				changeRequest.getRequestID());
	}

	private void loadPageDetails() {

		Phase currentPhase = requestedPhases.get(currentPhaseIndex);
		txtPhaseName.setText(currentPhase.getPhaseName());
		txtEstimateTimeOfCompletion.setText(ControllerManager.getDateTime(currentPhase.getEstimatedTimeOfCompletion()));

//		Client.getInstance().requestWithListener(Command.getEmployeeByEmployeeNumber, srMsg -> {
//			if (srMsg.getCommand() == Command.getEmployeeByEmployeeNumber) {
//				// Casting error even tho it works and prints the content
//
//				if (srMsg.getAttachedData()[0] instanceof Employee) {
//
//					currentEmployeeOfSelectedPhase = (Employee) srMsg.getAttachedData()[0];
//
//					txtAssignedByName.setText(currentEmployeeOfSelectedPhase.getFirstName() + " "
//							+ currentEmployeeOfSelectedPhase.getLastName());
//					Client.removeMessageRecievedFromServer(GET_EMPLOYEE_BY_EMPLOYEE_NUMBER);
//				}
//			}
//		}, GET_EMPLOYEE_BY_EMPLOYEE_NUMBER, currentPhase.getEmpNumber());

		txtDeadLine.setText(ControllerManager.getDateTime(currentPhase.getDeadline()));

		txtPhaseOwner.setText(getPhaseOwnerLabel(currentPhase.getPhaseName()) + ": ");

		txtCompletedOnTime.setText(ControllerManager.getDateTime(currentPhase.getTimeOfCompletion()));
		txtTimeException.setText(currentPhase.hasTimeException() ? "True" : "False");

		if (currentPhase.getPhaseTimeExtensionRequest() == null) {
			txtExtendedTime.setText("N/A");

			txtRequestedTimeExtension.setText("N/A");
		} else {
			txtRequestedTimeExtension.setText(
					ControllerManager.getDateTime(currentPhase.getPhaseTimeExtensionRequest().getRequestedTime()));

			// Check if this has been time extended
			txtExtendedTime.setText(currentPhase.hasBeenTimeExtended
					// if yes, then get the date from the time extension request
					? ControllerManager.getDateTime(currentPhase.getPhaseTimeExtensionRequest().getRequestedTime())
					// otherwise write not available.
					: "N/A");
		}

	}

	private String getPhaseOwnerLabel(String phaseName) {
		PhaseType phaseType = PhaseType.valueOf(phaseName);
		switch (phaseType) {

		case Evaluation:

			return "Evaluator";

		case Decision:

			return "The Change Committee";

		case Execution:

			return "Executer";

		case Examination:

			return "Examiner";

		case Closing:
		case Supervision:

			return "Supervisor";

		default:
			System.err.println("Error, phase type not recognize in function [getPhaseOwnerLabel] at class "
					+ getClass().getName());
			return "";
		}

	}

}
