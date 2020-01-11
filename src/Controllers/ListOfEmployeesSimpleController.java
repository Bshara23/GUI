package Controllers;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import ClientLogic.Client;
import Controllers.Logic.ControllerManager;
import Entities.ChangeRequest;
import Entities.Phase;
import Protocol.Command;
import Utility.DateUtil;
import Utility.Func;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class ListOfEmployeesSimpleController implements Initializable {

	private static final String GET_E_MPLOYEES_SIMPLE = "GetEMployeesSimple";

	@FXML
	private Canvas canvasRight;

	@FXML
	private TableView<TableEmps> tblEmployees;

	@FXML
	private TableColumn<TableEmps, String> tcEmpNumber;

	@FXML
	private TableColumn<TableEmps, String> tcUsername;

	@FXML
	private TableColumn<TableEmps, String> tcFirstName;

	@FXML
	private TableColumn<TableEmps, String> tcLastName;

	@FXML
	private Canvas canvasLeft;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Client.addMessageRecievedFromServer(GET_E_MPLOYEES_SIMPLE, srMsg -> {

			if (srMsg.getCommand() == Command.getEmployeesListSimple) {

				ArrayList<ArrayList<String>> data = (ArrayList<ArrayList<String>>) srMsg.getAttachedData()[0];

				loadDataIntoTable(data);

				Client.removeMessageRecievedFromServer(GET_E_MPLOYEES_SIMPLE);

			}
		});

		Client.getInstance().request(Command.getEmployeesListSimple);

	}

	private void loadDataIntoTable(ArrayList<ArrayList<String>> data) {
		initTable();

		ArrayList<TableEmps> tableContent = new ArrayList<TableEmps>();

		for (ArrayList<String> row : data) {

			TableEmps tableRow = new TableEmps(row.get(0), row.get(1), row.get(2), row.get(3));
			tableContent.add(tableRow);

		}

		tblEmployees.setItems(FXCollections.observableArrayList(tableContent));

	}

	private static Func f;
	public static void setOnRowClicked(Func func) {
		f = func;
	};
	
	@FXML
	public void clickItem(MouseEvent event) {
		if (event.getClickCount() == 2) // Checking double click
		{
			if(f != null)
				f.execute();
		}
	}

	private void initTable() {

		tcEmpNumber.setCellValueFactory(new PropertyValueFactory<TableEmps, String>("employeeNumber"));
		tcFirstName.setCellValueFactory(new PropertyValueFactory<TableEmps, String>("firstName"));
		tcLastName.setCellValueFactory(new PropertyValueFactory<TableEmps, String>("lastName"));
		tcUsername.setCellValueFactory(new PropertyValueFactory<TableEmps, String>("username"));

	}

	public class TableEmps {
		
		public String employeeNumber, username, firstName, lastName;

		public TableEmps(String employeeNumber, String username, String firstName, String lastName) {
			super();
			this.employeeNumber = employeeNumber;
			this.username = username;
			this.firstName = firstName;
			this.lastName = lastName;
		}

		public String getEmployeeNumber() {
			return employeeNumber;
		}

		public void setEmployeeNumber(String employeeNumber) {
			this.employeeNumber = employeeNumber;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		@Override
		public String toString() {
			return "TableDataRequests [employeeNumber=" + employeeNumber + ", username=" + username + ", firstName="
					+ firstName + ", lastName=" + lastName + "]";
		}

	}

}
