package Controllers;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import ClientLogic.Client;
import Controllers.Logic.CommonEffects;
import Controllers.Logic.ControllerManager;
import Controllers.Logic.FxmlNames;
import Protocol.Command;
import Utility.ControllerSwapper;
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
				sendFilesToServer(files);
			}
		});

	}

	private void sendFilesToServer(ArrayList<String> files) {
		for (String path : files) {
			sendFileToServer(path);
		}
	}
	
	private void sendFileToServer(String filePath) {
		
		File file = new File(0, 5, filePath, "");
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
