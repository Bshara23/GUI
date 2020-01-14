package Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import ClientLogic.Client;
import Controllers.Logic.CommonEffects;
import Controllers.Logic.ControllerManager;
import Controllers.Logic.FxmlNames;
import Controllers.Logic.NavigationBar;
import Entities.ChangeRequest;
import Entities.EvaluationReport;
import Entities.Phase;
import Protocol.Command;
import Protocol.PhaseStatus;
import Utility.ControllerSwapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class RequestDetailsDecisionGUIController implements Initializable {

	private static final String GET_LATEST_EVAL_REPORT = "getLatestEvalReport";

	private static final String REQUEST_MORE_DATE_FOR_DECISION = "requestMoreDateForDecision";

	private static final String ACCEPT_DECISION_PHASE = "AcceptDecisionPhase";

	private static final String DECLINE_DECISION_PHASE = "declineDecisionPhase";
	@FXML
	private VBox vbContainerAll;

	@FXML
	private VBox vbEvaluationReport;

	@FXML
	private HBox hbAgreeOrDeclineBtns;

	@FXML
	private HBox hbAgree;

	@FXML
	private HBox hbDecline;

	@FXML
	private VBox vbRequestMoreDataContainer;

	@FXML
	private TextArea taMoreDataDesc;

	@FXML
	private HBox hbRequestData;

	@FXML
	private HBox hbFullRequestDetails;

	@FXML
	private HBox hbEvaluationReport;

	@FXML
	private HBox hbTimeExtension;

	@FXML
	private Canvas canvasRight;

	@FXML
	private Canvas canvasLeft;

	private ChangeRequest lastRequest;

	private Phase lastPhase;

	private EvaluationReport evalReport;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		
		
		
		
		// Apply the effects for the canvas
		RequestDetailsUserController.applyCanvasEffects(canvasRight, canvasLeft);

		lastRequest = ListOfRequestsForTreatmentController.lastSelectedRequest;
		lastPhase = lastRequest.getPhases().get(0);

		hbFullRequestDetails.setCursor(Cursor.HAND);
		ControllerManager.setEffect(hbFullRequestDetails, CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		ControllerManager.setOnHoverEffect(hbFullRequestDetails, CommonEffects.REQUESTS_TABLE_ELEMENT_BLUE,
				CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);

		hbFullRequestDetails.setOnMousePressed(event -> {

			NavigationBar.next("Request Full Details", FxmlNames.REQUEST_DETAILS);

		});

		hbEvaluationReport.setCursor(Cursor.HAND);
		ControllerManager.setEffect(hbEvaluationReport, CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		ControllerManager.setOnHoverEffect(hbEvaluationReport, CommonEffects.REQUESTS_TABLE_ELEMENT_BLUE,
				CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);

		hbEvaluationReport.setOnMousePressed(event -> {
			EvaluationReportComViewController controller2 = (EvaluationReportComViewController) ControllerSwapper
					.loadContentWithController(vbEvaluationReport, FxmlNames.EVALUATION_REPORT_COM_VIEW);
			
			controller2.setEvaluationReport(evalReport);
			
			
			hbFullRequestDetails.setVisible(false);
			hbTimeExtension.setVisible(false);
			hbEvaluationReport.setVisible(false);
		});

		hbAgree.setCursor(Cursor.HAND);
		ControllerManager.setEffect(hbAgree, CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		ControllerManager.setOnHoverEffect(hbAgree, CommonEffects.REQUEST_DETAILS_BUTTON_GREEN,
				CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);

		hbAgree.setOnMousePressed(event -> {

			onAgreePressed();
		});

		hbDecline.setCursor(Cursor.HAND);
		ControllerManager.setEffect(hbDecline, CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		ControllerManager.setOnHoverEffect(hbDecline, CommonEffects.REQUEST_DETAILS_BUTTON_RED,
				CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);

		hbDecline.setOnMousePressed(event -> {
			onDeclinePressed();
		});

		hbRequestData.setCursor(Cursor.HAND);
		ControllerManager.setEffect(hbRequestData, CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		ControllerManager.setOnHoverEffect(hbRequestData, CommonEffects.REQUEST_DETAILS_BUTTON_BLUE,
				CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);

		hbRequestData.setOnMousePressed(event -> {
			onRequestMoreDataPressed();
		});

		if (ClientGUI.isComitteeHead) {

			hbAgreeOrDeclineBtns.setVisible(true);
			vbRequestMoreDataContainer.setVisible(true);

		} else {
			hbAgreeOrDeclineBtns.setVisible(false);
			vbRequestMoreDataContainer.setVisible(false);
		}

		hbTimeExtension.setVisible(false);

		PhaseStatus phaseStatus = PhaseStatus.valueOfAdvanced(lastPhase.getStatus());
		switch (phaseStatus) {

		case Active_And_Waiting_For_Time_Extension:
		case Active:
			if (phaseStatus != PhaseStatus.Active_And_Waiting_For_Time_Extension) {
				hbTimeExtension.setVisible(true);
				hbTimeExtension.setCursor(Cursor.HAND);
				ControllerManager.setEffect(hbTimeExtension, CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
				ControllerManager.setOnHoverEffect(hbTimeExtension, CommonEffects.REQUESTS_TABLE_ELEMENT_BLUE,
						CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);

				hbTimeExtension.setOnMousePressed(event -> {

					requestTimeExtensionController r = (requestTimeExtensionController) ControllerSwapper
							.loadContentWithController(vbContainerAll, FxmlNames.REQUEST_TIME_EXTENSION);
					r.setPhase(lastPhase);

				});
			}

			break;

		default:

			System.err.println("Error, phase " + lastPhase.getStatus() + " is undefined!");
			break;
		}

		
		Client.getInstance().requestWithListener(Command.getLatestEvalReport, srMsg -> {

			if (srMsg.getCommand() == Command.getLatestEvalReport) {

				evalReport = (EvaluationReport)srMsg.getAttachedData()[0];

				if (evalReport == null) {
					System.err.println("Error, eval report not found");
				}
				Client.removeMessageRecievedFromServer(GET_LATEST_EVAL_REPORT);
			}

		}, GET_LATEST_EVAL_REPORT, lastPhase.getRequestID());
		
		loadPageDetails();

	}

	private void loadPageDetails() {

		EvaluationReport evalR = lastPhase.getEvaluationReport();

	}

	private void onDeclinePressed() {

		lastPhase.setStatus(PhaseStatus.Closed.name());

		Client.getInstance().requestWithListener(Command.declineDecisionPhase, srMsg -> {

			if (srMsg.getCommand() == Command.declineDecisionPhase) {

				ControllerManager.showInformationMessage("Success", "Execution Declined",
						"The request has been declined for execution!", null);

				Client.removeMessageRecievedFromServer(DECLINE_DECISION_PHASE);
				NavigationBar.back(true);

			}

		}, DECLINE_DECISION_PHASE, lastPhase);

	}

	private void onRequestMoreDataPressed() {

		String moreDetailsDescription = taMoreDataDesc.getText();
		if (moreDetailsDescription.compareTo("") == 0) {
			ControllerManager.showErrorMessage("Error", "Missing description", "Please write a description!", null);
			return;
		}

		lastPhase.setStatus(PhaseStatus.Waiting_For_More_Data.nameNo_());

		Client.getInstance().requestWithListener(Command.requestMoreDateForDecision, srMsg -> {

			if (srMsg.getCommand() == Command.requestMoreDateForDecision) {

				ControllerManager.showInformationMessage("Success", "Data Request Sent",
						"A message has been sent to request more data for this request", null);

				Client.removeMessageRecievedFromServer(REQUEST_MORE_DATE_FOR_DECISION);
				NavigationBar.reload();

			}

		}, REQUEST_MORE_DATE_FOR_DECISION, lastPhase, taMoreDataDesc.getText());

	}

	private void onAgreePressed() {
		lastPhase.setStatus(PhaseStatus.Closed.name());

		Client.getInstance().requestWithListener(Command.acceptDecisionPhase, srMsg -> {

			if (srMsg.getCommand() == Command.acceptDecisionPhase) {

				ControllerManager.showInformationMessage("Success", "Execution Accepted",
						"The request has been accepted for execution!", null);

				Client.removeMessageRecievedFromServer(ACCEPT_DECISION_PHASE);
				NavigationBar.back(true);

			}

		}, ACCEPT_DECISION_PHASE, lastPhase);

	}

}
