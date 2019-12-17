package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Controllers.Logic.FxmlNames;
import Controllers.Logic.NavigationBar;
import Utility.AppManager;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class SystemUserGUIController extends Application implements Initializable {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private AnchorPane mainAnchor;

	@FXML
	private AnchorPane apMainContent;

	@FXML
	private AnchorPane apBtnIssueRequest;

	@FXML
	private AnchorPane apBtnMyRequests;

	@FXML
	private AnchorPane apBtnMessages;

	@FXML
	private AnchorPane apBtnSettings;

    @FXML
    private ImageView imgNavigationBarArrow;
    
    @FXML
    private HBox hbNavigator;
    
    private ArrayList<AnchorPane> apList;
    
	@Override
	public void start(Stage stage) {
		stage.initStyle(StageStyle.UNDECORATED);

		FXMLLoader loader = new FXMLLoader(getClass().getResource("SystemUserGUI.fxml"));
		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException e) {

			e.printStackTrace();
		}

		stage.setScene(new Scene(root));
		stage.setTitle("Manager GUI");
		stage.show();
		
		


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
		
		apList = new ArrayList<AnchorPane>();
		apList.add(apBtnIssueRequest);
		apList.add(apBtnMessages);
		apList.add(apBtnMyRequests);
		apList.add(apBtnSettings);
	}

	@FXML
	void onIssueRequestPress(MouseEvent event) {
		
		menuSelection(apBtnIssueRequest);
		NavigationBar.clear();

	}

	@FXML
	void onMessagesPress(MouseEvent event) {
		menuSelection(apBtnMessages);
		NavigationBar.clear();

	}

	@FXML
	void onMyRequestsPress(MouseEvent event) {
		
		menuSelection(apBtnMyRequests);

		NavigationBar.clear();
		NavigationBar.next("My Requests", FxmlNames.REQUESTS_LIST);
	}

	@FXML
	void onSettingsPress(MouseEvent event) {
		NavigationBar.clear();
		menuSelection(apBtnSettings);
		//NavigationBar.next("Settings", "ListOfRequestsGUI.fxml");

	}
	
	private void menuSelection(AnchorPane target) {
		for (AnchorPane anchorPane : apList) {
			if (target.equals(anchorPane)) {
				anchorPane.setEffect(new ColorAdjust(0, 0, 0.85, 0));
			}else {
				anchorPane.setEffect(null);
			}
		}
	}
	
	
	
	
	

}
