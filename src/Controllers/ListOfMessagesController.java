package Controllers;

import java.io.IOException;
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
import Utility.ControllerSwapper;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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

	@FXML
	private VBox hbMessagesContainer;

	private ArrayList<Node> buttons, messageTypes;
	private boolean xddd = true;
	private ArrayList<MessageEntryController> msgEntryControllers;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		msgEntryControllers = new ArrayList<MessageEntryController>();
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
					CommonEffects.REQUEST_DETAILS_BUTTON_GRAY, CommonEffects.REQUEST_DETAILS_BUTTON_BLUE, Cursor.HAND);
		}

		for (Node node : messageTypes) {
			ControllerManager.setMouseHoverPressEffects(node, CommonEffects.REQUEST_DETAILS_BUTTON_BLACK,
					CommonEffects.REQUEST_DETAILS_BUTTON_GRAY, CommonEffects.REQUEST_DETAILS_BUTTON_BLUE, messageTypes,
					Cursor.HAND);
		}
		
		
		ControllerManager.setMouseHoverPressEffects(imgTrashBin, CommonEffects.REQUEST_DETAILS_BUTTON_RED,
				CommonEffects.REQUEST_DETAILS_BUTTON_GRAY, CommonEffects.REQUEST_DETAILS_BUTTON_BLACK, messageTypes,
				Cursor.HAND);

		imgTrashBin.setOnMousePressed(event -> {
			boolean hasAtleastOneSelected = false;
			for (MessageEntryController messageEntryController : msgEntryControllers) {
				if (messageEntryController.checked) {
					hasAtleastOneSelected = true;
					break;
				}
			}

			if (hasAtleastOneSelected) {
				ControllerManager.showOkCancelMessage("Delete", "Delete messages",
						"Are you sure you want to delete the selected messages?", () -> {
							for (int i = 0; i < msgEntryControllers.size(); i++) {
								MessageEntryController cc = msgEntryControllers.get(i);

								if (cc.checked) {
									cc.deleteSelf();
									//msgEntryControllers.remove(cc);
								}
							}
						}, null);
			}

		});

		ControllerManager.setEffect(lineTableJob, CommonEffects.REQUESTS_TABLE_ELEMENT_BLUE);
		ControllerManager.setEffect(hbPrimary, CommonEffects.REQUEST_DETAILS_BUTTON_BLUE);

		hbMessagesContainer.getChildren();

		ObservableList<Node> nodes = FXCollections.observableArrayList();
		for (int i = 0; i < 10; i++) {

			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("MessageEntry.fxml"));
				AnchorPane pane = loader.load();
				MessageEntryController msgController = (MessageEntryController) loader.getController();
				msgEntryControllers.add(msgController);
				nodes.addAll(pane);
				msgController.setFields(xddd, xddd, "ddd" + i, "ddd" + i, "ddd" + i);
				msgController.setAttachedPane(pane);
				xddd = !xddd;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		hbMessagesContainer.getChildren().setAll(nodes);

	}

}
