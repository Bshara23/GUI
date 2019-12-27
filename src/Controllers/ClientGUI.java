package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import ClientLogic.Client;
import Controllers.Logic.CommonEffects;
import Controllers.Logic.ControllerManager;
import Controllers.Logic.FxmlNames;
import Controllers.Logic.NavigationBar;
import Utility.AppManager;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class ClientGUI extends Application implements Initializable {

	@FXML
	private AnchorPane mainAnchor;

	@FXML
	private AnchorPane apMainContent;

	@FXML
	private VBox vbMenu;

	@FXML
	private AnchorPane apBtnLogoMain;

	@FXML
	private AnchorPane apBtnIssueRequest;

	@FXML
	private AnchorPane apBtnMyRequests;

	@FXML
	private AnchorPane apBtnAnalytics;

	@FXML
	private AnchorPane apBtnRequestsTreatment;

	@FXML
	private AnchorPane apBtnEmployees;

	@FXML
	private AnchorPane apBtnMessages;

	@FXML
	private AnchorPane apBtnSettings;

	@FXML
	private AnchorPane apHeader;

	@FXML
	private ImageView imgNavigationBarArrow;

	@FXML
	private HBox hbNavigator;
	


	private AnchorPane selectedMenuElement;

	private ArrayList<Node> apList;

	private static Stage stage;
	
	public static Stage getStage() {
		return stage;
	}
	
	private static double xOffset = 0;
	private static double yOffset = 0;

	@Override
	public void start(Stage stage) {
		stage.initStyle(StageStyle.UNDECORATED);
		Client.getInstance().initialize("10.0.0.6", 5555);

		FXMLLoader loader = new FXMLLoader(getClass().getResource(FxmlNames.GLOBAL_MENUS));
		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException e) {

			e.printStackTrace();
		}
		stage.setScene(new Scene(root));
		stage.setTitle("ICM System");
		stage.show();
		
		ClientGUI.stage = stage;

		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				System.exit(1);
			}
		});

	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		NavigationBar.imgNavigationBarArrow = imgNavigationBarArrow;
		NavigationBar.navigationBar = hbNavigator;
		NavigationBar.apMainContent = apMainContent;

		apList = new ArrayList<Node>();

		apList.add(apBtnLogoMain);

		apList.add(apBtnIssueRequest);
		apList.add(apBtnMessages);
		apList.add(apBtnMyRequests);
		apList.add(apBtnSettings);
		apList.add(apBtnAnalytics);
		apList.add(apBtnEmployees);
		apList.add(apBtnRequestsTreatment);

		for (Node node : apList) {

			ControllerManager.setMouseHoverPressEffects(node, CommonEffects.MENU_ELEMENT_ON_HOVER,
					CommonEffects.MENU_ELEMENT_IDLE, CommonEffects.MENU_ELEMENT_PRESSED, apList, Cursor.HAND);

		}
		ControllerManager.setMouseHoverPressEffects(apBtnLogoMain, CommonEffects.MENU_ELEMENT_ON_HOVER,
				CommonEffects.MENU_ELEMENT_IDLE, CommonEffects.LOGO_SELECT, apList, Cursor.HAND);
		selectedMenuElement = null;
		commondMenuBehavior(apBtnLogoMain, "Home", FxmlNames.HOME);
		ControllerManager.setEffect(apBtnLogoMain, CommonEffects.LOGO_SELECT);

	}

	private int index = 0;
	private int size = BlendMode.values().length;

	
	

	// TODO: not working
	@FXML
	void HeaderSetOnMouseDragged(MouseEvent event) {
		// stage.setX(event.getScreenX() + xOffset);
		// stage.setY(event.getScreenY() + yOffset);
	}

	@FXML
	void headerSetOnMousePressed(MouseEvent event) {
		// xOffset = stage.getX() - event.getScreenX();
		// yOffset = stage.getY() - event.getScreenY();
	}

	@FXML
	void onIssueRequestPress(MouseEvent event) {

		commondMenuBehavior(apBtnIssueRequest, "Issue Request", FxmlNames.ISSUE_REQUEST);

	}

	@FXML
	void onMessagesPress(MouseEvent event) {

		commondMenuBehavior(apBtnMessages, "Messages", FxmlNames.MESSAGES);
	}

	@FXML
	void onMyRequestsPress(MouseEvent event) {

		ListOfRequestsController.disableAllJobs = true;
		ListOfRequestsController.pageHeader = "List of My Requests";

		commondMenuBehavior(apBtnMyRequests, "My Requests", FxmlNames.REQUESTS_LIST);
	}

	@FXML
	void onSettingsPress(MouseEvent event) {

		commondMenuBehavior(apBtnSettings, "Settings", FxmlNames.SETTINGS);

	}

	@FXML
	void onAnalyticsPress(MouseEvent event) {

		commondMenuBehavior(apBtnAnalytics, "Analytics", FxmlNames.ANALYTICS);
	}

	@FXML
	void onEmployeesPress(MouseEvent event) {

		commondMenuBehavior(apBtnEmployees, "Employees", FxmlNames.EMPLOYEES);
	}

	@FXML
	void onLogoMainPress(MouseEvent event) {
		commondMenuBehavior(apBtnLogoMain, "Home", FxmlNames.HOME);
	}

	@FXML
	void onRequestTreatmentPress(MouseEvent event) {

		ListOfRequestsController.disableAllJobs = false;
		ListOfRequestsController.pageHeader = "List of Requests for Treatment";
		commondMenuBehavior(apBtnMyRequests, "Requests Treatment", FxmlNames.REQUESTS_LIST);

	}

	private void commondMenuBehavior(AnchorPane ap, String pageName, String fxmlName) {
		selectedMenuElement = ap;
		NavigationBar.clear();
		NavigationBar.next(pageName, fxmlName);
	}

}
