package CreationAndBasicTesting;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import ClientLogic.Client;
import Entities.File;
import Entities.SystemUser;
import Protocol.Command;
import Protocol.SRMessage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public class ClientConsleTest extends Application implements Initializable {

	public static void main(String[] args) {

		launch(args);

//		File file = new File(5, 5, "dd.jpg", "jpg");
//		file.loadBytesFromLocal();		
//		Client.getInstance().request(Command.insertFile, file);

//		Client.addMessageRecievedFromServer("dddadwd", msg -> {
//			if (msg.getCommand() == Command.getFile) {
//				File dowloadedFile = (File)msg.getAttachedData();
//				dowloadedFile.writeFileToLocal();
//			}
//		});
//	
//		Client.getInstance().request(Command.getFile, 5);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Client.getInstance().initialize("10.0.0.6", 5555);
		// File file = new File(5, 5, "dd.jpg", "jpg");
		// file.loadBytesFromLocal();
		// Client.getInstance().request(Command.insertFile, file);WWW

//		SRMessage srM = new SRMessage(Command.insertFile, file);
//
//		Client.getInstance().sendToServer(srM);

		Client.addMessageRecievedFromServer("dddadwd", msg -> {
			if (msg.getCommand() == Command.getFile) {
				File dowloadedFile = (File) msg.getAttachedData();
				dowloadedFile.writeFileToLocal();
			}
		});

		Client.getInstance().request(Command.getFile, 5);

	}

}
