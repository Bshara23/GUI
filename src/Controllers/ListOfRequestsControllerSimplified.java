package Controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
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

public class ListOfRequestsControllerSimplified implements Initializable {

	
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
	
	




	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		initTable();

		addRandomDataToTable();
	}


	// Random data...
	private void addRandomDataToTable() {
		Random rnd = new Random();

		String[] phases = new String[] { "Evaluation", "Closing", "Execution", "Testing", "Decision" };
		String[] statuses = new String[] { "Frozen", "Active" };

		ArrayList<TableDataRequests> strs = new ArrayList<TableDataRequests>();

		for (int i = 0; i < 20; i++) {
			String s1 = i + 1 + "";

			int day = rnd.nextInt(20);
			Date d = new Date(2020, 3, rnd.nextInt(28));
			String s2 = d.getDate() + "/" + d.getMonth() + "/" + d.getYear();

			String s3 = phases[rnd.nextInt(phases.length)];

			String s4 = statuses[rnd.nextInt(statuses.length)];

			day += rnd.nextInt(8);
			Date d2 = new Date(2020, 3, day);
			String s5 = d2.getDate() + "/" + d2.getMonth() + "/" + d2.getYear();

			strs.add(new TableDataRequests(s1, s2, s3, s4, s5));
		}

		addContentToTable(strs);
	}

	
	// Maps the values of the columns via the getters of the fields from the TableDataRequests class
	private void initTable() {
		
		tcRequestID.setCellValueFactory(new PropertyValueFactory<TableDataRequests, String>("S1"));
		tcIssueDate.setCellValueFactory(new PropertyValueFactory<TableDataRequests, String>("S2"));
		tcCurrentPhase.setCellValueFactory(new PropertyValueFactory<TableDataRequests, String>("S3"));
		tcStatus.setCellValueFactory(new PropertyValueFactory<TableDataRequests, String>("S4"));
		tcDeadline.setCellValueFactory(new PropertyValueFactory<TableDataRequests, String>("S5"));

	}

	private void addContentToTable(ArrayList<TableDataRequests> strs) {

		// Set the table items
		tblSupervisorRequests.setItems(FXCollections.observableArrayList(strs));

	}

	// Create a class with getters for the table
	public class TableDataRequests {
		
		// fields names don't matter...
		// Each field represents a column in the table
		public String s1, s2, s3, s4, s5;

		public TableDataRequests(String s1, String s2, String s3, String s4, String s5) {
			this.s1 = s1;
			this.s2 = s2;
			this.s3 = s3;
			this.s4 = s4;
			this.s5 = s5;
		}

		// Getters required for the PropertyValueFactory
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

	}

}
