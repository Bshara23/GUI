package Controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import ClientLogic.Client;
import Controllers.Logic.CommonEffects;
import Controllers.Logic.ControllerManager;
import Protocol.Command;
import Utility.AppManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class SettingsController implements Initializable {

	@FXML
	private HBox hbApplyChanges;

	@FXML
	private Canvas canvasRight;

	@FXML
	private Canvas canvasLeft;
	
	@FXML
	private Button btnDebug;

	@FXML
	private TextField tfDebug;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Apply the effects for the canvas
		RequestDetailsUserController.applyCanvasEffects(canvasRight, canvasLeft);

		hbApplyChanges.setCursor(Cursor.HAND);
		ControllerManager.setEffect(hbApplyChanges, CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		ControllerManager.setOnHoverEffect(hbApplyChanges, CommonEffects.REQUESTS_TABLE_ELEMENT_BLUE,
				CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		
		
//		tfDebug.setOnMousePressed(event -> {
//			ArrayList<String> sss = new ArrayList<String>();
//			sss.add(AppManager.getRnd().nextInt(100) + "");
//			System.out.println("Debugging with " + sss.get(0));
//
//			Client.getInstance().request(Command.debug_simulateBigCalculations, sss);
//			
//		});
		

	}
	
    @FXML
    void onDebug(MouseEvent event) {
    	ArrayList<String> sss = new ArrayList<String>();
		sss.add(AppManager.getRnd().nextInt(100) + "");
		System.out.println("Debugging with " + sss.get(0));
		Client.getInstance().request(Command.debug_simulateBigCalculations, sss);

    }
	
	

}