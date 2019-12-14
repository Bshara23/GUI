package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import Utility.ControllerSwapper;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class ManagerGUIController extends Application implements Initializable {

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

	@Override
	public void start(Stage stage) {

		String title = "Area Chart Sample";

		stage = ControllerSwapper.Show(stage, getClass().getResource("Manager.fxml"), title);
		stage.initStyle(StageStyle.UNDECORATED);

		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				System.exit(1);
			}
		});

	}

	public static void main(String[] args) {
		launch(args);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	
 
		//chartSaReqestExecution.setTitle("Requests Status");

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
			
			int s1n = i*i +9 - rnd.nextInt(9);
			int s2n = i + 5 + rnd.nextInt(3);
			int s3n = 3 + rnd.nextInt(6);
			int s4n = i + 5 + rnd.nextInt(23);
			
			s1.getData().add(new XYChart.Data<String, Number>("Day "+i, s1n));
			s2.getData().add(new XYChart.Data<String, Number>("Day "+i, s2n));
			s3.getData().add(new XYChart.Data<String, Number>("Day "+i, s3n));
			s4.getData().add(new XYChart.Data<String, Number>("Day "+i, s4n));
			
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

}
