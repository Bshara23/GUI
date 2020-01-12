package Controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import ClientLogic.Client;
import Controllers.Logic.CommonEffects;
import Controllers.Logic.ControllerManager;
import Controllers.Logic.FxmlNames;
import Controllers.Logic.NavigationBar;
import Entities.ChangeRequest;
import Entities.Employee;
import Entities.Phase;
import Protocol.Command;
import Protocol.PhaseStatus;
import Protocol.PhaseType;
import Utility.AppManager;
import Utility.ControllerSwapper;
import Utility.Curve;
import Utility.DateUtil;
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

	private static final String ACCE = "dawdawfw53252365435435435435t5th5463453";

	private static final String REJECT_PHASE_DEADLINE = "RejectPhaseDeadline";

	private static final String CONFIRM_PHASE_DEADLINE = "ConfirmPhaseDeadline";

	private static final String ACTIVATE_EVALUATION_PHASE = "ActivateEvaluationPhase";

	private static final String UPDATE_PHASE_OWNER_FROM_REQUEST_DETAILS_SUPERVISOR = "UpdatePhaseOwnerFromRequestDetailsSupervisor9879";

	private static final String GET_EMPLOYEE_BY_EMPLOYEE_NUMBER = "GetEmployeeByEmployeeNumber";

	private static final String GET_PHASES_OF_REQUEST_WITH_TIME_EXTENSIONS_IF_POSSIBLE = "getPhasesOfRequestWithTimeExtensionsIfPossible";

	@FXML
	private Canvas canvasRight;

	@FXML
	private HBox hbEditPhaseDetails;

	@FXML
	private HBox hbBrowsePhases;

	@FXML
	private Text txtPhaseOwner;

	@FXML
	private Text txtPhaseName;

	@FXML
	private Text txtPhaseStatus;

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
	private HBox hbAssignEvaluatiorContainer;

	@FXML
	private ImageView imgWarningAutoAssign;

	@FXML
	private HBox hbConfirmAutoAssign;

	@FXML
	private HBox hbAssignOtherEmp;

	@FXML
	private HBox hbDeadline;

	@FXML
	private ImageView imgWarningRequestedTimeExtension2;

	@FXML
	private HBox hbConfirmDeadline;

	@FXML
	private HBox hbRejectDeadline;

	@FXML
	private ImageView imgWarningRequestedTimeExtension1;

	@FXML
	private HBox hbConfirmTimeRequestExtension1;

	@FXML
	private HBox hbDeclineRequestTimeExtension1;

	@FXML
	private HBox hbRequestedTimeExtenContainer;

	@FXML
	private ImageView imgWarningRequestedTimeExtension;

	@FXML
	private HBox hbConfirmTimeRequestExtension;

	@FXML
	private HBox hbDeclineRequestTimeExtension;

	@FXML
	private HBox hbFullRequestDetails;

	@FXML
	private Canvas canvasLeft;

	private ChangeRequest changeRequest;

	private ArrayList<Phase> requestedPhases;
	private int currentPhaseIndex = 0;
	private Employee currentEmployeeOfSelectedPhase;

	private boolean reload = false;

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		changeRequest = ListOfRequestsForTreatmentController.lastSelectedRequest;

		RequestDetailsUserController.applyCanvasEffects(canvasRight, canvasLeft);

		setButtonsBehaviors();

		setClientObservers();

	}

	private void setButtonsBehaviors() {
		hbFullRequestDetails.setCursor(Cursor.HAND);
		ControllerManager.setEffect(hbFullRequestDetails, CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		ControllerManager.setOnHoverEffect(hbFullRequestDetails, CommonEffects.REQUESTS_TABLE_ELEMENT_BLUE,
				CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);

		hbFullRequestDetails.setOnMousePressed(event -> {

			NavigationBar.next("Request Full Details", FxmlNames.REQUEST_DETAILS);

		});

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

		hbAssignOtherEmp.setOnMousePressed(event -> {
			NavigationBar.next("Select Employee", FxmlNames.LIST_OF_EMPLOYEES_SIMPLE);
		});

		ListOfEmployeesSimpleController.setOnRowDoubleClicked(emp -> {
			txtAssignedByName.setText(emp.getFirstName() + " " + emp.getLastName());

			Client.getInstance().requestWithListener(Command.updatePhaseOwner, srMsg -> {
				if (srMsg.getCommand() == Command.updatePhaseOwner) {

					NavigationBar.back(true);

					ControllerManager.showInformationMessage("Success", "Employee Selected",
							"The employee has been set to the current phase, press the confirm button to confirm your selection",
							null);
					// OK, so now the confirm button will change the phase status to active and send
					// the manager of the phase a message.
					Client.removeMessageRecievedFromServer(UPDATE_PHASE_OWNER_FROM_REQUEST_DETAILS_SUPERVISOR);
				}
			}, UPDATE_PHASE_OWNER_FROM_REQUEST_DETAILS_SUPERVISOR, requestedPhases.get(currentPhaseIndex),
					emp.getEmpNumber());

		});

		hbConfirmAutoAssign.setOnMousePressed(event -> {
			requestedPhases.get(currentPhaseIndex)
					.setStatus(PhaseStatus.Waiting_To_Set_Time_Required_For_Evaluation.nameNo_());

			Client.getInstance().requestWithListener(Command.setEvaluationPhaseToWaitingToSetTime, srMsg -> {
				if (srMsg.getCommand() == Command.setEvaluationPhaseToWaitingToSetTime) {

					hbAssignEvaluatiorContainer.setVisible(false);

					ControllerManager.showInformationMessage("Success", "Evaluator has been confirmed",
							"A message has been sent to the evaluator to choose a time to evaluate this request.",
							null);

					Client.removeMessageRecievedFromServer(ACTIVATE_EVALUATION_PHASE);
				}
			}, ACTIVATE_EVALUATION_PHASE, requestedPhases.get(currentPhaseIndex));
		});

		hbConfirmDeadline.setOnMousePressed(event -> {
			requestedPhases.get(currentPhaseIndex)
					.setDeadline(requestedPhases.get(currentPhaseIndex).getEstimatedTimeOfCompletion());

			requestedPhases.get(currentPhaseIndex).setStatus(PhaseStatus.Active.name());

			Client.getInstance().requestWithListener(Command.confirmPhaseDeadline, srMsg -> {
				if (srMsg.getCommand() == Command.confirmPhaseDeadline) {

					hbDeadline.setVisible(false);

					ControllerManager.showInformationMessage("Success", "Deadline confirmed",
							"The deadline for this request has been confirmed!", null);

					Client.removeMessageRecievedFromServer(CONFIRM_PHASE_DEADLINE);
				}
			}, CONFIRM_PHASE_DEADLINE, requestedPhases.get(currentPhaseIndex));
		});

		hbRejectDeadline.setOnMousePressed(event -> {
			requestedPhases.get(currentPhaseIndex).setEstimatedTimeOfCompletion(DateUtil.NA);
			requestedPhases.get(currentPhaseIndex)
					.setStatus(PhaseStatus.Waiting_To_Set_Time_Required_For_Phase.nameNo_());
			
			Client.getInstance().requestWithListener(Command.rejectPhaseDeadline, srMsg -> {
				if (srMsg.getCommand() == Command.rejectPhaseDeadline) {

					hbDeadline.setVisible(false);

					ControllerManager.showInformationMessage("Success", "Deadline rejected",
							"The deadline for this request has been rejected!", null);

					Client.removeMessageRecievedFromServer(REJECT_PHASE_DEADLINE);
				}
			}, REJECT_PHASE_DEADLINE, requestedPhases.get(currentPhaseIndex));
		});

	}

	private void setClientObservers() {
		Client.addMessageRecievedFromServer(ACCE, srMsg -> {
			if (srMsg.getCommand() == Command.acceptPhaseTimeExtensionSupervisor) {

				boolean isSuccess = (boolean) srMsg.getAttachedData()[0];

				if (isSuccess) {
					long requestId = (long) srMsg.getAttachedData()[1];
					ControllerManager.showInformationMessage("Success", "Time extension confirmed",
							"The time extension for request with id " + requestId + " has been successfuly applied!",
							null);

					// remove the phase time extension
					Phase currentPhase = requestedPhases.get(currentPhaseIndex);
					currentPhase.setPhaseTimeExtensionRequest(null);

					NavigationBar.reload();

				} else {
					ControllerManager.showErrorMessage("Error", "An error has occured",
							"Something has gone wrong when adding a time extension for this request", null);
				}

			}
		});

		Client.addMessageRecievedFromServer("dawdawfw5366664646th5463453", srMsg -> {
			if (srMsg.getCommand() == Command.rejectPhaseTimeExtensionSupervisor) {

				boolean isSuccess = (boolean) srMsg.getAttachedData()[0];

				if (isSuccess) {
					long requestId = (long) srMsg.getAttachedData()[1];
					ControllerManager.showInformationMessage("Success", "Time extension rejected",
							"The time extension for request with id " + requestId + " has been successfuly rejected!",
							null);
					hbRequestedTimeExtenContainer.setVisible(false);

					// remove the phase time extension
					Phase currentPhase = requestedPhases.get(currentPhaseIndex);
					currentPhase.setPhaseTimeExtensionRequest(null);
					currentPhase.setStatus(PhaseStatus.Active.name());


				} else {
					ControllerManager.showErrorMessage("Error", "An error has occured",
							"Something has gone wrong when adding a time extension for this request", null);
				}

			}
		});

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

		// if the estimated time of completion has been set
		if (!currentPhase.getEstimatedTimeOfCompletion().equals(DateUtil.NA)) {

			if (currentPhase.getStatus()
					.compareTo(PhaseStatus.Waiting_To_Confirm_Time_Required_For_Phase.nameNo_()) == 0) {
				hbDeadline.setVisible(true);

			} else {
				hbDeadline.setVisible(false);
			}

		} else {
			hbDeadline.setVisible(false);
		}

		Client.getInstance().requestWithListener(Command.getEmployeeByEmployeeNumber, srMsg -> {
			if (srMsg.getCommand() == Command.getEmployeeByEmployeeNumber) {
				// Casting error even tho it works and prints the content

				if (srMsg.getAttachedData()[0] instanceof Employee) {

					currentEmployeeOfSelectedPhase = (Employee) srMsg.getAttachedData()[0];

					txtAssignedByName.setText(currentEmployeeOfSelectedPhase.getFirstName() + " "
							+ currentEmployeeOfSelectedPhase.getLastName());
					Client.removeMessageRecievedFromServer(GET_EMPLOYEE_BY_EMPLOYEE_NUMBER);
				}
			}
		}, GET_EMPLOYEE_BY_EMPLOYEE_NUMBER, currentPhase.getEmpNumber());

		if (currentPhase.getEstimatedTimeOfCompletion().equals(DateUtil.NA)) {
			txtEstimateTimeOfCompletion.setText("Not set yet");

		} else {
			txtEstimateTimeOfCompletion.setText(DateUtil.toString(currentPhase.getEstimatedTimeOfCompletion()));
		}

		if (currentPhase.getDeadline().equals(DateUtil.NA)) {
			txtDeadLine.setText("Not set yet");

		} else {
			txtDeadLine.setText(DateUtil.toString(currentPhase.getDeadline()));
		}

		txtPhaseOwner.setText(getPhaseOwnerLabel(currentPhase.getPhaseName()) + ": ");

		txtPhaseStatus.setText(currentPhase.getStatus());

		if (currentPhase.getTimeOfCompletion().equals(DateUtil.NA)) {
			txtCompletedOnTime.setText("Not set yet");

		} else {
			txtCompletedOnTime.setText(DateUtil.toString(currentPhase.getTimeOfCompletion()));
		}

		txtTimeException.setText(currentPhase.hasTimeException() ? "True" : "False");

		if (currentPhase.getPhaseTimeExtensionRequest() != null) {
			int days = currentPhase.getPhaseTimeExtensionRequest().getRequestedTimeInDays();
			int hours = currentPhase.getPhaseTimeExtensionRequest().getRequestedTimeInHours();
			// Check if this has been time extended
			txtExtendedTime.setText("Days: " + days + " Hours: " + hours);
		} else {
			txtExtendedTime.setText("N/A");
		}

		if (currentPhase.phaseName.equals(PhaseType.Evaluation.name())) {

			if (currentPhase.getStatus().equals(PhaseStatus.Waiting_To_Set_Evaluator.nameNo_())) {

				System.out.println("is evaluation and is waiting");

				hbAssignEvaluatiorContainer.setVisible(true);

			} else {
				hbAssignEvaluatiorContainer.setVisible(false);

			}

			if (!currentPhase.isHasBeenTimeExtended() && currentPhase.getPhaseTimeExtensionRequest() != null) {
				hbRequestedTimeExtenContainer.setVisible(true);

				if (currentPhase.getPhaseTimeExtensionRequest() != null) {
					int days1 = currentPhase.getPhaseTimeExtensionRequest().getRequestedTimeInDays();
					int hours1 = currentPhase.getPhaseTimeExtensionRequest().getRequestedTimeInHours();
					txtRequestedTimeExtension.setText("Days: " + days1 + " Hours: " + hours1);

				} else {
					txtRequestedTimeExtension.setText("N/A");

				}
				// if supervisor has accepted the time request
				hbConfirmTimeRequestExtension.setOnMousePressed(event -> {
					Client.getInstance().request(Command.acceptPhaseTimeExtensionSupervisor, currentPhase);
				});

				// if the supervisor has decline the time request
				hbDeclineRequestTimeExtension.setOnMousePressed(event -> {
					Client.getInstance().request(Command.rejectPhaseTimeExtensionSupervisor, currentPhase);
				});

			} else {
				hbRequestedTimeExtenContainer.setVisible(false);
				txtRequestedTimeExtension.setText("N/A");

			}
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
