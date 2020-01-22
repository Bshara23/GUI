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

	@Test
	void testActive_Average_PositiveNumbers() {

		getActivityReportInject lambda = report -> {
			
			ArrayList<Integer> active = set(1, 2, 3, 2, 2, 1, 2, 3, 4, 1);
	
			activityReport.setActive(active);
			
			return activityReport;
		};
		
		controller.setGetAct(lambda);
		
		double avgActive = controller.getAverages(activityReport).get(0);
		
		assertTrue(avgActive == 2.1);
	}
	
	
	@Test
	void testActive_Average_NegativeNumbers() {

		getActivityReportInject lambda = report -> {
			
			ArrayList<Integer> active = set(-1, -2, -3, -2, -2, -1, -2, -3, -4, -1);
	
			activityReport.setActive(active);
			
			return activityReport;
		};
		
		controller.setGetAct(lambda);
		
		double avgActive = controller.getAverages(activityReport).get(0);
		
		assertTrue(avgActive == 0);
	}
	
	@Test
	void testActive_Average_ZeroNumbers() {

		getActivityReportInject lambda = report -> {
			
			ArrayList<Integer> active = set(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	
			activityReport.setActive(active);
			
			return activityReport;
		};
		
		controller.setGetAct(lambda);
		
		double avgActive = controller.getAverages(activityReport).get(0);
		
		assertTrue(avgActive == 0);
	}
	
	
	

	@Test
	void testActive_Average_Empty() {

		getActivityReportInject lambda = report -> {
			
			ArrayList<Integer> active = set();
	
			activityReport.setActive(active);
			
			return activityReport;
		};
		
		controller.setGetAct(lambda);
		
		double avgActive = controller.getAverages(activityReport).get(0);
		
		assertTrue(avgActive == 0);
	}
	
	
	
	
	
	
	
	@Test
	void testActive_Median_PositiveNumbers() {

		getActivityReportInject lambda = report -> {
			
			ArrayList<Integer> active = set(1, 2, 3, 2, 2, 1, 2, 3, 4, 1);
	
			activityReport.setActive(active);
			
			return activityReport;
		};
		
		controller.setGetAct(lambda);
		
		double medActive = controller.getMedian(activityReport).get(0);
		
		assertTrue(medActive == 2);
	}
	
	
	@Test
	void testActive_Median_NegativeNumbers() {

		getActivityReportInject lambda = report -> {
			
			ArrayList<Integer> active = set(-1, -2, -3, -2, -2, -1, -2, -3, -4, -1);
	
			activityReport.setActive(active);
			
			return activityReport;
		};
		
		controller.setGetAct(lambda);
		
		double medActive = controller.getMedian(activityReport).get(0);
		
		assertTrue(medActive == 0);
	}
	
	@Test
	void testActive_Median_ZeroNumbers() {

		getActivityReportInject lambda = report -> {
			
			ArrayList<Integer> active = set(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	
			activityReport.setActive(active);
			
			return activityReport;
		};
		
		controller.setGetAct(lambda);
		
		double medActive = controller.getMedian(activityReport).get(0);
		
		assertTrue(medActive == 0);
	}
	

	@Test
	void testActive_Median_Empty() {

		getActivityReportInject lambda = report -> {
			
			ArrayList<Integer> active = set();
	
			activityReport.setActive(active);
			
			return activityReport;
		};
		
		controller.setGetAct(lambda);
		
		double medActive = controller.getMedian(activityReport).get(0);
		
		assertTrue(medActive == 0);
	}
	
	
	
	
	
	
	@Test
	void testActive_STD_PositiveNumbers() {

		getActivityReportInject lambda = report -> {
			
			ArrayList<Integer> active = set(1, 2, 3, 2, 2, 1, 2, 3, 4, 1);
	
			activityReport.setActive(active);
			
			return activityReport;
		};
		
		controller.setGetAct(lambda);
		
		Double stdActive = controller.getSTD(activityReport).get(0);
		
		assertTrue(stdActive == 0.9433981132056605);
	}
	
	
	@Test
	void testActive_STD_NegativeNumbers() {

		getActivityReportInject lambda = report -> {
			
			ArrayList<Integer> active = set(-1, -2, -3, -2, -2, -1, -2, -3, -4, -1);
	
			activityReport.setActive(active);
			
			return activityReport;
		};
		
		controller.setGetAct(lambda);
		
		double stdActive = controller.getSTD(activityReport).get(0);
		
		assertTrue(stdActive == 0);
	}
	
	@Test
	void testActive_STD_ZeroNumbers() {

		getActivityReportInject lambda = report -> {
			
			ArrayList<Integer> active = set(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	
			activityReport.setActive(active);
			
			return activityReport;
		};
		
		controller.setGetAct(lambda);
		
		double stdActive = controller.getSTD(activityReport).get(0);
		
		assertTrue(stdActive == 0);
	}
	
	@Test
	void testActive_STD_Empty() {

		getActivityReportInject lambda = report -> {
			
			ArrayList<Integer> active = set();
	
			activityReport.setActive(active);
			
			return activityReport;
		};
		
		controller.setGetAct(lambda);
		
		double stdActive = controller.getSTD(activityReport).get(0);
		
		assertTrue(stdActive == 0);
	}
	
	private ArrayList<Integer> set(int... data){
		ArrayList<Integer> res = new ArrayList<Integer>(10);
		for (int i : data) {
			res.add(i);
		}
		return res;
	}

}
