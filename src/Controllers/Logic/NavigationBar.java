package Controllers.Logic;

import java.util.ArrayList;

import com.sun.javafx.cursor.CursorType;

import Utility.ControllerSwapper;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class NavigationBar {

	public static ImageView imgNavigationBarArrow;
	public static HBox navigationBar;
	public static AnchorPane apMainContent;

	private static ArrayList<String> pageNameList, fxmlFileNameList;

	static {
		pageNameList = new ArrayList<String>();
		fxmlFileNameList = new ArrayList<String>();
	}

	public static void clear() {
		pageNameList.clear();
		fxmlFileNameList.clear();
		navigationBar.getChildren().clear();
		apMainContent.getChildren().clear();

	}


	public static void next(String pageName, String fxmlFileName) {

		if (pageNameList.contains(pageName))
			return;
		
		// Add an arrow iff this is not the first entry
		if (navigationBar.getChildren().size() != 0) {
			
			ImageView img = new ImageView(imgNavigationBarArrow.getImage());
			img.setFitWidth(imgNavigationBarArrow.getFitWidth());
			img.setFitHeight(imgNavigationBarArrow.getFitHeight() - 6);

			navigationBar.getChildren().add(img);
		}

		pageNameList.add(pageName);
		fxmlFileNameList.add(fxmlFileName);

		Text txt = new Text(pageName);
		txt.setFont(new Font(14));
		txt.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {

				goTo(pageName);
			}
		});

		txt.setCursor(Cursor.HAND);

		navigationBar.getChildren().add(txt);
		
		loadLastPage();
		
	}

	public static void back(boolean load) {

		if (pageNameList.size() == 1) {

			navigationBar.getChildren().remove(navigationBar.getChildren().size() - 1);

			pageNameList.remove(pageNameList.size() - 1);
			fxmlFileNameList.remove(fxmlFileNameList.size() - 1);
			
			if (load)
				loadLastPage();

		} else if (pageNameList.size() > 1) {

			navigationBar.getChildren().remove(navigationBar.getChildren().size() - 1);
			navigationBar.getChildren().remove(navigationBar.getChildren().size() - 1);

			pageNameList.remove(pageNameList.size() - 1);
			fxmlFileNameList.remove(fxmlFileNameList.size() - 1);
			
			if (load)
				loadLastPage();

		}

	}

	private static void loadLastPage() {
		
		String fxmlFileName = fxmlFileNameList.get(fxmlFileNameList.size() - 1);
		ControllerSwapper.loadAnchorContent(apMainContent, fxmlFileName);
	}

	public static void goTo(String pageName) {

		// Remove the extra elements after the targeted page.
		int lastIndex = pageNameList.lastIndexOf(pageName);

		if (lastIndex == -1)
			return;
		
		int times = pageNameList.size() - lastIndex - 1;
		for (int i = 0; i < times; i++) {
			back(false);
		}
		loadLastPage();
	}

}
