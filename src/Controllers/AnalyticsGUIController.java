package Controllers;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import Utility.ControllerSwapper;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class AnalyticsGUIController implements Initializable {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private AnchorPane mainAnchor;

	@FXML
	private LineChart<String, Number> chartSaReqestExecution;

	@FXML
	private Text lblRequestsCanceled;

	@FXML
	private Text lblRequestsLocked;

	@FXML
	private Text lblRequestsClosed;

	@FXML
	private Text lblRequestsActive;

	@FXML
	private Text txtStatTitle;

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// chartSaReqestExecution.setTitle("Requests Status");

		Series<String, Number> s1 = new XYChart.Series<String, Number>();
		Series<String, Number> s2 = new XYChart.Series<String, Number>();
		Series<String, Number> s3 = new XYChart.Series<String, Number>();
		Series<String, Number> s4 = new XYChart.Series<String, Number>();

		s1.setName("Active");
		s2.setName("Locked");
		s3.setName("Closed");
		s4.setName("Canceled");

		Random rnd = new Random();

		int requestsActive = 0;
		int requestsLocked = 0;
		int requestsCanceled = 0;
		int requestsClosed = 0;

		for (int i = 1; i <= 7; i++) {

			int s1n = i * i + 9 - rnd.nextInt(9);
			int s2n = i + 5 + rnd.nextInt(3);
			int s3n = 3 + rnd.nextInt(6);
			int s4n = i + 5 + rnd.nextInt(23);

			s1.getData().add(new XYChart.Data<String, Number>("Day " + i, s1n));
			s2.getData().add(new XYChart.Data<String, Number>("Day " + i, s2n));
			s3.getData().add(new XYChart.Data<String, Number>("Day " + i, s3n));
			s4.getData().add(new XYChart.Data<String, Number>("Day " + i, s4n));

			requestsCanceled += s4n;
			requestsLocked += s2n;
			requestsActive += s3n;
			requestsClosed += s1n;

		}

		int requestsNumber = requestsCanceled + requestsActive + requestsClosed + requestsLocked;

		lblRequestsCanceled.setText(percentile(requestsCanceled, requestsNumber));
		lblRequestsLocked.setText(percentile(requestsLocked, requestsNumber));
		lblRequestsActive.setText(percentile(requestsActive, requestsNumber));
		lblRequestsClosed.setText(percentile(requestsClosed, requestsNumber));

		chartSaReqestExecution.getData().addAll(s4, s2, s3, s1);
	}

	private String percentile(int num, int whole) {
		System.out.println(whole);
		return ((100 * num / whole)) + "%";
	}

	@FXML
	private void changeScene() {

	}

	String[] statTitles = new String[] { "Requests Executions", "Requests Delays", "Requests Delays222" };
	int statTitlesCurrIndex = 0;

	@FXML
	private void onMouseScrollStatType(ScrollEvent event) {
		double y = event.getDeltaY();

		// Scrolling up
		if (y > 0) {
			changeStatTitle(true);
		}
		// Scrolling down
		else if (y < 0) {
			changeStatTitle(false);

		}

		System.out.println(y);
	}

	private void changeStatTitle(boolean goNext) {
		if (goNext) {
			statTitlesCurrIndex = (statTitlesCurrIndex + 1) % statTitles.length;
		} else {
			statTitlesCurrIndex = (statTitlesCurrIndex + statTitles.length - 1) % statTitles.length;
		}

		String res = statTitles[statTitlesCurrIndex];

		txtStatTitle.setText(res);

	}

}
