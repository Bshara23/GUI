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
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class RequestDetailsEvaluatorGUIController implements Initializable {

	@FXML
    private Canvas canvasRight;

    @FXML
    private Canvas canvasLeft;


    @FXML
    private VBox vbLoadRequestDetails;

    @FXML
    private HBox hbSendExecutionDetails;

    @FXML
    private VBox vbEvaluationReport;


	@Override
	public void initialize(URL location, ResourceBundle resources) {

		
		// Apply the effects for the canvas
		RequestDetailsUserController.applyCanvasEffects(canvasRight, canvasLeft);
		ControllerSwapper.loadAnchorContent(vbLoadRequestDetails, FxmlNames.REQUEST_DETAILS);

		hbSendExecutionDetails.setCursor(Cursor.HAND);
		ControllerManager.setEffect(hbSendExecutionDetails, CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		ControllerManager.setOnHoverEffect(hbSendExecutionDetails, CommonEffects.REQUESTS_TABLE_ELEMENT_BLUE,
				CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		
		
		ControllerSwapper.loadAnchorContent(vbEvaluationReport, FxmlNames.EVALUATION_REPORT_PAGE_1);

	}

}
