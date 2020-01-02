package Controllers;

import java.net.URL;
import java.sql.Timestamp;
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
import Entities.ChangeRequest;
import Entities.Phase;
import Protocol.Command;
import Protocol.PhaseType;
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

public class ListOfRequestsForTreatmentController implements Initializable {

	private static final String GET_MY_REQUESTS_AS_SUPERVISOR = "GetMyRequestsAsSupervisor";

	private static final String GET_COUNT_OF_REQUESTS = "GET_COUINT_RESQUEST234256354";

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
    private TableColumn<TableDataRequests, String> tcIssuedBy;

    @FXML
    private TableColumn<TableDataRequests, String> tcPhaseStatus;

    @FXML
    private TableColumn<TableDataRequests, String> tcPhaseStartingDate;

    @FXML
    private TableColumn<TableDataRequests, String> tcPhaseDeadline;

    @FXML
    private TableColumn<TableDataRequests, String> tcPhaseTimeLeft;

    @FXML
    private TableColumn<TableDataRequests, String> tcHasBeenTimeExtended;
	
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

	@FXML
	private Text txtRequestsCount;

	private ArrayList<Node> tableButtons, jobs;

	public static boolean disableAllJobs = false;
	public static PhaseType phaseType;

	private int currentRowIndex = 0;
	private int countOfRequests;
	private int rowCountLimit = 16;

	@SuppressWarnings("unchecked")
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

		// Set a listener for the requests list from the server.

		// TODO: implement
		Client.addMessageRecievedFromServer(GET_COUNT_OF_REQUESTS + 2, srMsg -> {

			if (srMsg.getCommand() == Command.countOfObjects) {

				countOfRequests = (int) srMsg.getAttachedData()[0];

				// TODO: this changed order from username, PhaseType to PhaseType, username
				Client.getInstance().request(Command.GetMyRequests, PhaseType.myRequests, ClientGUI.userName,
						currentRowIndex, rowCountLimit);
			}
		});

		Client.addMessageRecievedFromServer(GET_REQS_LIST_CTRL, srMsg -> {

			if (srMsg.getCommand() == Command.GetMyRequests) {

				PhaseType requestType = (PhaseType) srMsg.getAttachedData()[0];
				ArrayList<ChangeRequest> myRequests = (ArrayList<ChangeRequest>) srMsg.getAttachedData()[1];
				int size = myRequests.size();
				txtRequestsCount.setText("Size: " + size);
				switch (requestType) {

				// TODO: add the closing with the supervision; or instead of
				case supervision:

					loadIntoTable(myRequests);
					break;

				case examination:
					loadIntoTable(myRequests);

					break;

				case execution:
					loadIntoTable(myRequests);

					break;

				case decision:
					loadIntoTable(myRequests);

					break;

				case evaluation:
					loadIntoTable(myRequests);

					break;

				// TODO: add the rest of the phases
				default:
					break;
				}
			}
		});

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

		imgForward.setOnMousePressed(event -> {

			if (currentRowIndex + rowCountLimit < countOfRequests) {
				currentRowIndex += rowCountLimit;
				Client.getInstance().request(Command.GetMyRequests, ClientGUI.userName, PhaseType.myRequests,
						currentRowIndex, rowCountLimit);
				txtRequestsCount.setText(
						(currentRowIndex + 1) + "-" + (currentRowIndex + rowCountLimit) + " of " + countOfRequests);

			}
		});

		imgBack.setOnMousePressed(event -> {

			if (currentRowIndex - rowCountLimit >= 0) {
				currentRowIndex -= rowCountLimit;
				Client.getInstance().request(Command.GetMyRequests, ClientGUI.userName, PhaseType.myRequests,
						currentRowIndex, rowCountLimit);

				txtRequestsCount.setText(
						(currentRowIndex + 1) + "-" + (currentRowIndex + rowCountLimit) + " of " + countOfRequests);
			}

		});

		/**
		 * 
		 * Adding a behavior for all of the five phases TODO: make one for the closing
		 * 
		 */

	}

	private void loadIntoTable(ArrayList<ChangeRequest> myRequests) {
		ArrayList<TableDataRequests> tableContent = new ArrayList<TableDataRequests>();

		for (ChangeRequest cr : myRequests) {

			Phase ph = cr.getPhases().get(0);
			
			String issuedBy = cr.getUsername();
			String phaseStatus = ph.getStatus();
			String phaseStartingDate = ControllerManager.getDateTime(ph.getStartingDate());
			String phaseDeadline = ControllerManager.getDateTime(ph.getDeadline());
			String phaseTimeLeft = ControllerManager.getDateTimeDiff(ph.getStartingDate(), ph.getDeadline());
			String hasBeenTimeExtended = ph.isHasBeenTimeExtended() ? "Yes" : "No";
			
			
			TableDataRequests tableRow = new TableDataRequests(issuedBy, phaseStatus, phaseStartingDate, phaseDeadline,
					phaseTimeLeft, hasBeenTimeExtended);
			tableContent.add(tableRow);
		}

		tblSupervisorRequests.setItems(FXCollections.observableArrayList(tableContent));

	}

	@Override
	protected void finalize() throws Throwable {
		Client.removeMessageRecievedFromServer(GET_COUNT_OF_REQUESTS + 2);
		Client.removeMessageRecievedFromServer(GET_MY_REQUESTS_AS_SUPERVISOR);

		super.finalize();
	}

	// TODO: implement
	private PhaseType firstRelatedRequests(long myID) {
		return PhaseType.supervision;
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
			Client.getInstance().request(Command.GetMyRequests, PhaseType.supervision, ClientGUI.empNumber);
			// This only prepares the next page in case that the user has double clicked on
			// one of the requests from the table
			NavigationBar.setCurrentPage("Request Details (Supervisor View)", FxmlNames.REQUEST_DETAILS_SUPERVISOR);
			break;
		case "Evaluate":
			Client.getInstance().request(Command.GetMyRequests, PhaseType.evaluation, ClientGUI.empNumber);
			NavigationBar.setCurrentPage("Request Details (Evaluator View)", FxmlNames.REQUEST_DETAILS_EVALUATE);

			break;
		case "Decide":
			Client.getInstance().request(Command.GetMyRequests, PhaseType.decision, ClientGUI.empNumber);
			NavigationBar.setCurrentPage("Request Details (Committee Members View)",
					FxmlNames.REQUEST_DETAILS_DECISION);

			break;
		case "Execute":
			Client.getInstance().request(Command.GetMyRequests, PhaseType.execution, ClientGUI.empNumber);
			NavigationBar.setCurrentPage("Request Details (Executer View)", FxmlNames.REQUEST_DETAILS_EXECUTIONER);

			break;
		case "Examine":
			Client.getInstance().request(Command.GetMyRequests, PhaseType.examination, ClientGUI.empNumber);
			NavigationBar.setCurrentPage("Request Details (Examiner View)", FxmlNames.REQUEST_DETAILS_EXAMINER);

			break;
		default:
			System.err.println("Error, case not defined! [updateRequestsTableByJob]");
			break;
		}

	}

	private void initTable() {

		
		
		tcHasBeenTimeExtended.setCellValueFactory(new PropertyValueFactory<TableDataRequests, String>("hasBeenTimeExtended"));
		tcIssuedBy.setCellValueFactory(new PropertyValueFactory<TableDataRequests, String>("issuedBy"));
		tcPhaseDeadline.setCellValueFactory(new PropertyValueFactory<TableDataRequests, String>("phaseDeadline"));
		tcPhaseStartingDate.setCellValueFactory(new PropertyValueFactory<TableDataRequests, String>("phaseStartingDate"));
		tcPhaseStatus.setCellValueFactory(new PropertyValueFactory<TableDataRequests, String>("phaseStatus"));
		tcPhaseTimeLeft.setCellValueFactory(new PropertyValueFactory<TableDataRequests, String>("phaseTimeLeft"));


	}

	public class TableDataRequests {
		public String issuedBy, phaseStatus, phaseStartingDate, phaseDeadline, phaseTimeLeft, hasBeenTimeExtended;

		public TableDataRequests(String issuedBy, String phaseStatus, String phaseStartingDate, String phaseDeadline,
				String phaseTimeLeft, String hasBeenTimeExtended) {
			super();
			this.issuedBy = issuedBy;
			this.phaseStatus = phaseStatus;
			this.phaseStartingDate = phaseStartingDate;
			this.phaseDeadline = phaseDeadline;
			this.phaseTimeLeft = phaseTimeLeft;
			this.hasBeenTimeExtended = hasBeenTimeExtended;
		}

		public String getIssuedBy() {
			return issuedBy;
		}

		public void setIssuedBy(String issuedBy) {
			this.issuedBy = issuedBy;
		}

		public String getPhaseStatus() {
			return phaseStatus;
		}

		public void setPhaseStatus(String phaseStatus) {
			this.phaseStatus = phaseStatus;
		}

		public String getPhaseStartingDate() {
			return phaseStartingDate;
		}

		public void setPhaseStartingDate(String phaseStartingDate) {
			this.phaseStartingDate = phaseStartingDate;
		}

		public String getPhaseDeadline() {
			return phaseDeadline;
		}

		public void setPhaseDeadline(String phaseDeadline) {
			this.phaseDeadline = phaseDeadline;
		}

		public String getPhaseTimeLeft() {
			return phaseTimeLeft;
		}

		public void setPhaseTimeLeft(String phaseTimeLeft) {
			this.phaseTimeLeft = phaseTimeLeft;
		}

		public String getHasBeenTimeExtended() {
			return hasBeenTimeExtended;
		}

		public void setHasBeenTimeExtended(String hasBeenTimeExtended) {
			this.hasBeenTimeExtended = hasBeenTimeExtended;
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
