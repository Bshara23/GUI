package Controllers;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import ClientLogic.Client;
import Controllers.Logic.CommonEffects;
import Controllers.Logic.ControllerManager;
import Controllers.Logic.FxmlNames;
import Protocol.Command;
import Utility.AppManager;
import Utility.ControllerSwapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import Entities.*;

public class IssueRequestController implements Initializable {

	private static final String TIME_OF_ISSUE_REQUEST = "timeOfIssueRequest";

	@FXML
	private Text txtCurrentDate;

	@FXML
	private ComboBox<String> cbInformationSystem;

	@FXML
	private TextArea taRequestDescription;

	@FXML
	private TextArea taDescriptionOfCurrentState;

	@FXML
	private TextArea taDescriptionOfRequestedChange;

	@FXML
	private TextArea taComments;

	@FXML
	private HBox hbBrowseFiles;

	@FXML
	private HBox hbIssueRequest;

	@FXML
	private Canvas canvasRight;

	@FXML
	private Canvas canvasLeft;

	private ArrayList<String> files;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Apply the effects for the canvas
		RequestDetailsUserController.applyCanvasEffects(canvasRight, canvasLeft);

		hbIssueRequest.setCursor(Cursor.HAND);
		ControllerManager.setEffect(hbIssueRequest, CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		ControllerManager.setOnHoverEffect(hbIssueRequest, CommonEffects.REQUESTS_TABLE_ELEMENT_BLUE,
				CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);

		hbBrowseFiles.setCursor(Cursor.HAND);
		ControllerManager.setEffect(hbBrowseFiles, CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);
		ControllerManager.setOnHoverEffect(hbBrowseFiles, CommonEffects.REQUESTS_TABLE_ELEMENT_BLUE,
				CommonEffects.REQUEST_DETAILS_BUTTON_GRAY);

		AppManager.safeUpdate(TIME_OF_ISSUE_REQUEST, () -> {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			txtCurrentDate.setText(dtf.format(now));
		});

		final FileChooser fileChooser = new FileChooser();

		hbBrowseFiles.setOnMousePressed(event -> {
			List<java.io.File> list = fileChooser.showOpenMultipleDialog(ClientGUI.getStage());
			files = new ArrayList<String>();
			if (list != null) {
				for (java.io.File file : list) {
					String path = file.getPath();
					path = path.replace("\\", "/");
					files.add(path);
				}
			}
		});

		hbIssueRequest.setOnMousePressed(event -> {

			String comments = taComments.getText();
			String reqDesc = taRequestDescription.getText();
			String descReqChange = taDescriptionOfRequestedChange.getText();
			String descCurrState = taDescriptionOfCurrentState.getText();
			String relateInfoSys = cbInformationSystem.getValue();
			long reqestID = 9996;			// TODO: if id = -1, the server should know that he has to find a fitting id

			ChangeRequest changeRequest = new ChangeRequest(reqestID, ClientGUI.userName, LocalDate.now(), LocalDate.now(), LocalDate.now(), comments, reqDesc, descReqChange, descCurrState, relateInfoSys);
			
			Client.getInstance().request(Command.insertRequest, changeRequest);
			if(files != null)
				sendFilesToServer(files, reqestID);
		});

		cbInformationSystem.setItems(FXCollections.observableArrayList("Moodle", "Information System", "Library System", "Classroom Computers", "Braude Website", "Labs and Computers Farms"));

	}

	// TODO: add limits to types, size and number of files.
	private void sendFilesToServer(ArrayList<String> files, long reqID) {
		for (String path : files) {
			sendFileToServer(path, reqID);
		}
	}

	private void sendFileToServer(String filePath, long reqID) {

		File file = new File(0, reqID, filePath, "");
		file.loadBytesFromLocal();
		file.autoSetTypeAndNameFromLocation();

		Client.getInstance().request(Command.insertFile, file);

//		Client.addMessageRecievedFromServer("dddadwd", msg -> {
//			if (msg.getCommand() == Command.getFile) {
//				File dowloadedFile = (File) msg.getAttachedData();
//				dowloadedFile.writeFileToLocal();
//			}
//		});
//
//		Client.getInstance().request(Command.getFile, 5);
	}

}
