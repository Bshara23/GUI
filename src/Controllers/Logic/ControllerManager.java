package Controllers.Logic;

import java.util.ArrayList;

import Utility.VoidFunc;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
			if (!exceptThisEffect.equals(node.getEffect()))
				node.setEffect(effect);
		}
	}

	public static void setEffectConditioned(Node node, Effect effect, Effect exceptThisEffect) {
		if (!exceptThisEffect.equals(node.getEffect()))
			node.setEffect(effect);
	}

	public static ArrayList<Node> getAllNodes(Pane root) {
		ArrayList<Node> nodes = new ArrayList<Node>();
		addAllDescendents(root, nodes);
		return nodes;
	}

	private static void addAllDescendents(Pane parent, ArrayList<Node> nodes) {
		for (Node node : parent.getChildrenUnmodifiable()) {
			nodes.add(node);
			if (node instanceof Pane)
				addAllDescendents((Pane) node, nodes);
		}
	}

	public static void setOnHoverEffect(Node node, Effect onEntered, Effect onExited) {
		node.setOnMouseEntered(event -> {
			ControllerManager.setEffect(node, onEntered);
		});
		node.setOnMouseExited(event -> {
			ControllerManager.setEffect(node, onExited);
		});
	}

	public static void setOnHoverEffectConditioned(Node node, Effect onEntered, Effect onExited, Effect condition) {
		node.setOnMouseEntered(event -> {
			ControllerManager.setEffectConditioned(node, onEntered, condition);
		});
		node.setOnMouseExited(event -> {
			ControllerManager.setEffectConditioned(node, onExited, condition);
		});
	}

	public static void setMouseHoverEffects(Node node, Effect onEntered, Effect onExited, Effect condition,
			Cursor value) {

		node.setCursor(Cursor.HAND);
		setEffect(node, onExited);
		setOnHoverEffectConditioned(node, onEntered, onExited, condition);
	}

	public static void setMouseHoverEffects(Node node, Effect onEntered, Effect onExited, Effect condition) {
		setMouseHoverEffects(node, onEntered, onExited, condition, Cursor.HAND);
	}


	
	public static void setMouseHoverPressEffects(Node node, Effect onEntered, Effect onExited, Effect onPressed,
			 ArrayList<Node> nodesToReset, Cursor value) {

		node.setCursor(Cursor.HAND);
		setEffect(node, onExited);
		setOnHoverEffectConditioned(node, onEntered, onExited, onPressed);
		node.setOnMousePressed(event -> {
			ControllerManager.setEffect(nodesToReset, onExited);
			ControllerManager.setEffect(node, onPressed);
		});
	}
	
	public static void setMouseHoverPressEffects(Node node, Effect onEntered, Effect onExited, Effect onPressed,
			 Cursor value) {

		node.setCursor(Cursor.HAND);
		setEffect(node, onExited);
		setOnHoverEffectConditioned(node, onEntered, onExited, onPressed);
		node.setOnMousePressed(event -> {
			ControllerManager.setEffect(node, onPressed);
		});
	}
	
	public static boolean areAllStringsNotEmpty(String... strings) {
		for (String str : strings) {
			if(str.compareTo("") == 0)
				return false;
		}
		return true;
	}
	
	

}
