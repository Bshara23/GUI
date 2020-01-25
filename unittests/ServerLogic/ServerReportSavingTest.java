package ServerLogic;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Entities.ActivityReport;
import Utility.DateUtil;

/**
 * Here we are testing the function that saves the activity report into the database from the server
 * the function checks if the reports is valid before inserting it
 * after passing the server test, we also check if the MySql has succeeded in inserting the report.
 * */
class ServerReportSavingTest {

	private ArrayList<Integer> s2;
	private ArrayList<Integer> s3;
	private ArrayList<Integer> s4;
	private ArrayList<Integer> s5;
	private ActivityReport activityReport;

	@BeforeEach
	void setUp() throws Exception {

		s2 = set(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		s3 = set(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		s4 = set(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		s5 = set(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

		activityReport = new ActivityReport(999, "rr", DateUtil.now(), null, s2, s3, s4, s5, 0, 0, 0, 0, 0);

		Server.TEST_MODE = true;
		Server.getInstance().initialize(5555, "root", "Aa123456", "icm", 5);
	}


	@Test
	/**
	 * Inserting a report with an array of positive numbers, this case should be valid.
	 * */
	void insertReport_PositiveArray_Success() {

		activityReport.setActive(set(1, 2, 1, 1, 2, 1, 1, 2, 1, 2));

		boolean res = Server.getInstance().InsertActivityReport(activityReport);

		assertTrue(res);
	}

	
	@Test
	/**
	 * Inserting a report with an array of positive numbers that are less than the constant number of intervals (which is 10), this case should be valid.
	 * */
	void insertReport_PositiveArrayLessThan10Numbers_Success() {

		activityReport.setActive(set(1, 2, 1, 1, 2, 1, 2));

		boolean res = Server.getInstance().InsertActivityReport(activityReport);

		assertTrue(res);
	}
	
	@Test
	/**
	 * Inserting a report with an array of positive and negative numbers, this case should be invalid.
	 * */
	void insertReport_NegativeArray_Fail() {

		activityReport.setActive(set(1, 2, 1, 1, -2, 1, 1, 2, 1, 2));

		boolean res = Server.getInstance().InsertActivityReport(activityReport);

		assertFalse(res);
	}

	@Test
	/**
	 * Inserting a report with an array of size 0 (empty array), this case should be invalid.
	 * */
	void insertReport_EmptyArray_Fail() {

		activityReport.setActive(set());

		boolean res = Server.getInstance().InsertActivityReport(activityReport);

		assertFalse(res);
	}

	@Test
	/**
	 * Inserting a report without one of the arrays (the array is null), this case should be invalid.
	 * */
	void insertReport_NullArray_Fail() {

		boolean res = Server.getInstance().InsertActivityReport(activityReport);

		assertFalse(res);
	}

	@Test
	/**
	 * Inserting a report without a name, this case should be invalid.
	 * */
	void insertReport_NoName_Fail() {

		activityReport.setName("");
		activityReport.setActive(set(1, 2, 1, 1, 2, 1, 1, 2, 1, 2));
		boolean res = Server.getInstance().InsertActivityReport(activityReport);

		assertFalse(res);
	}

	@Test
	/**
	 * Inserting a report without a date, this case should be invalid.
	 * */
	void insertReport_NoDateDefined_Fail() {

		activityReport.setDate(null);
		activityReport.setActive(set(1, 2, 1, 1, 2, 1, 1, 2, 1, 2));
		boolean res = Server.getInstance().InsertActivityReport(activityReport);

		assertFalse(res);
	}


	/**
	 * A helper function that creates and return an array using the given data
	 * */
	private ArrayList<Integer> set(int... data) {
		ArrayList<Integer> res = new ArrayList<Integer>(10);
		for (int i : data) {
			res.add(i);
		}
		return res;
	}
}
