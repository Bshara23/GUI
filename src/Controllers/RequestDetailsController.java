package Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import Controllers.Logic.CommonEffects;
import Controllers.Logic.ControllerManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class RequestDetailsController implements Initializable {

	@FXML
	private AnchorPane apLoadRequestDetails;

	@FXML
	private HBox hbHide;
	
    @FXML
    private HBox hbEditRequestDetails;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		hbHide.setCursor(Cursor.HAND);
		ControllerManager.setEffect(hbHide, CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		ControllerManager.setOnHoverEffect(hbHide, CommonEffects.REQUESTS_TABLE_ELEMENT_BLUE,
				CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		
		
		hbEditRequestDetails.setCursor(Cursor.HAND);
		ControllerManager.setEffect(hbEditRequestDetails, CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		ControllerManager.setOnHoverEffect(hbEditRequestDetails, CommonEffects.REQUESTS_TABLE_ELEMENT_BLUE,
				CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);

	}

}
