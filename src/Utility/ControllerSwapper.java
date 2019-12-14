package Utility;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ControllerSwapper {

	public static Stage Show(Stage stage, URL location, String title) {
		
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(location);

			Parent root1 = null;
			try {
				root1 = (Parent) fxmlLoader.load();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    stage = new Stage();
			stage.setTitle(title);
			stage.setScene(new Scene(root1));

			ArrayList<Stage> s = new ArrayList<>();
			
			// Since the we can't use stage.show() directly
			// encapsulate it in an array list instead.
			s.add(stage);
			Platform.runLater(() -> {
				s.get(0).show();
			});
			return stage;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
