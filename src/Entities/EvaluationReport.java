package Entities;

import java.sql.Date;

public class EvaluationReport extends Report {

	public String place, result, constraints, risks;
	public Date estimatedExecutionTime;
	
	
	public EvaluationReport(long reportID, long requestID, String contentLT, String place, String result,
			String constraints, String risks, Date estimatedExecutionTime) {
		super(reportID, requestID, contentLT);
		this.place = place;
		this.result = result;
		this.constraints = constraints;
		this.risks = risks;
		this.estimatedExecutionTime = estimatedExecutionTime;
	}


	public String getPlace() {
		return place;
	}


	public void setPlace(String place) {
		this.place = place;
	}


	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}


	public String getConstraints() {
		return constraints;
	}


	public void setConstraints(String constraints) {
		this.constraints = constraints;
	}


	public String getRisks() {
		return risks;
	}


	public void setRisks(String risks) {
		this.risks = risks;
	}


	public Date getEstimatedExecutionTime() {
		return estimatedExecutionTime;
	}


	public void setEstimatedExecutionTime(Date estimatedExecutionTime) {
		this.estimatedExecutionTime = estimatedExecutionTime;
	}
	
	
	
	
}
