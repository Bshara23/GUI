package Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import Entities.EvaluationReport;
import Entities.Phase;
import Utility.DateUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class EvaluationReportComViewController implements Initializable {

    @FXML
    private VBox vbEvaluationReport;

    @FXML
    private Label lblPlace;

    @FXML
    private Label lblDescriptionOfRequiredChange;

    @FXML
    private Label lblResults;

    @FXML
    private Label lblConstraint;

    @FXML
    private Label lblRisks;

    @FXML
    private Label lblEstimatedExecTime;
    
    private EvaluationReport evaluationReport;
    

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		
	}

	public void setEvaluationReport(EvaluationReport evaluationReport) {
		this.evaluationReport = evaluationReport;
		
		lblConstraint.setText(evaluationReport.getConstraints());
		lblDescriptionOfRequiredChange.setText(evaluationReport.getContentLT());
		lblEstimatedExecTime.setText(DateUtil.toString(evaluationReport.getEstimatedExecutionTime()));
		lblPlace.setText(evaluationReport.getPlace());
		lblResults.setText(evaluationReport.getResult());
		lblRisks.setText(evaluationReport.getRisks());
		
	}


	
	

}
