package ServerLogic;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Controllers.AnalyticsGUIController;
import Controllers.Logic.getActivityReportInject;
import Entities.ActivityReport;

class AnalyticsGUIControllerTest {

	AnalyticsGUIController controller;
	private ArrayList<Integer> s2;
	private ArrayList<Integer> s3;
	private ArrayList<Integer> s4;
	private ArrayList<Integer> s5;
	private ActivityReport activityReport;

	@BeforeEach
	void setUp() throws Exception {
		controller = new AnalyticsGUIController();

		s2 = set(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		s3 = set(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		s4 = set(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		s5 = set(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

		activityReport = new ActivityReport(-1, "rr", null, null, s2, s3, s4, s5, 0, 0, 0, 0, 0);
	}

	/**
	 * 
	 * Testing the active intervals average on positive numbers
	 * 
	 */
	@Test
	void testActiveAverage_PositiveNumbers_Success() {

		getActivityReportInject lambda = report -> {

			ArrayList<Integer> active = set(1, 2, 3, 2, 2, 1, 2, 3, 4, 1);

			activityReport.setActive(active);

			return activityReport;
		};

		controller.setGetAct(lambda);

		double avgActive = controller.getAverages(activityReport).get(0);

		assertTrue(avgActive == 2.1);
	}

	/**
	 * 
	 * Testing the active intervals average on negative numbers expecting to get an
	 * answer of zero since there is at least one negative number
	 */
	@Test
	void testActiveAverage_NegativeNumbers_Failure() {

		getActivityReportInject lambda = report -> {

			ArrayList<Integer> active = set(-1, -2, -3, -2, -2, -1, -2, -3, -4, -1);

			activityReport.setActive(active);

			return activityReport;
		};

		controller.setGetAct(lambda);

		double avgActive = controller.getAverages(activityReport).get(0);

		assertTrue(avgActive == 0);
	}

	/**
	 * 
	 * Testing the active intervals average on zeros
	 * 
	 */
	@Test
	void testActiveAverage_ZeroNumbers_Success() {

		getActivityReportInject lambda = report -> {

			ArrayList<Integer> active = set(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

			activityReport.setActive(active);

			return activityReport;
		};

		controller.setGetAct(lambda);

		double avgActive = controller.getAverages(activityReport).get(0);

		assertTrue(avgActive == 0);
	}

	/**
	 * 
	 * Testing the active intervals average on an empty array expecting to get zero
	 * since there is not elements
	 * 
	 */
	@Test
	void testActiveAverage_Empty_Success() {

		getActivityReportInject lambda = report -> {

			ArrayList<Integer> active = set();

			activityReport.setActive(active);

			return activityReport;
		};

		controller.setGetAct(lambda);

		double avgActive = controller.getAverages(activityReport).get(0);

		assertTrue(avgActive == 0);
	}

	/**
	 * 
	 * Testing the active intervals median on positive numbers
	 * 
	 */
	@Test
	void testActiveMedian_PositiveNumbers_Success() {

		getActivityReportInject lambda = report -> {

			ArrayList<Integer> active = set(1, 2, 3, 2, 2, 1, 2, 3, 4, 1);

			activityReport.setActive(active);

			return activityReport;
		};

		controller.setGetAct(lambda);

		double medActive = controller.getMedian(activityReport).get(0);

		assertTrue(medActive == 2);
	}

	/**
	 * 
	 * Testing the active intervals median on negative numbers expecting to get an
	 * answer of zero, since negative numbers are not possible for this type of
	 * statistics
	 */
	@Test
	void testActiveMedian_NegativeNumbers_Failure() {

		getActivityReportInject lambda = report -> {

			ArrayList<Integer> active = set(-1, -2, -3, -2, -2, -1, -2, -3, -4, -1);

			activityReport.setActive(active);

			return activityReport;
		};

		controller.setGetAct(lambda);

		double medActive = controller.getMedian(activityReport).get(0);

		assertTrue(medActive == 0);
	}

	/**
	 * 
	 * Testing the active intervals median on zeros
	 * 
	 */
	@Test
	void testActiveMedian_ZeroNumbers_Success() {

		getActivityReportInject lambda = report -> {

			ArrayList<Integer> active = set(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

			activityReport.setActive(active);

			return activityReport;
		};

		controller.setGetAct(lambda);

		double medActive = controller.getMedian(activityReport).get(0);

		assertTrue(medActive == 0);
	}

	/**
	 * 
	 * Testing the active intervals median on an empty array, expecting a zero
	 * 
	 */
	@Test
	void testActiveMedian_Empty_Success() {

		getActivityReportInject lambda = report -> {

			ArrayList<Integer> active = set();

			activityReport.setActive(active);

			return activityReport;
		};

		controller.setGetAct(lambda);

		double medActive = controller.getMedian(activityReport).get(0);

		assertTrue(medActive == 0);
	}

	/**
	 * 
	 * Testing the active intervals STD on positive numbers
	 * 
	 */
	@Test
	void testActiveSTD_PositiveNumbers_Success() {

		getActivityReportInject lambda = report -> {

			ArrayList<Integer> active = set(1, 2, 3, 2, 2, 1, 2, 3, 4, 1);

			activityReport.setActive(active);

			return activityReport;
		};

		controller.setGetAct(lambda);

		Double stdActive = controller.getSTD(activityReport).get(0);

		assertTrue(stdActive == 0.9433981132056605);
	}

	/**
	 * 
	 * Testing the active intervals STD on negative numbers expecting to get a zero
	 * since negative numbers are not possible on such statistics
	 */
	@Test
	void testActiveSTD_NegativeNumbers_Failure() {

		getActivityReportInject lambda = report -> {

			ArrayList<Integer> active = set(-1, -2, -3, -2, -2, -1, -2, -3, -4, -1);

			activityReport.setActive(active);

			return activityReport;
		};

		controller.setGetAct(lambda);

		double stdActive = controller.getSTD(activityReport).get(0);

		assertTrue(stdActive == 0);
	}

	/**
	 * 
	 * Testing the active intervals STD on zeros expecting to get a zero
	 * 
	 */
	@Test
	void testActiveSTD_ZeroNumbers_Success() {

		getActivityReportInject lambda = report -> {

			ArrayList<Integer> active = set(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

			activityReport.setActive(active);

			return activityReport;
		};

		controller.setGetAct(lambda);

		double stdActive = controller.getSTD(activityReport).get(0);

		assertTrue(stdActive == 0);
	}

	/**
	 * 
	 * Testing the active intervals STD on an empty array
	 * 
	 */
	@Test
	void testActiveSTD_Empty_Success() {

		getActivityReportInject lambda = report -> {

			ArrayList<Integer> active = set();

			activityReport.setActive(active);

			return activityReport;
		};

		controller.setGetAct(lambda);

		double stdActive = controller.getSTD(activityReport).get(0);

		assertTrue(stdActive == 0);
	}

	/**
	 * A helper function to initialize an array with the given data
	 */
	private ArrayList<Integer> set(int... data) {
		ArrayList<Integer> res = new ArrayList<Integer>(data.length);
		for (int i : data) {
			res.add(i);
		}
		return res;
	}

}
