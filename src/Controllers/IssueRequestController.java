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
import Protocol.MsgReturnType;
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

	public static final int MB_4 = 4194303;

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

	private static ArrayList<String> filesPaths;
	static {
		filesPaths = new ArrayList<String>();
	}

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
			filesPaths.clear();
			if (list != null) {
				for (java.io.File file : list) {
					String path = file.getPath();
					path = path.replace("\\", "/");
					filesPaths.add(path);
				}
			}
		});

		// Set the behavior of the issue request button.
		hbIssueRequest.setOnMousePressed(event -> {

			String comments = taComments.getText();
			String reqDesc = taRequestDescription.getText();
			String descReqChange = taDescriptionOfRequestedChange.getText();
			String descCurrState = taDescriptionOfCurrentState.getText();
			String relateInfoSys = cbInformationSystem.getValue().toString();

			boolean areAllFieldsFilled = ControllerManager.areAllStringsNotEmpty(comments, reqDesc, descReqChange,
					descCurrState);

			if (areAllFieldsFilled) {
				long reqestID = 9996; // TODO: if id = -1, the server should know that he has to find a fitting id

				ChangeRequest changeRequest = new ChangeRequest(reqestID, ClientGUI.userName, LocalDate.now(),
						LocalDate.now(), LocalDate.now(), comments, reqDesc, descReqChange, descCurrState,
						relateInfoSys);

				if (filesPaths.size() == 0) {
					Client.getInstance().request(Command.insertRequest, changeRequest);

				} else {
					ArrayList<File> files = new ArrayList<File>();
					for (String path : filesPaths) {

						File file = new File(0, reqestID, path, "");
						file.loadBytesFromLocal();
						file.autoSetTypeAndNameFromPath();
						files.add(file);
						if(file.getStoredBytesSize() > MB_4) {
							ControllerManager.ShowAlertMessage("Error", "The attached file is too large",
									"Please attach files that are 4MB and below", null);
							return;
						}
					}
					Client.getInstance().request(Command.insertRequestWithFiles, changeRequest, files);

				}
			} else {
				ControllerManager.ShowAlertMessage("Error", "Required Fields Are Missing",
						"Please fill the missing fields", null);
			}

		});

		// Set the behavior of the controller after receiving a message back from the
		// server for
		// issuing a request.
		Client.addMessageRecievedFromServer("IssueRequestMessageReceieved", srMsg -> {
			if (srMsg.getCommand() == Command.insertRequest) {

				if (srMsg.getReturnType() == MsgReturnType.Success) {
					ControllerManager.ShowAlertMessage("Issue Request", "Success",
							"The request has been successfully issued!", null);
				} else if (srMsg.getReturnType() == MsgReturnType.Failure) {
					System.out.println("insertRequest");

					if ((String) srMsg.getAttachedData() == null) {
						ControllerManager.ShowAlertMessage("Issue Request", "Failure", "Something went wrong!", null);

					} else {
						ControllerManager.ShowAlertMessage("Issue Request", "Failure", (String) srMsg.getAttachedData(),
								null);
					}
				}

			} else if (srMsg.getCommand() == Command.insertRequestWithFiles) {
				if (srMsg.getReturnType() == MsgReturnType.Success) {
					ControllerManager.ShowAlertMessage("Issue Request", "Success",
							"The request has been successfully issued!", null);

				} else if (srMsg.getReturnType() == MsgReturnType.Failure) {
					System.out.println("insertRequestWithFiles");
					if ((String) srMsg.getAttachedData() == null) {
						ControllerManager.ShowAlertMessage("Issue Request", "Failure", "Something went wrong!", null);

					} else {
						ControllerManager.ShowAlertMessage("Issue Request", "Failure", (String) srMsg.getAttachedData(),
								null);
					}
				}
			}

		});

		cbInformationSystem.setItems(FXCollections.observableArrayList("Moodle", "Information System", "Library System",
				"Classroom Computers", "Braude Website", "Labs and Computers Farms"));
		cbInformationSystem.setValue("Information System");

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
		file.autoSetTypeAndNameFromPath();

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
