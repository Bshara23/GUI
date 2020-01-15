package Controllers;

import java.net.URL;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import ClientLogic.Client;
import Controllers.Logic.CommonEffects;
import Controllers.Logic.ControllerManager;
import Protocol.Command;
import Protocol.MsgReturnType;
import Utility.ControllerSwapper;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Pair;

public class AnalyticsGUIController implements Initializable {

	private static int CurrentData = 7;

    @FXML
    private HBox hbBtnBarChart;

    @FXML
    private Label LastWeek;

    @FXML
    private Label Last30;
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private AnchorPane mainAnchor;
    private CategoryAxis xAxis = new CategoryAxis();
    private NumberAxis yAxis = new NumberAxis();
    @FXML
	private BarChart<String,Number> bc=new BarChart<String,Number>(xAxis,yAxis);
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
	
    @FXML
    private Label lblLastWeek;

    @FXML
    private Label lblLast30;

    @FXML
    private Label lblLastYear;

    @FXML
    private Label lblAverageCanceled;

    @FXML
    private Label lblSTDCanceled;

    @FXML
    private Label lblMedianCanceled;

    @FXML
    private Label lblAverageLocked;

    @FXML
    private Label lblSTDLocked;

    @FXML
    private Label lblMedianLocked;

    @FXML
    private Label lblAverageClosed;

    @FXML
    private Label lblSTDClosed;

    @FXML
    private Label lblMedianClosed;

    @FXML
    private Label lblAverageActive;

    @FXML
    private Label lblSTDActive;

    @FXML
    private Label lblMedianActive;

	Timestamp date1;
	Timestamp date2;
	private ArrayList<Node> buttons;
	private double average;
	private double std;
	Series<String, Number> s1 = new XYChart.Series<String, Number>();
	Series<String, Number> s2 = new XYChart.Series<String, Number>();
	Series<String, Number> s3 = new XYChart.Series<String, Number>();
	Series<String, Number> s4 = new XYChart.Series<String, Number>();
	Series<String,Double> Active =new Series<>();
	Series<String,Double> Canceled =new Series<>();
	Series<String,Double> Locked =new Series<>();
	Series<String,Double> Closed =new Series<>();
	int requestsNumber = 0;
	int requestsActive = 0;
	int requestsLocked = 0;
	int requestsCanceled = 0;
	int requestsClosed = 0;
	ArrayList<HashMap<String, Integer>> results;
	private static  ArrayList<XYChart.Series>  ser = new ArrayList<XYChart.Series>();


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		buttons = new ArrayList<Node>();

		buttons.add(hbBtnLineChart);
		buttons.add(hbBtnPieChart);
		buttons.add(hbBtnBarChart);
		hbBtnLineChart.setOnMouseClicked(event -> {
			pieChartRequests.setOpacity(0);
			chartSaReqestExecution.setOpacity(1);
			bc.setOpacity(0);
		});

		hbBtnPieChart.setOnMouseClicked(event -> {
			pieChartRequests.setOpacity(1);
			chartSaReqestExecution.setOpacity(0);
			bc.setOpacity(0);
		});
		
		hbBtnBarChart.setOnMouseClicked(event -> {
			bc.setOpacity(1);
			pieChartRequests.setOpacity(0);
			chartSaReqestExecution.setOpacity(0);
		});;
		for (Node node : buttons) {
			ControllerManager.setMouseHoverPressEffects(node, CommonEffects.REQUEST_DETAILS_BUTTON_BLACK,
					CommonEffects.REQUEST_DETAILS_BUTTON_GRAY, CommonEffects.REQUEST_DETAILS_BUTTON_BLUE, buttons,
					Cursor.HAND);
		}

		ControllerManager.setEffect(hbBtnLineChart, CommonEffects.REQUEST_DETAILS_BUTTON_BLUE);
		pieChartRequests.setOpacity(0);
		bc.setOpacity(0);
		chartSaReqestExecution.setTitle("Requests Status");
		try {
	    	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 	Timestamp firstDate = new Timestamp(System.currentTimeMillis());
		    Calendar cal = Calendar.getInstance();
		    cal.setTimeInMillis(firstDate.getTime());
		    cal.add(Calendar.DAY_OF_MONTH, -30);
		    Timestamp secDate = new Timestamp(cal.getTime().getTime());
	    	CurrentData=30;
	    	 Date d = new Date();
	         Date d2 = new Date();
	     	d = sdf.parse(firstDate.toString());
			d2 = sdf.parse(secDate.toString());
			date1 = new Timestamp(d.getTime());
			date2 = new Timestamp(d2.getTime());
	     	Client.getInstance().request(Command.GetCounterOfPhasesByStatusDateRange, date2,date1);
	    	SetData2();
		}catch(Exception e) {
		
		}


	}


	private void SetData2() {
		Client.getInstance().addMessageRecievedFromServer("GetCounterOfPhasesByStatusDateRange", rsMsg -> {
			if (rsMsg.getCommand() == Command.GetCounterOfPhasesByStatusDateRange) {
			
				bc.setTitle("");
				bc.setBarGap(2);
			    xAxis.setLabel("Day");       
			    yAxis.setLabel("Value");
				
				ArrayList<Double> listofActiveRequest= new ArrayList<>();
				ArrayList<Double> listofClosedRequest= new ArrayList<>();
				ArrayList<Double> listofCanceledRequest= new ArrayList<>();
				ArrayList<Double> listofLockedRequest= new ArrayList<>();
				chartSaReqestExecution.getData().removeAll(s1,s2,s3,s4);
		
				s1 = new XYChart.Series<String, Number>();
		    	s2 = new XYChart.Series<String, Number>();
		    	s3 = new XYChart.Series<String, Number>();
		    	s4 = new XYChart.Series<String, Number>();
		    	results = (ArrayList<HashMap<String,Integer>>)rsMsg.getAttachedData()[0];
		    	requestsActive = requestsActive = requestsLocked = requestsClosed = 0;
		    	
		     	ser.add(Canceled);
		    	ser.add(Locked);
		    	ser.add(Active);
		    	ser.add(Closed);
		    	 int size=results.size();
		    	 int j=0;
				for(int i=0;i<results.size();i++)
				{
					j++;
				
					
					 
				 for(String r:results.get(i).keySet()) {
					 switch(r) {
				 		case "Active":
				 			s1.getData().add(new XYChart.Data<String, Number>("" + i, results.get(i).get("Active")));
				 			if( (double)results.get(i).get(r)>=requestsActive)
				 				requestsActive= results.get(i).get(r);
						 	listofActiveRequest.add((double)results.get(i).get(r));
						 	
						 	break;
					
					 
				 		case"Canceled":
				 			s2.getData().add(new XYChart.Data<String, Number>("" + i, results.get(i).get("Canceled")));
				 			if( (double)results.get(i).get(r)>=requestsCanceled)
				 				requestsCanceled= results.get(i).get(r);
						 	listofCanceledRequest.add((double)results.get(i).get(r));
						 
						 break;

					 
				 		case "Locked":
				 			s3.getData().add(new XYChart.Data<String, Number>("" + i, results.get(i).get("Locked")));
				 			if( (double)results.get(i).get(r)>=requestsLocked)
				 				requestsLocked=  results.get(i).get(r);
						 	listofLockedRequest.add((double)results.get(i).get(r));
						 	
						 break;
					 
				 		case "Closed":
				 			s4.getData().add(new XYChart.Data<String, Number>("" + i, results.get(i).get("Closed")));
				 			if( (double)results.get(i).get(r)>=requestsClosed)
				 				requestsClosed+=(double)results.get(i).get(r);
				 			listofClosedRequest.add((double)results.get(i).get(r));
				 			
						 break;
					 
					 }	 
					 
					
					 if((j%10==0 && j>=10) ||(results.size()<=10&& results.size()-j<=10)||(results.size()>10&&results.size()-j+1==0)) {
						 
						 ser.get(0).getData().add(new XYChart.Data(i%10+"",requestsCanceled));
						 ser.get(1).getData().add(new XYChart.Data(i%10+"",requestsLocked));
						 ser.get(2).getData().add(new XYChart.Data(i%10+"", requestsActive));
						 ser.get(3).getData().add(new XYChart.Data(i%10+"",requestsClosed));
						
						
					 }
				 }
					
					
					
				}
				if(bc!=null&&bc.getData().size()>0)bc.getData().clear();
				for(Series<String, Number> x:ser)
					if(!bc.getData().contains(x))
						bc.getData().add(x);
				requestsNumber = requestsCanceled + requestsActive + requestsClosed + requestsLocked;
				listofActiveRequest.sort(new Comparator<Double>() {
				    public int compare(Double p1, Double p2) {
				        if (p1 < p2) return -1;
				        if (p1 > p2) return 1;
				        return 0;
				    }} );
				
		
				DecimalFormat df = new DecimalFormat("#.##"); 
				lblAverageActive.setText(df.format(average(listofActiveRequest))+"");
				lblAverageCanceled.setText(df.format(average(listofCanceledRequest))+"");
				lblAverageClosed.setText(df.format(average(listofClosedRequest))+"");
				lblAverageLocked.setText(df.format(average(listofLockedRequest))+"");
				
				lblSTDActive.setText(df.format(std(listofActiveRequest,average(listofActiveRequest)))+"");
				lblSTDCanceled.setText(df.format(std(listofCanceledRequest,average(listofCanceledRequest)))+"");
				lblSTDLocked.setText(df.format(std(listofLockedRequest,average(listofLockedRequest)))+"");
				lblSTDClosed.setText(df.format(std(listofClosedRequest,average(listofClosedRequest)))+"");
				
				lblMedianActive.setText(df.format(median(listofActiveRequest))+"");
				lblMedianCanceled.setText(df.format(median(listofCanceledRequest))+"");
				lblMedianLocked.setText(df.format(median(listofLockedRequest))+"");
				lblMedianClosed.setText(df.format(median(listofClosedRequest))+"");
				
				lblRequestsCanceled.setText(percentile(requestsCanceled, requestsNumber));
				lblRequestsLocked.setText(percentile(requestsLocked, requestsNumber));
				lblRequestsActive.setText(percentile(requestsActive, requestsNumber));
				lblRequestsClosed.setText(percentile(requestsClosed, requestsNumber));

				chartSaReqestExecution.getData().addAll(s1,s2,s3,s4);

				pieChartRequests.setData(getChartData(percentile(requestsCanceled, requestsNumber, 1),
						percentile(requestsLocked, requestsNumber, 1), percentile(requestsActive, requestsNumber, 1),
						percentile(requestsClosed, requestsNumber, 1)));
				
			}
				
		});
		Client.getInstance().request(Command.GetCounterOfPhasesByStatusDateRange,date1,date2);
		
	}



	private ObservableList<Data> getChartData(double d, double e, double f, double g) {
		ObservableList<Data> list = FXCollections.observableArrayList();
		list.addAll(new PieChart.Data("Canceled", d), new PieChart.Data("Locked", e), new PieChart.Data("Active", f),
				new PieChart.Data("Closed", g));
		return list;
	}

	/**
	 * 
	 * */
	private String percentile(int requestsCanceled, int requestsNumber) {
		if (requestsNumber > 0)
			return ((100 * requestsCanceled / requestsNumber)) + "%";

		return "0%";
	}

	private double percentile(int requestsCanceled, int requestsNumber, int dummy) {
		if (requestsNumber > 0)
			return ((100 * requestsCanceled / requestsNumber));
		return 0;
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
	/**
	 * @author EliaB get the data about requests from server for till today
	 */

    @FXML
    void SetNewDataTo30Days(MouseEvent event) {
    	
    	chartSaReqestExecution.getData().removeAll(s1,s2,s3,s4);

    	s1 = new XYChart.Series<String, Number>();
    	s2 = new XYChart.Series<String, Number>();
    	s3 = new XYChart.Series<String, Number>();
    	s4 = new XYChart.Series<String, Number>();
   
    	try {
    	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	 	Timestamp firstDate = new Timestamp(System.currentTimeMillis());
	    Calendar cal = Calendar.getInstance();
	    cal.setTimeInMillis(firstDate.getTime());
	    cal.add(Calendar.DAY_OF_MONTH, -30);
	    Timestamp secDate = new Timestamp(cal.getTime().getTime());
    	CurrentData=30;
    	 Date d = new Date();
         Date d2 = new Date();
     	d = sdf.parse(firstDate.toString());
		d2 = sdf.parse(secDate.toString());
		date2 = new Timestamp(d.getTime());
		date1 = new Timestamp(d2.getTime());
     	Client.getInstance().request(Command.GetCounterOfPhasesByStatusDateRange, date1,date2);
    	SetData2();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
    }
    @FXML
    void SetNewDataToLastWeek(MouseEvent event) {
    	chartSaReqestExecution.getData().removeAll(s1,s2,s3,s4);
    	s1 = new XYChart.Series<String, Number>();
    	s2 = new XYChart.Series<String, Number>();
    	s3 = new XYChart.Series<String, Number>();
    	s4 = new XYChart.Series<String, Number>();

 
    	try {
    	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	 	Timestamp firstDate = new Timestamp(System.currentTimeMillis());
	    Calendar cal = Calendar.getInstance();
	    cal.setTimeInMillis(firstDate.getTime());
	    cal.add(Calendar.DAY_OF_MONTH, -7);
	    Timestamp secDate = new Timestamp(cal.getTime().getTime());
    	CurrentData=7;
    	 Date d = new Date();
         Date d2 = new Date();
     	d = sdf.parse(firstDate.toString());
		d2 = sdf.parse(secDate.toString());
		date2 = new Timestamp(d.getTime());
		date1 = new Timestamp(d2.getTime());
     	Client.getInstance().request(Command.GetCounterOfPhasesByStatusDateRange, date2,date1);
    	SetData2();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @FXML
    void SetNewDataToLastYear(MouseEvent event) {
    	chartSaReqestExecution.getData().removeAll(s1,s2,s3,s4);
    	s1 = new XYChart.Series<String, Number>();
    	s2 = new XYChart.Series<String, Number>();
    	s3 = new XYChart.Series<String, Number>();
    	s4 = new XYChart.Series<String, Number>();

 
    	try {
    	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	 	Timestamp firstDate = new Timestamp(System.currentTimeMillis());
	    Calendar cal = Calendar.getInstance();
	    cal.setTimeInMillis(firstDate.getTime());
	    cal.add(Calendar.DAY_OF_MONTH, -300);
	    Timestamp secDate = new Timestamp(cal.getTime().getTime());
    	CurrentData=300;
    	 Date d = new Date();
         Date d2 = new Date();
     	d = sdf.parse(firstDate.toString());
		d2 = sdf.parse(secDate.toString());
		date2 = new Timestamp(d.getTime());
		date1 = new Timestamp(d2.getTime());
		System.out.println(date1+ " "+ date2);
     	Client.getInstance().request(Command.GetCounterOfPhasesByStatusDateRange, date2,date1);
    	SetData2();
		} catch (ParseException e) {
			
		}
    }
    @FXML
    void SetDateRange(MouseEvent event) {
 
    	Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Pick two dates");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("OK", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

                GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));


        DatePicker datePicker = new DatePicker();
      

        DatePicker datePicker2 = new DatePicker();
       

        gridPane.add(datePicker, 0, 0);
        gridPane.add(new Label("To:"), 1, 0);
        gridPane.add(datePicker2, 2, 0);

        dialog.getDialogPane().setContent(gridPane);

        // Request focus on the username field by default.
        Platform.runLater(() -> datePicker.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(datePicker.getValue().toString(), datePicker2.getValue().toString());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(pair -> {
           Date d = new Date();
           Date d2 = new Date();
           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
           Timestamp CurrentDate = new Timestamp(System.currentTimeMillis());
           
			try {
				
				d = sdf.parse(pair.getKey());
				d2 = sdf.parse(pair.getValue());
				date1 = new Timestamp(d.getTime());
				date2 = new Timestamp(d2.getTime());
				
		            System.out.println("date1 : " + sdf.format(date1));
		            System.out.println("date2 : " + sdf.format(date2));
		            System.out.println("Range by days:"+(TimeUnit.DAYS.convert(date2.getTime() - date1.getTime(), TimeUnit.MILLISECONDS)));
		            if (CurrentDate.compareTo(date2) > 0 && date2.compareTo(date1) > 0) {
		            	CurrentData= (int) ((TimeUnit.DAYS.convert(date2.getTime() - date1.getTime(), TimeUnit.MILLISECONDS))+1);
		             	Client.getInstance().request(Command.GetCounterOfPhasesByStatusDateRange,date1,date2);
		            	SetData2();
		            } else {
		            	SetDateRange(event);
		            }
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
           
        });
    }
 
    public Double std(ArrayList<Double> listofRequest,Double average) {
    	int std=0;
    	for(Double x:listofRequest)
		{
			std+=Math.pow(x-average,2);
			
		}
    	if(listofRequest.size()>0) {
		std/=listofRequest.size();
		return Math.sqrt(std);
    	}
    	return 0.0;
	
    }
    private Double average(ArrayList<Double> listofRequest) {
    	double average=0;
    	if(listofRequest.size()>0)
    	{
    		for(Double x:listofRequest)
    			average+=x;
    		
    		average/=listofRequest.size();
    		return average;
    	}
    	return 0.0;
    }
	private Double median(ArrayList<Double> listofRequest) {
		if(listofRequest.size()>0) {


			Collections.sort(listofRequest);
		
				return listofRequest.get((int)listofRequest.size()/2);
			
		}
		return 0.0;
	}

}
