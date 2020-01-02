package Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import Controllers.Logic.CommonEffects;
import Controllers.Logic.ControllerManager;
import Controllers.Logic.FxmlNames;
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {

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
		
		
		
		AppManager.safeUpdate("awdawfwa4234", ()->{
			// TODO: intorpelate effect
			//imgWarningRequestedTimeExtension.setEffect(C);
		});
	}

}
