package ServerLogic;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Entities.ActivityReport;

/**
 * This class tests fetching the report data from the database by the functions
 * that were defined in the server. In this case, we are just testing the active
 * requests.
 */
class ServerReportFetchingTest {

	@BeforeEach
	void setUp() throws Exception {

		Server.getInstance().initialize(5555, "root", "Aa123456", "icm", 5);
	}

	

	@Test
	/**
	 * Testing a general range, nothing special
	 */
	void getReport_DateRange1_Success() {

		LocalDate dFrom = LocalDate.of(2018, 1, 1);
		LocalDate dTo = LocalDate.of(2018, 1, 20);
		
		Server.TEST_MODE = true;
		ActivityReport res = Server.getInstance().getActivityReport(dFrom, dTo);
		ArrayList<Integer> intervals = res.getActive();
		int total = res.getTotalActive();

		assertTrue(total == 3 && matchIntervals(intervals, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0));
	}
	
	
	@Test
	/**
	 * Testing a range where there exists a change request on both of the limits (included)
	 */
	void getReport_DateRange2_Success() {

		LocalDate dFrom = LocalDate.of(2018, 1, 6);
		LocalDate dTo = LocalDate.of(2018, 1, 26);
		ActivityReport res = Server.getInstance().getActivityReport(dFrom, dTo);
		ArrayList<Integer> intervals = res.getActive();
		int total = res.getTotalActive();

		assertTrue(total == 2 && matchIntervals(intervals, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1));
	}
	
	
	@Test
	/**
	 * Testing a range where there exists a change request on both of the limits (excluded)
	 */
	void getReport_DateRange3_Success() {

		LocalDate dFrom = LocalDate.of(2018, 1, 7);
		LocalDate dTo = LocalDate.of(2018, 1, 25);
		ActivityReport res = Server.getInstance().getActivityReport(dFrom, dTo);
		ArrayList<Integer> intervals = res.getActive();
		int total = res.getTotalActive();

		assertTrue(total == 0 && matchIntervals(intervals, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
	}
	
	
	/**
	 * A helper function that checks if the intervals match the given data
	 */
	private boolean matchIntervals(ArrayList<Integer> intervals, int... data) {

		if (data.length != intervals.size())
			return false;

		for (int i = 0; i < data.length; i++)
			if (data[i] != intervals.get(i))
				return false;

		return true;
	}

}
