package Controllers;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

import Controllers.Logic.CommonEffects;
import Controllers.Logic.ControllerManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class RequestDetailsController implements Initializable {

	@FXML
	private AnchorPane apLoadRequestDetails;

	@FXML
	private HBox hbHide;
	
    @FXML
    private HBox hbEditRequestDetails;
    
    @FXML
    private HBox hbInformationContainer;
    
    @FXML
    private Text txtShowHide;
    
    private ObservableList<Node> nodes;
    private boolean swapper = false;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nodes = FXCollections.observableArrayList(hbInformationContainer.getChildren());

		
		hbInformationContainer.setPrefHeight(20);
		hbInformationContainer.getChildren().clear();
		txtShowHide.setText("Show");
		ControllerManager.setEffect(hbHide, CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);


		
		hbHide.setCursor(Cursor.HAND);

		//ControllerManager.setOnHoverEffect(hbHide, CommonEffects.REQUESTS_TABLE_ELEMENT_BLUE,
		//		CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		
		
		hbEditRequestDetails.setCursor(Cursor.HAND);
		ControllerManager.setEffect(hbEditRequestDetails, CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		ControllerManager.setOnHoverEffect(hbEditRequestDetails, CommonEffects.REQUESTS_TABLE_ELEMENT_BLUE,
				CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		
		
		hbHide.setOnMousePressed(event -> {
			
			swapper = !swapper;
			if(swapper) {
				hbInformationContainer.setPrefHeight(289);
				hbInformationContainer.getChildren().setAll(nodes);
				txtShowHide.setText("Hide");
				ControllerManager.setEffect(hbHide, CommonEffects.REQUESTS_TABLE_ELEMENT_BLUE);
			}else {
				hbInformationContainer.setPrefHeight(20);
				hbInformationContainer.getChildren().clear();
				txtShowHide.setText("Show");
				ControllerManager.setEffect(hbHide, CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);


			}
			//hbInformationContainer.getChildren().setAll(nodes);
			//System.out.println("DDDD");

		});

	}

}
