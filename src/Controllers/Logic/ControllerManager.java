package Controllers.Logic;

import java.util.ArrayList;

import Utility.VoidFunc;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.Alert.AlertType;

public class ControllerManager {

	
	
	/**
	 * Displays an alert message
	 * @param func The function to call after the "OK" button has been pressed.
	 * */
	public static void ShowAlertMessage(String title, String header, String content, VoidFunc func) {
		Alert alertSuccess = new Alert(AlertType.INFORMATION);
		alertSuccess.setTitle(title);
		alertSuccess.setHeaderText(header);
		alertSuccess.setContentText(content);
		alertSuccess.showAndWait().ifPresent(rs -> {
			if (rs == ButtonType.OK) {
				Platform.runLater(() -> {
					
					if(func != null)
						func.call();

				});
			}
		});
	}
	
	
	
	
	
	
	
	
	
}
