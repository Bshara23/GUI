package ServerLogic;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;

import Entities.ActivityReport;
import Utility.DateUtil;

public class temp {
	
	private int diff(Timestamp a, Timestamp b) {

		return (int) (b.getTime() - a.getTime());
	}
	public ActivityReport getActivityReport(ActivityReport ac, Timestamp dFrom, Timestamp dTo, MySQL db) {
		
		
		ArrayList<Integer> activeCnt = new ArrayList<Integer>();
		ArrayList<Integer> freezeCnt = new ArrayList<Integer>();
		ArrayList<Integer> closedCnt = new ArrayList<Integer>();
		ArrayList<Integer> rejectedCnt = new ArrayList<Integer>();
		ArrayList<Integer> workingDaysCnt = new ArrayList<Integer>();

		int diff = diff(dTo, dFrom);

		int interval = 10;
		if (diff % interval == 0) {
			interval = diff / interval;
		} else
			interval = diff / interval + 1;

		for (Timestamp i = dFrom; !i.equals(dTo);) {

			Timestamp to = DateUtil.add(i, interval - 1, 0);

			if (!to.after(dTo)) {

				activeCnt.add(db.countOfActiveReqests(i, to));
				freezeCnt.add(db.countOfFreezeReqests(i, to));
				closedCnt.add(db.countOfClosedRequests(i, to));
				rejectedCnt.add(db.countOfDeniedRequests(i, to));
				workingDaysCnt.add(db.countOfTotalWorkingDays(i, to));

				i = DateUtil.add(to, 1, 0);

			} else {

				activeCnt.add(db.countOfActiveReqests(i, dTo));
				freezeCnt.add(db.countOfFreezeReqests(i, dTo));
				closedCnt.add(db.countOfClosedRequests(i, dTo));
				rejectedCnt.add(db.countOfDeniedRequests(i, dTo));
				workingDaysCnt.add(db.countOfTotalWorkingDays(i, dTo));

				i = dTo;
			}

		}



		int totalActiveCnt = db.countOfActiveReqests(dFrom, dTo);
		int totalFreezeCnt = db.countOfFreezeReqests(dFrom, dTo);
		int totalClosedCnt = db.countOfClosedRequests(dFrom, dTo);
		int totalRejectedCnt = db.countOfDeniedRequests(dFrom, dTo);
		int totalWorkingCnt = db.countOfTotalWorkingDays(dFrom, dTo);

		ac.setActive(activeCnt);
		ac.setClosed(closedCnt);
		ac.setFrozen(freezeCnt);
		ac.setRejected(rejectedCnt);
		ac.setNumOfWorkDays(workingDaysCnt);
		
		ac.setTotalActive(totalActiveCnt);
		ac.setTotalClosed(totalClosedCnt);
		ac.setTotalFrozen(totalFreezeCnt);
		ac.setTotalRejected(totalRejectedCnt);
		ac.setTotalNumOfWorkDays(totalWorkingCnt);
		
		
		return ac;
		
	}
	
	


//	private void calcAndSendStatDelayReport(ConnectionToClient client, OperationType operationType,
//			TypeOfInformationSystem infoSys, Timestamp dayFromDelay, Timestamp dayToDelay) {
//
//		ArrayList<Integer> totDelayNum = new ArrayList<Integer>();
//		ArrayList<Integer> durDelayNum = new ArrayList<Integer>();
//
//		int diff = (int) (dayToDelay.toEpochDay() - dayFromDelay.toEpochDay());
//
//		if (diff % interval == 0) {
//			interval = diff / interval;
//		} else
//			interval = diff / interval + 1;
//
//		System.out.println("interval for delay :" + interval + "");
//
//		for (Timestamp i = dayFromDelay; !i.equals(dayToDelay);) {
//
//			Timestamp to = i.plusDays(interval - 1);
//
//			if (!to.after(dayToDelay)) {
//
//				totDelayNum.add( numOfTotDelayNum(infoSys, i, to));
//				durDelayNum.add( numOfDurDelayNum(infoSys, i, to));
//
//				i = to.plusDays(1);
//
//			} else {
//
//				totDelayNum.add( numOfTotDelayNum(infoSys, i, dayToDelay));
//				durDelayNum.add( numOfDurDelayNum(infoSys, i, dayToDelay));
//
//				i = dayToDelay;
//			}
//
//		}
//		System.out.println("tot delay num:" + totDelayNum);
//		System.out.println("dur delay num:" + durDelayNum);
//
//		ArrayList<Double> medians = new ArrayList<Double>();
//		ArrayList<Double> averages = new ArrayList<Double>();
//		ArrayList<Double> standarsdDeviation = new ArrayList<Double>();
//
//		medians.add(CalcMedian(totDelayNum));
//		medians.add(CalcMedian(durDelayNum));
//
//		System.out.println("median tot delay num:" + totDelayNum);
//		System.out.println("median dur delay num:" + durDelayNum);
//
//		averages.add(CalcAvg(totDelayNum));
//		averages.add(CalcAvg(durDelayNum));
//
//		System.out.println("avg tot delay num:" + totDelayNum);
//		System.out.println("avg dur delay num:" + durDelayNum);
//
//		standarsdDeviation.add(CalcStandardDeviation(totDelayNum, averages.get(0)));
//		standarsdDeviation.add(CalcStandardDeviation(durDelayNum, averages.get(1)));
//
//		System.out.println("standDev tot delay num:" + totDelayNum);
//		System.out.println("standDev dur delay num:" + durDelayNum);
//
//		int totDelayCnt =  numOfTotDelayNum(infoSys, dayFromDelay, dayToDelay);
//		int durDelayCnt =  numOfDurDelayNum(infoSys, dayFromDelay, dayToDelay);
//
//		interval = 10;
//
//		sendMessageBack(client, operationType, totDelayNum, durDelayNum, medians, averages, standarsdDeviation,
//				totDelayCnt, durDelayCnt);
//	}

}
