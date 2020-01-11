package Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import Entities.Phase;
import Utility.DateUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class WaitingForPhaseEstimatedTimeConfirmationGUI implements Initializable {

	
	private Phase phase;
    @FXML
    private VBox vbEvaluationReport;

    @FXML
    private Text txtEstimationDate;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		
	}
	
	public void setPhase(Phase ph) {
		phase = ph;
		
		txtEstimationDate.setText(DateUtil.toString(ph.getEstimatedTimeOfCompletion()));
	}

}
