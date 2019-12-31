package Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
