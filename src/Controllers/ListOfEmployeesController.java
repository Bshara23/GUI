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

public class ListOfEmployeesController implements Initializable {


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
    private ImageView imgThreeDots;

    @FXML
    private HBox hbInformationEngineers;

    @FXML
    private HBox hbLecurers;

    @FXML
    private HBox hbCommitteeMembers;

    @FXML
    private HBox hbOtherEmployees;

    @FXML
    private HBox hbStudents;

    @FXML
    private Line lineTableJob;

    @FXML
    private HBox hbRegisterUser;


	private ArrayList<Node> buttons, empTypes;


	@Override
	public void initialize(URL location, ResourceBundle resources) {

		buttons = new ArrayList<Node>();
		empTypes = new ArrayList<Node>();
		
		buttons.add(imgBack);
		buttons.add(imgForward);
		buttons.add(imgRefresh);
		buttons.add(imgSearch);
		buttons.add(imgSettings);
		buttons.add(imgThreeDots);


		empTypes.add(hbCommitteeMembers);
		empTypes.add(hbInformationEngineers);
		empTypes.add(hbLecurers);
		empTypes.add(hbOtherEmployees);
		empTypes.add(hbRegisterUser);
		empTypes.add(hbStudents);

		

		for (Node node : buttons) {
			ControllerManager.setMouseHoverPressEffects(node, CommonEffects.REQUEST_DETAILS_BUTTON_BLACK,
					CommonEffects.REQUEST_DETAILS_BUTTON_GRAY, CommonEffects.REQUEST_DETAILS_BUTTON_BLUE, buttons,
					Cursor.HAND);
		}
		
		for (Node node : empTypes) {
			ControllerManager.setMouseHoverPressEffects(node, CommonEffects.REQUEST_DETAILS_BUTTON_BLACK,
					CommonEffects.REQUEST_DETAILS_BUTTON_GRAY, CommonEffects.REQUEST_DETAILS_BUTTON_BLUE, buttons,
					Cursor.HAND);
		}

		
		
		ControllerManager.setEffect(lineTableJob, CommonEffects.REQUESTS_TABLE_ELEMENT_BLUE);
		ControllerManager.setEffect(hbInformationEngineers, CommonEffects.REQUEST_DETAILS_BUTTON_BLUE);
		

	}

}
