package Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import Controllers.Logic.ControllerManager;
import Controllers.Logic.NavigationBar;
import Entities.Message;
import Entities.SystemUser;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class MessageSingalController implements Initializable {

	@FXML
	private Canvas canvasRight;

	@FXML
	private HBox hbInformationContainer;

	@FXML
	private Text txtSubject;

	@FXML
	private Text txtFrom;

	@FXML
	private Text txtSentAt;

	@FXML
	private Text txtContent;

	@FXML
	private Text txtLinkedRequest;

	@FXML
	private Canvas canvasLeft;

	private Message msg;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Apply the effects for the canvas
		RequestDetailsUserController.applyCanvasEffects(canvasRight, canvasLeft);
	}

	public void setMessage(Message msg) {
		this.msg = msg;

		txtSubject.setText(msg.getSubject());
		txtContent.setText(msg.getMessageContentLT());
		txtSentAt.setText(ControllerManager.getDateTime(msg.getSentAt()));
		txtFrom.setText(msg.getFrom());

		if (msg.getRequestId() != -1) {
			txtLinkedRequest.setOnMousePressed(event -> {

				// TODO: check permissions

				boolean hasExpired = checkIfMessageLinkExpired();
				if (hasExpired) {
					ControllerManager.showInformationMessage("Message Expired", "Message has been expired",
							"This message link is no longer available", null);
				} else {
					NavigationBar.next("Request Details",
							getFxmlName(ClientGUI.systemUser, ClientGUI.empNumber, msg.getRequestId()));
				}

			});
		} else {
			txtLinkedRequest.setText("Not Available");
			txtLinkedRequest.setUnderline(false);
			txtLinkedRequest.setFill(Color.BLACK);
		}

	}

	private String getFxmlName(SystemUser systemUser, long empNumber, long requestId) {
		// TODO Auto-generated method stub
		return null;
	}

	private boolean checkIfMessageLinkExpired() {
		// TODO Auto-generated method stub
		return false;
	}
}
