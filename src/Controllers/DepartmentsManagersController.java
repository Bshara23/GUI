package Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import ClientLogic.Client;
import Controllers.Logic.CommonEffects;
import Controllers.Logic.ControllerManager;
import Controllers.Logic.FxmlNames;
import Controllers.Logic.NavigationBar;
import Protocol.Command;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class DepartmentsManagersController implements Initializable {

	@FXML
	private Canvas canvasRight;

	@FXML
	private HBox hbInformationContainer;

	@FXML
	private Text txtBraudeWebsite;

	@FXML
	private Text txtClassroomComputr;

	@FXML
	private Text txtInfoSystem;

	@FXML
	private Text txtLabsAndComputers;

	@FXML
	private Text txtLibrarySystem;

	@FXML
	private ImageView imgMoodle;

	@FXML
	private ImageView imgBraudeWebsite;

	@FXML
	private ImageView imgClassroomComputers;

	@FXML
	private ImageView imgInfoSystem;

	@FXML
	private ImageView imgLabsAndComputers;

	@FXML
	private ImageView imgLibrarySystem;

	@FXML
	private Canvas canvasLeft;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Client.getInstance().requestWithListener(Command.getDepartmentsManagers, srMsg -> {
			
			if (srMsg.getCommand() == Command.getDepartmentsManagers) {
				
				
				
			}
			
		}, "getDepartmentsManagers");
		
		
		buttons();


	}

	private void buttons() {
		imgBraudeWebsite.setCursor(Cursor.HAND);
		ControllerManager.setEffect(imgBraudeWebsite, CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		ControllerManager.setOnHoverEffect(imgBraudeWebsite, CommonEffects.REQUESTS_TABLE_ELEMENT_BLUE,
				CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);

		imgBraudeWebsite.setOnMousePressed(event -> {

			NavigationBar.next("List Of Employees", FxmlNames.LIST_OF_EMPLOYEES_SIMPLE);

		});
		
		
		imgClassroomComputers.setCursor(Cursor.HAND);
		ControllerManager.setEffect(imgClassroomComputers, CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		ControllerManager.setOnHoverEffect(imgClassroomComputers, CommonEffects.REQUESTS_TABLE_ELEMENT_BLUE,
				CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);

		imgClassroomComputers.setOnMousePressed(event -> {

			NavigationBar.next("List Of Employees", FxmlNames.LIST_OF_EMPLOYEES_SIMPLE);

		});

		
		
		imgInfoSystem.setCursor(Cursor.HAND);
		ControllerManager.setEffect(imgInfoSystem, CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		ControllerManager.setOnHoverEffect(imgInfoSystem, CommonEffects.REQUESTS_TABLE_ELEMENT_BLUE,
				CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);

		imgInfoSystem.setOnMousePressed(event -> {

			NavigationBar.next("List Of Employees", FxmlNames.LIST_OF_EMPLOYEES_SIMPLE);

		});

		
		
		imgLabsAndComputers.setCursor(Cursor.HAND);
		ControllerManager.setEffect(imgLabsAndComputers, CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		ControllerManager.setOnHoverEffect(imgLabsAndComputers, CommonEffects.REQUESTS_TABLE_ELEMENT_BLUE,
				CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);

		imgLabsAndComputers.setOnMousePressed(event -> {

			NavigationBar.next("List Of Employees", FxmlNames.LIST_OF_EMPLOYEES_SIMPLE);

		});

		
		
		imgLibrarySystem.setCursor(Cursor.HAND);
		ControllerManager.setEffect(imgLibrarySystem, CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		ControllerManager.setOnHoverEffect(imgLibrarySystem, CommonEffects.REQUESTS_TABLE_ELEMENT_BLUE,
				CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);

		imgLibrarySystem.setOnMousePressed(event -> {

			NavigationBar.next("List Of Employees", FxmlNames.LIST_OF_EMPLOYEES_SIMPLE);

		});

		
		
		imgMoodle.setCursor(Cursor.HAND);
		ControllerManager.setEffect(imgMoodle, CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		ControllerManager.setOnHoverEffect(imgMoodle, CommonEffects.REQUESTS_TABLE_ELEMENT_BLUE,
				CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);

		imgMoodle.setOnMousePressed(event -> {

			NavigationBar.next("List Of Employees", FxmlNames.LIST_OF_EMPLOYEES_SIMPLE);

		});
	}

}
