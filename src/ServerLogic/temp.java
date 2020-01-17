package ServerLogic;

import java.time.LocalDate;
import java.util.ArrayList;

public class temp {
	private boolean isLeftOuter(LocalDate limA, LocalDate b) {
		return b.isBefore(limA);
	}

	private boolean isRightOuter(LocalDate limB, LocalDate a) {
		return a.isAfter(limB);
	}

	private boolean isOnLimitA(LocalDate limA, LocalDate limB, LocalDate a, LocalDate b) {
		return a.isBefore(limA) && b.isAfter(limA) && b.isBefore(limB);
	}

	private boolean isOnLimitAEql(LocalDate limA, LocalDate limB, LocalDate a, LocalDate b) {
		return a.isBefore(limA) && b.isAfter(limA) && b.isBefore(limB);
	}

	private boolean isOnLimitB(LocalDate limA, LocalDate limB, LocalDate a, LocalDate b) {
		return a.isAfter(limA) && b.isAfter(limB) && a.isBefore(limB);
	}

	private boolean isBetweenLimits(LocalDate limA, LocalDate limB, LocalDate a, LocalDate b) {
		return a.isAfter(limA) && b.isBefore(limB);
	}

	private boolean isContainingRange(LocalDate limA, LocalDate limB, LocalDate a, LocalDate b) {
		return a.isBefore(limA) && b.isAfter(limB);
	}

	private boolean isEqualRange(LocalDate limA, LocalDate limB, LocalDate a, LocalDate b) {
		return a.equals(limA) && b.equals(limB);
	}

	private int diff(LocalDate a, LocalDate b) {

		return (int) (b.toEpochDay() - a.toEpochDay());
	}

	private int getNumOfDays(LocalDate limA, LocalDate limB, LocalDate a, LocalDate b) {

		b = b.equals(NA) ? limB : b;

		if (a.equals(limB) || b.equals(limA))
			return 1;

		if (isRightOuter(limB, a) || isLeftOuter(limA, b))
			return 0;

		if (isOnLimitA(limA, limB, a, b) || a.equals(limA))
			return diff(limA, b);

		if (isOnLimitB(limA, limB, a, b) || b.equals(limB))
			return diff(a, limB);

		if (isBetweenLimits(limA, limB, a, b))
			return diff(a, b);

		if (isContainingRange(limA, limB, a, b))
			return diff(limA, limB);

		if (isEqualRange(limA, limB, a, b))
			return diff(a, b);

		return 0;

	}
	
	
	private void calcAndSendStatActivReport(ConnectionToClient client, OperationType operationType, LocalDate dayFrom,
			LocalDate dayTo) {
		ArrayList<Integer> activeNum = new ArrayList<Integer>();
		ArrayList<Integer> freezeNum = new ArrayList<Integer>();
		ArrayList<Integer> closedNum = new ArrayList<Integer>();
		ArrayList<Integer> deniedNum = new ArrayList<Integer>();
		ArrayList<Integer> totalWorkingNum = new ArrayList<Integer>();

		int diff = (int) (dayTo.toEpochDay() - dayFrom.toEpochDay());

		if (diff % interval == 0) {
			interval = diff / interval;
		} else
			interval = diff / interval + 1;

		System.out.println("activity interval's days : " + interval + "");

		for (LocalDate i = dayFrom; !i.equals(dayTo);) {

			LocalDate to = i.plusDays(interval - 1);

			if (!to.isAfter(dayTo)) {

				activeNum.add(DBController.getInstance().numOfActiveReq(i, to));
				freezeNum.add(DBController.getInstance().numOfFreezeReq(i, to));
				closedNum.add(DBController.getInstance().numOfClosedReq(i, to));
				deniedNum.add(DBController.getInstance().numOfDeniedReq(i, to));
				totalWorkingNum.add(DBController.getInstance().numOfTotalWorking(i, to));

				i = to.plusDays(1);

			} else {

				activeNum.add(DBController.getInstance().numOfActiveReq(i, dayTo));
				freezeNum.add(DBController.getInstance().numOfFreezeReq(i, dayTo));
				closedNum.add(DBController.getInstance().numOfClosedReq(i, dayTo));
				deniedNum.add(DBController.getInstance().numOfDeniedReq(i, dayTo));
				totalWorkingNum.add(DBController.getInstance().numOfTotalWorking(i, dayTo));

				i = dayTo;
			}

		}

		ArrayList<Double> medians = new ArrayList<Double>();
		ArrayList<Double> averages = new ArrayList<Double>();
		ArrayList<Double> standarsdDeviation = new ArrayList<Double>();

		medians.add(CalcMedian(activeNum));

		medians.add(CalcMedian(freezeNum));

		medians.add(CalcMedian(closedNum));

		medians.add(CalcMedian(deniedNum));

		medians.add(CalcMedian(totalWorkingNum));

		averages.add(CalcAvg(activeNum));
		averages.add(CalcAvg(freezeNum));
		averages.add(CalcAvg(closedNum));
		averages.add(CalcAvg(deniedNum));
		averages.add(CalcAvg(totalWorkingNum));

		standarsdDeviation.add(CalcStandardDeviation(activeNum, averages.get(0)));
		standarsdDeviation.add(CalcStandardDeviation(freezeNum, averages.get(1)));
		standarsdDeviation.add(CalcStandardDeviation(closedNum, averages.get(2)));
		standarsdDeviation.add(CalcStandardDeviation(deniedNum, averages.get(3)));
		standarsdDeviation.add(CalcStandardDeviation(totalWorkingNum, averages.get(4)));

		int activeCnt = DBController.getInstance().numOfActiveReq(dayFrom, dayTo);
		int freezeCnt = DBController.getInstance().numOfFreezeReq(dayFrom, dayTo);
		int closedCnt = DBController.getInstance().numOfClosedReq(dayFrom, dayTo);
		int deniedCnt = DBController.getInstance().numOfDeniedReq(dayFrom, dayTo);
		int totalWorkingCnt = DBController.getInstance().numOfTotalWorking(dayFrom, dayTo);

		interval = 10;

		sendMessageBack(client, operationType, activeNum, freezeNum, closedNum, deniedNum, totalWorkingNum, medians,
				averages, standarsdDeviation, activeCnt, freezeCnt, closedCnt, deniedCnt, totalWorkingCnt);

	}

	private void calcAndSendStatDelayReport(ConnectionToClient client, OperationType operationType,
			TypeOfInformationSystem infoSys, LocalDate dayFromDelay, LocalDate dayToDelay) {

		ArrayList<Integer> totDelayNum = new ArrayList<Integer>();
		ArrayList<Integer> durDelayNum = new ArrayList<Integer>();

		int diff = (int) (dayToDelay.toEpochDay() - dayFromDelay.toEpochDay());

		if (diff % interval == 0) {
			interval = diff / interval;
		} else
			interval = diff / interval + 1;

		System.out.println("interval for delay :" + interval + "");

		for (LocalDate i = dayFromDelay; !i.equals(dayToDelay);) {

			LocalDate to = i.plusDays(interval - 1);

			if (!to.isAfter(dayToDelay)) {

				totDelayNum.add(DBController.getInstance().numOfTotDelayNum(infoSys, i, to));
				durDelayNum.add(DBController.getInstance().numOfDurDelayNum(infoSys, i, to));

				i = to.plusDays(1);

			} else {

				totDelayNum.add(DBController.getInstance().numOfTotDelayNum(infoSys, i, dayToDelay));
				durDelayNum.add(DBController.getInstance().numOfDurDelayNum(infoSys, i, dayToDelay));

				i = dayToDelay;
			}

		}
		System.out.println("tot delay num:" + totDelayNum);
		System.out.println("dur delay num:" + durDelayNum);

		ArrayList<Double> medians = new ArrayList<Double>();
		ArrayList<Double> averages = new ArrayList<Double>();
		ArrayList<Double> standarsdDeviation = new ArrayList<Double>();

		medians.add(CalcMedian(totDelayNum));
		medians.add(CalcMedian(durDelayNum));

		System.out.println("median tot delay num:" + totDelayNum);
		System.out.println("median dur delay num:" + durDelayNum);

		averages.add(CalcAvg(totDelayNum));
		averages.add(CalcAvg(durDelayNum));

		System.out.println("avg tot delay num:" + totDelayNum);
		System.out.println("avg dur delay num:" + durDelayNum);

		standarsdDeviation.add(CalcStandardDeviation(totDelayNum, averages.get(0)));
		standarsdDeviation.add(CalcStandardDeviation(durDelayNum, averages.get(1)));

		System.out.println("standDev tot delay num:" + totDelayNum);
		System.out.println("standDev dur delay num:" + durDelayNum);

		int totDelayCnt = DBController.getInstance().numOfTotDelayNum(infoSys, dayFromDelay, dayToDelay);
		int durDelayCnt = DBController.getInstance().numOfDurDelayNum(infoSys, dayFromDelay, dayToDelay);

		interval = 10;

		sendMessageBack(client, operationType, totDelayNum, durDelayNum, medians, averages, standarsdDeviation,
				totDelayCnt, durDelayCnt);
	}

	private double CalcMedian(ArrayList<Integer> arrList) {
		ArrayList<Integer> arr = (ArrayList<Integer>) arrList.clone();
		Collections.sort(arr);
		int median;
		int a, b;
		if (arrList.size() == 0) {
			return 0;
		}
		if (arrList.size() % 2 == 0) {
			median = arrList.size() / 2;
		} else {
			median = (arrList.size() + 1) / 2;
		}
		a = arrList.get(median);
		b = arrList.get(median + 1);
		return (a + b) / 2.0;
	}

	private double CalcAvg(ArrayList<Integer> arrList) {
		Integer sum = 0;

		if (arrList.size() == 0) {
			return 0;
		}

		for (Integer elem : arrList) {
			sum += elem;
		}
		return (double) sum / arrList.size();
	}

	private double CalcStandardDeviation(ArrayList<Integer> arrList, double avg) {
		double sum = 0;
		if (arrList.size() == 0)
			return 0;
		for (Integer elem : arrList) {
			sum += Math.pow((double) elem - avg, 2);
		}
		return Math.sqrt(sum / arrList.size());
	}

}
