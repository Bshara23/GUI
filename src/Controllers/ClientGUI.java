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
import Controllers.Logic.RequestsType;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
	
	// TODO: make this dynamic
	public static long myID = 5;

	public static String userName = "username2";

	@Override
	public void start(Stage stage) {
		stage.initStyle(StageStyle.UNDECORATED);
		Client.getInstance().initialize("localhost", 5555);

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

		
		
		apHeader.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = getStage().getX() - event.getScreenX();
                yOffset = getStage().getY() - event.getScreenY();
            	apHeader.setCursor(Cursor.CLOSED_HAND);
            	// TODO: add the opacity to the settings
            	getStage().setOpacity(0.8);

            }
        });
		apHeader.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	getStage().setX(event.getScreenX() + xOffset);
            	getStage().setY(event.getScreenY() + yOffset);
            }
        });
		apHeader.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	apHeader.setCursor(Cursor.OPEN_HAND);
            	getStage().setOpacity(1);
            }
        });
		
		apHeader.setOnMouseClicked(event -> {
			if(event.getClickCount() == 2) {
		        getStage().setIconified(true);
			}
		});
	}

	private int index = 0;
	private int size = BlendMode.values().length;

	
	



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
		ListOfRequestsController.requestsType = RequestsType.myRequests;
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
		ListOfRequestsController.requestsType = RequestsType.supervision;
		commondMenuBehavior(apBtnMyRequests, "Requests Treatment", FxmlNames.REQUESTS_LIST);

	}

	private void commondMenuBehavior(AnchorPane ap, String pageName, String fxmlName) {
		selectedMenuElement = ap;
		NavigationBar.clear();
		NavigationBar.next(pageName, fxmlName);
	}

}
