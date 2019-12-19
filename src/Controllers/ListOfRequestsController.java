package Controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

import Controllers.Logic.CommonEffects;
import Controllers.Logic.ControllerManager;
import Controllers.Logic.FxmlNames;
import Controllers.Logic.NavigationBar;
import Utility.AppManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class ListOfRequestsController implements Initializable {

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

		tblSupervisorRequests.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		addRandomDataToTable();

		// TODO: bug. the line does not work unless the function has been called by the
		// mouse pressed event!!
		// Select the first job at the initialization
		if (jobs.size() != 0) {
			Node node = jobs.get(0);
			selectNode(node);
		}

		System.out.println("Disable jobs = " + disableAllJobs);
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



	private void addRandomDataToTable() {
		Random rnd = new Random();

		String[] statuses = new String[] { "Frozen", "Active" };

		ArrayList<TableDataRequests> strs = new ArrayList<TableDataRequests>();

		for (int i = 0; i < 20; i++) {
			String s1 = i + 1 + "";

			int day = rnd.nextInt(20);
			GregorianCalendar  d = new GregorianCalendar(2020, 3, rnd.nextInt(28));
			String s2 = d.getGregorianChange().toGMTString();


			String s4 = statuses[rnd.nextInt(statuses.length)];

			day += rnd.nextInt(8);
			Date d2 = new Date(2020, 3, day);
			String s5 = d2.getDate() + "/" + d2.getMonth() + "/" + d2.getYear();

			String s3 = "";

			
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
			System.out.println("Supervise");
			break;
		case "Evaluate":
			addRandomDataToTable();
			NavigationBar.setCurrentPage("Request Details", FxmlNames.REQUEST_DETAILS_EVALUATE);
			
			break;
		case "Decide":
			addRandomDataToTable();
			System.out.println("Decide");

			break;
		case "Execute":
			addRandomDataToTable();
			System.out.println("Execute");
			NavigationBar.setCurrentPage("Request Details (Executer View)", FxmlNames.REQUEST_DETAILS_EXECUTIONER);


			break;
		case "Examine":
			addRandomDataToTable();
			System.out.println("Examine");
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

			// System.out.println(tblSupervisorRequests.getSelectionModel().getSelectedItem().toString());

			NavigationBar.next(NavigationBar.getNextPage().getPageTitle(),
					NavigationBar.getNextPage().getPageLocation());

		}
	}

}
