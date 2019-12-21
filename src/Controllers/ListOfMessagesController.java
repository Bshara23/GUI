package Controllers;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

import Controllers.Logic.CommonEffects;
import Controllers.Logic.ControllerManager;
import Controllers.Logic.FxmlNames;
import Controllers.Logic.NavigationBar;
import Utility.AppManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class ListOfMessagesController implements Initializable {

	@FXML
    private ImageView imgSearch;

    @FXML
    private Text txtPageHeader;

    @FXML
    private ImageView imgBack;

    @FXML
    private ImageView imgForward;

    @FXML
    private ImageView imgSettings;

    @FXML
    private ImageView imgRefresh;

    @FXML
    private ImageView imgArchive;

    @FXML
    private ImageView imgTrashBin;

    @FXML
    private ImageView imgMarkAsRead;

    @FXML
    private ImageView imgThreeDots;

    @FXML
    private HBox hbPrimary;

    @FXML
    private HBox hbUpdates;

    @FXML
    private HBox hbStaff;

    @FXML
    private HBox hbWork;

    @FXML
    private Line lineTableJob;
	private ArrayList<Node> buttons, messageTypes;



	@Override
	public void initialize(URL location, ResourceBundle resources) {

		buttons = new ArrayList<Node>();
		messageTypes = new ArrayList<Node>();

		buttons.add(imgArchive);
		buttons.add(imgBack);
		buttons.add(imgForward);
		buttons.add(imgMarkAsRead);
		buttons.add(imgRefresh);
		buttons.add(imgSearch);
		buttons.add(imgSettings);
		buttons.add(imgThreeDots);
		buttons.add(imgTrashBin);

		
	
		messageTypes.add(hbStaff);
		messageTypes.add(hbPrimary);

		messageTypes.add(hbUpdates);
		messageTypes.add(hbWork);

		for (Node node : buttons) {
			ControllerManager.setMouseHoverPressEffects(node, CommonEffects.REQUEST_DETAILS_BUTTON_BLACK,
					CommonEffects.REQUEST_DETAILS_BUTTON_GRAY, CommonEffects.REQUEST_DETAILS_BUTTON_BLUE,
					Cursor.HAND);
		}
		
		for (Node node : messageTypes) {
			ControllerManager.setMouseHoverPressEffects(node, CommonEffects.REQUEST_DETAILS_BUTTON_BLACK,
					CommonEffects.REQUEST_DETAILS_BUTTON_GRAY, CommonEffects.REQUEST_DETAILS_BUTTON_BLUE, messageTypes,
					Cursor.HAND);
			
		}

		
		
		ControllerManager.setEffect(lineTableJob, CommonEffects.REQUESTS_TABLE_ELEMENT_BLUE);
		ControllerManager.setEffect(hbPrimary, CommonEffects.REQUEST_DETAILS_BUTTON_BLUE);
		

	}

}
