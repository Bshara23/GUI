package Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class MessageEntryController implements Initializable {

	@FXML
	private ImageView imgCheckBox;

	@FXML
	private ImageView imgStar;

	@FXML
	private HBox hbContainer;

	@FXML
	private Text txtSubject;

	@FXML
	private Text txtContent;

	@FXML
	private Text txtDate;
	
	@FXML
    private HBox hbMainContainer;
	
	public boolean starred, checked;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		imgStar.setCursor(Cursor.HAND);
		imgCheckBox.setCursor(Cursor.HAND);
		hbContainer.setCursor(Cursor.HAND);


		imgStar.setOnMousePressed(event -> {
			setStarredImage(!starred);
		});
		
		imgCheckBox.setOnMousePressed(event -> {
			setCheckedImage(!checked);
		});
		
		
		hbContainer.setOnMousePressed(event -> {
			setCheckedImage(!checked);
		});
		
		
	}
	
	public void setFields(boolean starred, boolean checked, String subject, String content, String date) {
		

		
		txtContent.setText(content);
		txtSubject.setText(subject);
		txtDate.setText(date);
		
		
		setStarredImage(starred);
		setCheckedImage(checked);
	}
	
	
	private void setStarredImage(boolean value) {
		this.starred = value;
		if(value) {
			imgStar.setImage(new Image("Images\\Messages\\icons8_star_50px_2.png"));
		}else {
			imgStar.setImage(new Image("Images\\Messages\\icons8_star_50px_1.png"));
		}
	}
	
	private void setCheckedImage(boolean value) {
		this.checked = value;
		if(value) {
			imgCheckBox.setImage(new Image("Images\\Messages\\icons8_checked_checkbox_50px_3.png"));
		}else {
			imgCheckBox.setImage(new Image("Images\\Messages\\icons8_unchecked_checkbox_50px_1.png"));
		}
	}
	
	public void deleteSelf() {
		p.getChildren().clear();
		
	}

	private AnchorPane p;
	public void setAttachedPane(AnchorPane containerPane) {
		this.p = containerPane;
	}

}
