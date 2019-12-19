package Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import Controllers.Logic.FxmlNames;
import Utility.AppManager;
import Utility.ControllerSwapper;
import Utility.Curve;
import Utility.Particle;
import Utility.Graphics.ParticlePlexus;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.VBox;

public class RequestDetailsSupervisorController implements Initializable {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Canvas canvasRight;

	@FXML
	private Canvas canvasLeft;
	@FXML
	private VBox vbLoadRequestDetails;
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		RequestDetailsUserController.applyCanvasEffects(canvasRight, canvasLeft);
		
		ControllerSwapper.loadAnchorContent(vbLoadRequestDetails, FxmlNames.REQUEST_DETAILS);

	}

	
}
