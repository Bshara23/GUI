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

import ClientLogic.Client;
import Controllers.Logic.CommonEffects;
import Controllers.Logic.ControllerManager;
import Controllers.Logic.FxmlNames;
import Controllers.Logic.NavigationBar;
import Controllers.Logic.RequestsType;
import Entities.Message;
import Protocol.Command;
import Protocol.MsgReturnType;
import Protocol.SeriObject;
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

	private static final String MESSAGES_DELETED_LIST_OF_MESSAGES_RESPONSE = "messagesDeletedListOfMessagesResponse";

	private static final String GET_COUNT_OF_MESSAGES = "dawdwa42r2353465346346";

	private static final String GET_COUNT_OF_REQUESTS = "35325236236325r32r";

	private static final String GET_MESSAGES_PRIMARY_LIST_OF_MESSAGES = "getMessagesPrimaryListOfMessages";

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

	@FXML
	private Text txtMessagesCount;

	private ArrayList<Node> buttons, messageTypes;

	private ArrayList<MessageEntryController> msgEntryControllers;

	private int currentRowIndex = 0;
	private int countOfMessages;
	private int rowCountLimit = 10;
	private int selectedMessagesCount = 0;

	@SuppressWarnings("unchecked")
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
			selectedMessagesCount = 0;
			for (MessageEntryController messageEntryController : msgEntryControllers) {
				if (messageEntryController.checked) {
					hasAtleastOneSelected = true;
					selectedMessagesCount++;
				}
			}
			
			if (hasAtleastOneSelected) {
				ControllerManager.showOkCancelMessage("Delete", "Delete messages",
						"Are you sure you want to delete "+ selectedMessagesCount +" messages?", () -> {
							ArrayList<Message> messagesToDelete = new ArrayList<Message>();

							for (int i = 0; i < msgEntryControllers.size(); i++) {
								MessageEntryController cc = msgEntryControllers.get(i);

								if (cc.checked) {
									messagesToDelete.add(cc.getMessage());
									cc.deleteSelf();
								}
							}
							selectedMessagesCount = 0;
							Client.getInstance().request(Command.deleteObjects, messagesToDelete);
						}, null);
			}

		});

		ControllerManager.setEffect(lineTableJob, CommonEffects.REQUESTS_TABLE_ELEMENT_BLUE);
		ControllerManager.setEffect(hbPrimary, CommonEffects.REQUEST_DETAILS_BUTTON_BLUE);

		Client.addMessageRecievedFromServer(GET_COUNT_OF_MESSAGES, srMsg -> {

			if (srMsg.getCommand() == Command.countOfObjects) {

				countOfMessages = (int) srMsg.getAttachedData()[0];

				System.out.println(countOfMessages);
				Client.getInstance().request(Command.getMessagesPrimary, ClientGUI.userName, currentRowIndex,
						rowCountLimit);
			}
		});

		Client.addMessageRecievedFromServer(GET_MESSAGES_PRIMARY_LIST_OF_MESSAGES, rsMsg -> {

			if (rsMsg.getCommand() == Command.getMessagesPrimary) {
				ArrayList<Message> msgs = (ArrayList<Message>) rsMsg.getAttachedData()[0];
				
				int size = msgs.size();
				
				loadMessages(msgs);

				txtMessagesCount.setText((currentRowIndex + 1) + "-" + (currentRowIndex + size) + " of " + countOfMessages);

			}

		});

		Client.addMessageRecievedFromServer(MESSAGES_DELETED_LIST_OF_MESSAGES_RESPONSE, rsMsg -> {

			if (rsMsg.getCommand() == Command.deleteObjects) {

				String responseId = (String) rsMsg.getAttachedData()[0];

				if (responseId.compareTo(MESSAGES_DELETED_LIST_OF_MESSAGES_RESPONSE) == 0) {
					// We don't care if the delete was successful; show error message on failure of
					// deletion.
					if ((MsgReturnType)rsMsg.getAttachedData()[1] == MsgReturnType.Failure) {
						ControllerManager.showErrorMessage("Error", "Deletion Error",
								"Server was not able to delete the message!", null);
					}
					Client.getInstance().request(Command.countOfObjects, "`to`='" + ClientGUI.userName + "'",
							Message.getEmptyInstance());
					
				}

			}

		});

		// Client.getInstance().request(Command.getMessagesPrimary, new
		// SeriObject(ClientGUI.userName));

		imgForward.setOnMousePressed(event -> {

			if (currentRowIndex + rowCountLimit < countOfMessages) {
				currentRowIndex += rowCountLimit;
				Client.getInstance().request(Command.getMessagesPrimary, ClientGUI.userName, currentRowIndex,
						rowCountLimit);
				txtMessagesCount.setText(
						(currentRowIndex + 1) + "-" + (currentRowIndex + rowCountLimit) + " of " + countOfMessages);

			}
		});

		imgBack.setOnMousePressed(event -> {

			if (currentRowIndex - rowCountLimit >= 0) {
				currentRowIndex -= rowCountLimit;
				Client.getInstance().request(Command.getMessagesPrimary, ClientGUI.userName, currentRowIndex,
						rowCountLimit);

				txtMessagesCount.setText(
						(currentRowIndex + 1) + "-" + (currentRowIndex + rowCountLimit) + " of " + countOfMessages);
			}

		});

		Client.getInstance().request(Command.countOfObjects, "`to`='" + ClientGUI.userName + "'",
				Message.getEmptyInstance());

	}

	@Override
	protected void finalize() throws Throwable {
		Client.removeMessageRecievedFromServer(GET_MESSAGES_PRIMARY_LIST_OF_MESSAGES);
		super.finalize();
	}

	private void loadMessages(ArrayList<Message> msgs) {

		ObservableList<Node> nodes = FXCollections.observableArrayList();

		for (Message message : msgs) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource(FxmlNames.MESSAGE_ENTRY_FXML));
				AnchorPane pane = loader.load();
				MessageEntryController msgController = (MessageEntryController) loader.getController();
				msgEntryControllers.add(msgController);
				nodes.addAll(pane);

				msgController.setFields(message);
				msgController.setAttachedPane(pane);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		hbMessagesContainer.getChildren().setAll(nodes);

	}

}
