package Controllers.Logic;

import java.util.ArrayList;

import Utility.VoidFunc;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.Alert.AlertType;

public class ControllerManager {

	/**
	 * Displays an alert message
	 * 
	 * @param func The function to call after the "OK" button has been pressed.
	 */
	public static void ShowAlertMessage(String title, String header, String content, VoidFunc func) {
		Alert alertSuccess = new Alert(AlertType.INFORMATION);
		alertSuccess.setTitle(title);
		alertSuccess.setHeaderText(header);
		alertSuccess.setContentText(content);
		alertSuccess.showAndWait().ifPresent(rs -> {
			if (rs == ButtonType.OK) {
				Platform.runLater(() -> {

					if (func != null)
						func.call();

				});
			}
		});
	}

	public static void setEffect(ArrayList<Node> nodes, Effect effect) {
		for (Node node : nodes) {
			node.setEffect(effect);
		}
	}

	public static void setEffect(Node node, Effect effect) {
		node.setEffect(effect);
	}

	// TODO: bug found, were if i were to use the regular 
	public static void setEffectConditioned(ArrayList<Node> nodes, Effect effect, Effect exceptThisEffect) {
		for (Node node : nodes) {
			if (!node.getEffect().equals(exceptThisEffect))
				node.setEffect(effect);
		}
	}

	public static void setEffectConditioned(Node node, Effect effect, Effect exceptThisEffect) {
		if (!node.getEffect().equals(exceptThisEffect))
			node.setEffect(effect);
	}

}
