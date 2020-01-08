package Controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

import ClientLogic.Client;
import Controllers.Logic.CommonEffects;
import Controllers.Logic.ControllerManager;
import Protocol.Command;
import Protocol.MsgReturnType;
import Utility.ControllerSwapper;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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

	@FXML
	private PieChart pieChartRequests;

	@FXML
	private HBox hbBtnLineChart;

	@FXML
	private HBox hbBtnPieChart;

	private ArrayList<Node> buttons;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		buttons = new ArrayList<Node>();
		
		buttons.add(hbBtnLineChart);
		buttons.add(hbBtnPieChart);
		
		hbBtnLineChart.setOnMouseClicked(event -> {
			pieChartRequests.setOpacity(0);
			chartSaReqestExecution.setOpacity(1);
		});
		
		hbBtnPieChart.setOnMouseClicked(event -> {
			pieChartRequests.setOpacity(1);
			chartSaReqestExecution.setOpacity(0);
		});
		
		for (Node node : buttons) {
			ControllerManager.setMouseHoverPressEffects(node, CommonEffects.REQUEST_DETAILS_BUTTON_BLACK,
					CommonEffects.REQUEST_DETAILS_BUTTON_GRAY, CommonEffects.REQUEST_DETAILS_BUTTON_BLUE, buttons,
					Cursor.HAND);
		}
		
		ControllerManager.setEffect(hbBtnLineChart, CommonEffects.REQUEST_DETAILS_BUTTON_BLUE);
		pieChartRequests.setOpacity(0);
		
		
		// chartSaReqestExecution.setTitle("Requests Status");
		
		/**
		 * @author EliaB
		 * get the data about requests from server for till today
		 * */
		
		Client.getInstance().request(Command.GetCounterOfRequestsByStatus,3);
		Client.addMessageRecievedFromServer("IssueRequestMessageReceieved", srMsg -> {
			if (srMsg.getCommand() == Command.GetCounterOfRequestsByStatus) {
				Map<String,Integer> result=(Map<String,Integer>)srMsg.getAttachedData()[0];
				for(String r:result.keySet()) {
					System.out.println(r+"-"+result.get(r));
				}
			}


		});
		Series<String, Number> s1 = new XYChart.Series<String, Number>();
		Series<String, Number> s2 = new XYChart.Series<String, Number>();
		Series<String, Number> s3 = new XYChart.Series<String, Number>();
		Series<String, Number> s4 = new XYChart.Series<String, Number>();

		s1.setName("Active");
		s2.setName("Locked");
		s3.setName("Close");
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

		pieChartRequests.setData(getChartData(percentile(requestsCanceled, requestsNumber, 1),
				percentile(requestsLocked, requestsNumber, 1), percentile(requestsActive, requestsNumber, 1),
				percentile(requestsClosed, requestsNumber, 1)));
	}

	private ObservableList<Data> getChartData(int a, int b, int c, int d) {
		ObservableList<Data> list = FXCollections.observableArrayList();
		list.addAll(new PieChart.Data("Canceled", a), new PieChart.Data("Locked", b), new PieChart.Data("Active", c),
				new PieChart.Data("Closed", d));
		return list;
	}
	
/**
 * 
 * */
	private String percentile(int num, int whole) {

		return ((100 * num / whole)) + "%";
	}

	private int percentile(int num, int whole, int dummy) {

		return ((100 * num / whole));
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
