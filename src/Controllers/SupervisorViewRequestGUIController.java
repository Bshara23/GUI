package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class SupervisorViewRequestGUIController extends Application implements Initializable {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private AnchorPane mainAnchor;



	@Override
	public void start(Stage stage) {
		stage.initStyle(StageStyle.UNDECORATED);

		FXMLLoader loader = new FXMLLoader(getClass().getResource("SupervisorViewRequestGUI.fxml"));
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
		
	}
	
	

   
    

}
