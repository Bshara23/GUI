package Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import Utility.AppManager;
import Utility.Curve;
import Utility.Particle;
import Utility.Graphics.ParticlePlexus;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;


public class RequestDetailsController implements Initializable {

	
	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Canvas canvasRight;

    @FXML
    private Canvas canvasLeft;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		ParticlePlexus ppRight = new ParticlePlexus(210, 150, 50, canvasRight.getGraphicsContext2D());
		ParticlePlexus ppLeft = new ParticlePlexus(210, 150, 50, canvasLeft.getGraphicsContext2D());

		AppManager.updateUnique("drawCallbackLoop", () -> {
			ppRight.drawCallback();
			ppLeft.drawCallback();

		});
		AppManager.updateUnique("particle speed factor", () -> {
			Particle.globalSpeedFactor = Curve.cubic((Math.cos(AppManager.time * 0) 
					+ Curve.easeInOut(Math.cos(AppManager.time * 2.54), -0.61803398875)));
		});
		
	}

    

    
    
}
