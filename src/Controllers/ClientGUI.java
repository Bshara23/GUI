package Controllers;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.TimeZone;

import ClientLogic.Client;
import Controllers.Logic.CommonEffects;
import Controllers.Logic.ControllerManager;
import Controllers.Logic.FxmlNames;
import Controllers.Logic.NavigationBar;
import Protocol.Command;
import Protocol.PhaseType;
import Utility.Func;
import Utility.SRMessageFunc;
import Utility.VoidFunc;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class ClientGUI extends Application implements Initializable {

	private static final String LOGOUT = "Logout";

	private static final String ALLOW_EXISTING_TABS_ONLY_F_MAIN_MENU = "allowExistingTabsOnlyFMainMenu";

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
	@FXML
    private VBox DropDownMenu;
	private int DropDownMenuActive=0;
	@FXML
	private AnchorPane apHeader1;

 @FXML
    private HBox hboxHelp;

    @FXML
    private Label lblHelp;

    @FXML
    private ImageView imgHelp;

    @FXML
    private HBox hboxSignout;

    @FXML
    private Label lblSignOut;

    @FXML
    private ImageView imgSignOut;

    @FXML
    private HBox hboxExit;

    @FXML
    private Label lblExit;

    @FXML
    private ImageView imgExit;
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
	public static long empNumber = 10;

	private static HashMap<String, VoidFunc> onMenuBtnClickedEvents;
	static {
		onMenuBtnClickedEvents = new HashMap<String, VoidFunc>();
	}

	/**
	 * Adds a function that executes whenever a button from the main menu is clicked
	 * on. This function runs only once and then delete itself from the map.
	 */
	public static void addOnMenuBtnClickedEvent(String key, VoidFunc func) {
		onMenuBtnClickedEvents.remove(key);
		onMenuBtnClickedEvents.put(key, func);
	}

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
		System.out.println("Init: ClientGUI");

		
		
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
		//DropDown menu is closed
		DropDownMenu.getChildren().clear();
		DropDownMenu.setPrefHeight(0);
		apHeader1.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = getStage().getX() - event.getScreenX();
				yOffset = getStage().getY() - event.getScreenY();
				apHeader1.setCursor(Cursor.CLOSED_HAND);
				// TODO: add the opacity to the settings
				getStage().setOpacity(0.8);

			}
		});
		apHeader1.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				getStage().setX(event.getScreenX() + xOffset);
				getStage().setY(event.getScreenY() + yOffset);
			}
		});
		apHeader1.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				apHeader1.setCursor(Cursor.OPEN_HAND);
				getStage().setOpacity(1);
			}
		});

		apHeader1.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2) {
				getStage().setIconified(true);
			}
		});
		DropDownMenu.setOnMouseExited(event->{
			DropDownMenu.getChildren().clear();
    		DropDownMenu.setPrefHeight(0);
    		
    		DropDownMenuActive=0;
    		hboxExit.setOpacity(1);
    		hboxSignout.setOpacity(1);
    		hboxHelp.setOpacity(1);
			
		});
		
		hboxSignout.setCursor(Cursor.HAND);
		ControllerManager.setEffect(hboxSignout, CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		ControllerManager.setOnHoverEffect(hboxSignout, CommonEffects.REQUESTS_TABLE_ELEMENT_BLUE,
				CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		
		hboxExit.setCursor(Cursor.HAND);
		ControllerManager.setEffect(hboxExit, CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		ControllerManager.setOnHoverEffect(hboxExit, CommonEffects.REQUESTS_TABLE_ELEMENT_BLUE,
				CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		hboxHelp.setCursor(Cursor.HAND);
		ControllerManager.setEffect(hboxHelp, CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		ControllerManager.setOnHoverEffect(hboxHelp, CommonEffects.REQUESTS_TABLE_ELEMENT_BLUE,
				CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);

		
//		hboxSignout.setOnMouseEntered(event->{
//			hboxSignout.setOpacity(1);
//			hboxHelp.setOpacity(0.22);
//			hboxExit.setOpacity(0.22);
//		});
//		hboxExit.setOnMouseEntered(event->{
//			hboxExit.setOpacity(1);
//			hboxSignout.setOpacity(0.22);
//			hboxHelp.setOpacity(0.22);
//		});
//		hboxHelp.setOnMouseEntered(event->{
//			hboxHelp.setOpacity(1);
//			hboxSignout.setOpacity(0.22);
//			hboxExit.setOpacity(0.22);
//		});
		allowMenuButtonsByPermissions();
	}

	private void allowMenuButtonsByPermissions() {

		SRMessageFunc allowExistingTabsOnlyF = srMsg -> {

			if (srMsg.getCommand() == Command.getPermissionsData) {

				// get data from server
				boolean isManager = !((boolean) srMsg.getAttachedData()[0]);
				boolean hasAtleastOnePhaseToManage = (boolean) srMsg.getAttachedData()[1];

				ArrayList<Node> newNodesForMenu = new ArrayList<Node>();

				for (Node node : vbMenu.getChildren()) {
					if (node instanceof AnchorPane) {
						if (((AnchorPane) node).getChildren().size() == 2) {
							Node textNode = ((AnchorPane) node).getChildren().get(1);
							String text = ((Label) textNode).getText();
							switch (text) {
							case "Issue a Request":
								// Always add
								newNodesForMenu.add(node);

								break;
							case "My Requests":
								// Always add
								newNodesForMenu.add(node);

								break;
							case "Requests  Treatment":
								// Add only if he has a phase to manage
								if (hasAtleastOnePhaseToManage) {
									newNodesForMenu.add(node);
								}

								break;
							case "Analytics":
								// Add only if the user is the manager
								if (isManager) {
									newNodesForMenu.add(node);
								}
								
								break;
							case "Employees":
								// Add only if the user is the manager
								if (isManager) {
									newNodesForMenu.add(node);
								}
								
								break;
							case "Messages":
								newNodesForMenu.add(node);

								break;
							case "Settings":
								newNodesForMenu.add(node);

								break;
							default:
								System.err.println("Error, case not defined! [allowMenuButtonsByPermissions] at " + getClass().getName());
								break;
							}
						}else {
							// Add the home button to all users
							newNodesForMenu.add(node);
						}
					}
				}

				vbMenu.getChildren().clear();
				vbMenu.getChildren().setAll(FXCollections.observableArrayList(newNodesForMenu));

				// Auto delete after first use
				Client.removeMessageRecievedFromServer(ALLOW_EXISTING_TABS_ONLY_F_MAIN_MENU);
			}

		};

		Client.getInstance().requestWithListener(Command.getPermissionsData, allowExistingTabsOnlyF,
				ALLOW_EXISTING_TABS_ONLY_F_MAIN_MENU, ClientGUI.userName);

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

		commondMenuBehavior(apBtnMyRequests, "Requests Treatment", FxmlNames.LIST_OF_REQUESTS_FOR_TREATMENT);

	}

	private void commondMenuBehavior(AnchorPane ap, String pageName, String fxmlName) {

		for (Entry<String, VoidFunc> func : onMenuBtnClickedEvents.entrySet()) {
			if (func.getValue() != null)
				func.getValue().call();
			// Self delete after running once
			onMenuBtnClickedEvents.remove(func.getKey());
		}
		selectedMenuElement = ap;
		NavigationBar.clear();
		NavigationBar.next(pageName, fxmlName);
	}
	@FXML
    void DropDownMenu(MouseEvent event) {
    	if(DropDownMenuActive==0) {
    		DropDownMenu.getChildren().add(0, hboxHelp);
    		DropDownMenu.getChildren().add(1, hboxSignout);
    		DropDownMenu.getChildren().add(2, hboxExit);
    		DropDownMenu.setPrefHeight(180);
    		
    		DropDownMenuActive=1;
    	}
    	else {
    		DropDownMenu.getChildren().clear();
    		DropDownMenu.setPrefHeight(0);
    		
    		DropDownMenuActive=0;
    	}
    }

    @FXML
    void MenuExit(MouseEvent event) {
    	System.exit(0);
    }

    @FXML
    void MenuHelp(MouseEvent event) {
    	
    }

    @FXML
    void MenuSignOut(MouseEvent event) throws IOException {
    	
    		
    	  Client.getInstance().request(Command.LogOut,userName);
    	Client.addMessageRecievedFromServer(LOGOUT, SRMessage->{
    		if(SRMessage.getCommand()==Command.LogOut) {
    			
    				
    			boolean sucess=(boolean)SRMessage.getAttachedData()[0];
    			
    			if(sucess) {
    				try {
						ShowLogIn();
					} catch (IOException e) {
						e.printStackTrace();
					}
    				Client.removeMessageRecievedFromServer(LOGOUT);
    				
    			}else {
    				System.err.println("Failed to logout");
    			}
    		}
    	});
    	
    }

	private void ShowLogIn() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
		Parent root1 = (Parent) fxmlLoader.load();
		this.stage.close();
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setTitle("ABC");
		stage.setScene(new Scene(root1));  
		stage.show();
	}

}
