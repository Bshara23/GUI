package Controllers;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

import ClientLogic.Client;
import Controllers.Logic.CommonEffects;
import Controllers.Logic.ControllerManager;
import Controllers.Logic.FxmlNames;
import Controllers.Logic.NavigationBar;
import Controllers.Logic.RequestsType;
import Entities.ChangeRequest;
import Protocol.Command;
import Utility.AppManager;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class ListOfRequestsController implements Initializable {

	private static final String GET_REQS_LIST_CTRL = "dwad2414r2rr";

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private AnchorPane mainAnchor;

	@FXML
	private TableView<TableDataRequests> tblSupervisorRequests;

	@FXML
	private TableColumn<TableDataRequests, String> tcRequestID;

	@FXML
	private TableColumn<TableDataRequests, String> tcIssueDate;

	@FXML
	private TableColumn<TableDataRequests, String> tcCurrentPhase;

	@FXML
	private TableColumn<TableDataRequests, String> tcStatus;

	@FXML
	private TableColumn<TableDataRequests, String> tcDeadline;
	@FXML
	private HBox hbRequestsType;

	@FXML
	private HBox apAnalyze;

	@FXML
	private HBox apDecide;

	@FXML
	private HBox apExecute;

	@FXML
	private HBox apExamine;

	@FXML
	private ImageView imgSettings;

	@FXML
	private ImageView imgRefresh;

	@FXML
	private ImageView imgMenu;

	@FXML
	private ImageView imgSearch;

	@FXML
	private ImageView imgBack;

	@FXML
	private ImageView imgForward;

	@FXML
	private HBox apSupervise;

	@FXML
	private Line lineTableJob;

	@FXML
	private Line lineBottomJobs;
	@FXML
	private Text txtPageHeader;

	private ArrayList<Node> tableButtons, jobs;

	public static boolean disableAllJobs = false;
	public static String pageHeader = "";
	public static RequestsType requestsType;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initTable();

		tableButtons = new ArrayList<Node>();
		jobs = new ArrayList<Node>();

		jobs.addAll(hbRequestsType.getChildren());

		tableButtons.add(imgSearch);
		tableButtons.add(imgSettings);
		tableButtons.add(imgRefresh);
		tableButtons.add(imgMenu);

		tableButtons.add(imgBack);
		tableButtons.add(imgForward);

		// Set the on mouse pressed event for the jobs
		for (Node node : jobs) {
			node.setOnMousePressed(event -> {
				selectNode(node);
			});
			node.setOnMouseEntered(event -> {
				ControllerManager.setEffectConditioned(node, CommonEffects.REQUESTS_TABLE_ELEMENT_BLACK,
						CommonEffects.REQUESTS_TABLE_ELEMENT_BLUE);
			});
			node.setOnMouseExited(event -> {
				ControllerManager.setEffectConditioned(node, CommonEffects.REQUESTS_TABLE_ELEMENT_GRAY,
						CommonEffects.REQUESTS_TABLE_ELEMENT_BLUE);
			});
		}

		ControllerManager.setEffect(lineTableJob, CommonEffects.REQUESTS_TABLE_ELEMENT_BLUE);

		// Set the on mouse pressed even for the table buttons
		for (Node node : tableButtons) {
			node.setCursor(Cursor.HAND);
			node.setOnMouseEntered(event -> {
				ControllerManager.setEffect(node, CommonEffects.REQUESTS_TABLE_ELEMENT_BLACK);
			});
			node.setOnMouseExited(event -> {
				ControllerManager.setEffect(node, CommonEffects.REQUESTS_TABLE_ELEMENT_GRAY);
			});
		}

		ControllerManager.setEffect(jobs, CommonEffects.REQUESTS_TABLE_ELEMENT_GRAY);
		ControllerManager.setEffect(tableButtons, CommonEffects.REQUESTS_TABLE_ELEMENT_GRAY);

//		tblSupervisorRequests.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//		final ObservableList<TablePosition> selectedCells = tblSupervisorRequests.getSelectionModel().getSelectedCells();
//		selectedCells.addListener(new ListChangeListener<TablePosition>() {
//		    @Override
//		    public void onChanged(Change change) {
//		        for (TablePosition pos : selectedCells) {
//		        	if(pos.getColumn() == 0) {
//			            System.out.println("row 0 is selected, call a function here");
//		        	}
//		        	if(pos.getColumn() == 1) {
//			            System.out.println("row 0 is selected, call a function here");
//		        	}
//		        }
//		    };
//		});

		// Set a listener for the requests list from the server.

		// addRandomDataToTable(); TODO

		Client.addMessageRecievedFromServer(GET_REQS_LIST_CTRL, srMsg -> {
			
			System.out.println("Server: " + srMsg.getCommand());

			
			if (srMsg.getCommand() == Command.GetMyRequests) {
				System.out.println("Loading data");

				Object[] objs = (Object[]) srMsg.getAttachedData();
				ArrayList<ChangeRequest> myRequests = (ArrayList<ChangeRequest>) objs[0];
				RequestsType requestType = (RequestsType) objs[1];

				switch (requestType) {
				case myRequests:

					loadRequestToTable(myRequests);

					break;

				default:
					break;
				}
			}
		});

		Client.getInstance().request(Command.GetMyRequests,
				new Object[] { ClientGUI.userName, RequestsType.myRequests });

		// RequestsType rType = firstRelatedRequests(ClientGUI.myID);
		// requestDataForTable(ClientGUI.myID, rType);

		// TODO: bug. the line does not work unless the function has been called by the
		// mouse pressed event!!
		// Select the first job at the initialization
//		if (jobs.size() != 0) {
//			Node node = jobs.get(0);
//			selectNode(node);
//		}

		ArrayList<Node> nodes = ControllerManager.getAllNodes(hbRequestsType);
		nodes.add(lineTableJob);
		nodes.add(lineBottomJobs);
		for (Node node : nodes) {
			node.setDisable(disableAllJobs);
			node.setOpacity(disableAllJobs ? 0 : 1);
		}
		if (disableAllJobs) {
			NavigationBar.setCurrentPage("Request Details", FxmlNames.REQUEST_DETAILS_MY);
		}
		txtPageHeader.setText(pageHeader);

	}

	@Override
	protected void finalize() throws Throwable {
		Client.removeMessageRecievedFromServer(GET_REQS_LIST_CTRL);
		super.finalize();
	}

	// TODO: implement
	private RequestsType firstRelatedRequests(long myID) {
		return RequestsType.supervision;
	}

	private void requestDataForTable(long myID, RequestsType rType) {

		switch (rType) {
		case myRequests:

			Client.getInstance().request(Command.GetMyRequests, new Object[] { ClientGUI.userName, rType });
			break;

		default:
			break;
		}

	}

	private void loadRequestToTable(ArrayList<ChangeRequest> changeRequests) {

		ArrayList<TableDataRequests> strs = new ArrayList<TableDataRequests>();

		for (ChangeRequest changeRequest : changeRequests) {

			strs.add(new TableDataRequests(changeRequest.getRequestID() + "",
					changeRequest.getDateOfRequest().toString(),
					(changeRequest.getEndDateOfRequest().toEpochDay() - changeRequest.getDateOfRequest().toEpochDay()) + "" , "Active",
					changeRequest.getEndDateOfRequest().toString()));
		}

		addContentToTable(strs);
	}

	private void addRandomDataToTable() {

		Random rnd = new Random();

		String[] statuses = new String[] { "Frozen", "Active" };

		ArrayList<TableDataRequests> strs = new ArrayList<TableDataRequests>();

		for (int i = 0; i < 20; i++) {
			String s1 = rnd.nextInt(200) + 1 + "";

			int day = rnd.nextInt(28);

			Calendar calendar = new GregorianCalendar(2013, 1, day);

			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

			String s2 = dayOfMonth + "/" + month + "/" + year;

			String s4 = statuses[rnd.nextInt(statuses.length)];

			int rand = rnd.nextInt(8);
			calendar.add(Calendar.DAY_OF_MONTH, rand);

			int year1 = calendar.get(Calendar.YEAR);
			int month1 = calendar.get(Calendar.MONTH);
			int dayOfMonth1 = calendar.get(Calendar.DAY_OF_MONTH);

			String s5 = dayOfMonth1 + "/" + month1 + "/" + year1;

			String s3 = rand + " Days";

			strs.add(new TableDataRequests(s1, s2, s3, s4, s5));
		}

		addContentToTable(strs);
	}

	private void selectNode(Node node) {

		ControllerManager.setEffect(jobs, CommonEffects.REQUESTS_TABLE_ELEMENT_GRAY);
		ControllerManager.setEffect(node, CommonEffects.REQUESTS_TABLE_ELEMENT_BLUE);
		lineTableJob.setStartX(node.getBoundsInParent().getMinX() - 76);
		lineTableJob.setEndX(node.getBoundsInParent().getMaxX() - 76);

		updateRequestsTableByJob(node);
	}

	private void updateRequestsTableByJob(Node node) {
		Node textNode = ((HBox) node).getChildren().get(1);
		String text = ((Text) textNode).getText();

		switch (text) {
		case "Supervise":
			addRandomDataToTable();
			NavigationBar.setCurrentPage("Request Details (Supervisor View)", FxmlNames.REQUEST_DETAILS_SUPERVISOR);
			break;
		case "Evaluate":
			addRandomDataToTable();
			NavigationBar.setCurrentPage("Request Details (Evaluator View)", FxmlNames.REQUEST_DETAILS_EVALUATE);

			break;
		case "Decide":
			addRandomDataToTable();
			NavigationBar.setCurrentPage("Request Details (Committee Members View)",
					FxmlNames.REQUEST_DETAILS_DECISION);

			break;
		case "Execute":
			addRandomDataToTable();
			NavigationBar.setCurrentPage("Request Details (Executer View)", FxmlNames.REQUEST_DETAILS_EXECUTIONER);

			break;
		case "Examine":
			addRandomDataToTable();
			NavigationBar.setCurrentPage("Request Details (Examiner View)", FxmlNames.REQUEST_DETAILS_EXAMINER);

			break;
		default:
			System.err.println("Error, case not defined! [updateRequestsTableByJob]");
			break;
		}

	}

	private void initTable() {

		tcRequestID.setCellValueFactory(new PropertyValueFactory<TableDataRequests, String>("S1"));
		tcIssueDate.setCellValueFactory(new PropertyValueFactory<TableDataRequests, String>("S2"));
		tcCurrentPhase.setCellValueFactory(new PropertyValueFactory<TableDataRequests, String>("S3"));
		tcStatus.setCellValueFactory(new PropertyValueFactory<TableDataRequests, String>("S4"));
		tcDeadline.setCellValueFactory(new PropertyValueFactory<TableDataRequests, String>("S5"));

	}

	private void addContentToTable(ArrayList<TableDataRequests> strs) {
		// tblSupervisorRequests.getSelectionModel().setCellSelectionEnabled(true);

		tblSupervisorRequests.setItems(FXCollections.observableArrayList(strs));

	}

	public class TableDataRequests {
		public String s1, s2, s3, s4, s5;

		public TableDataRequests(String s1, String s2, String s3, String s4, String s5) {
			this.s1 = s1;
			this.s2 = s2;
			this.s3 = s3;
			this.s4 = s4;
			this.s5 = s5;
		}

		public String getS1() {
			return s1;
		}

		public String getS2() {
			return s2;
		}

		public String getS3() {
			return s3;
		}

		public String getS4() {
			return s4;
		}

		public String getS5() {
			return s5;
		}

		@Override
		public String toString() {
			return "TableDataRequests [s1=" + s1 + ", s2=" + s2 + ", s3=" + s3 + ", s4=" + s4 + ", s5=" + s5 + "]";
		}

	}

	@FXML
	public void clickItem(MouseEvent event) {
		if (event.getClickCount() == 2) // Checking double click
		{

			NavigationBar.next(NavigationBar.getNextPage().getPageTitle(),
					NavigationBar.getNextPage().getPageLocation());

		}
	}

	@FXML
	void onMouseEntered(MouseEvent event) {

	}

}
