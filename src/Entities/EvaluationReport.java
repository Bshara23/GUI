package Entities;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

public class EvaluationReport extends Report implements Serializable{

	private static EvaluationReport emptyInstance = new EvaluationReport(0, 0, null, null, null, null, null, null);
	public static EvaluationReport getEmptyInstance() {
		return emptyInstance;
	}
	
	public String result, constraints, risks;
	public Timestamp estimatedExecutionTime;
	
	public EvaluationReport(long reportID, long requestID, String contentLT, String place, String result,
			String constraints, String risks, Timestamp estimatedExecutionTime) {
		super(reportID, requestID, contentLT, place);
		this.result = result;
		this.constraints = constraints;
		this.risks = risks;
		this.estimatedExecutionTime = estimatedExecutionTime;
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


	public Timestamp getEstimatedExecutionTime() {
		return estimatedExecutionTime;
	}


	public void setEstimatedExecutionTime(Timestamp estimatedExecutionTime) {
		this.estimatedExecutionTime = estimatedExecutionTime;
	}


	@Override
	public int getPrimaryKeyIndex() {
		// TODO Auto-generated method stub
		return 4;
	}


	@Override
	public int getForeignKeyIndex() {
		// TODO Auto-generated method stub
		return 5;
	}


	@Override
	public String getReferenceTableName() {
		// TODO Auto-generated method stub
		return "ChangeRequest";
	}


	@Override
	public boolean hasForeignKey() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public String getReferenceTableForeignKeyName() {
		// TODO Auto-generated method stub
		return "requestID";
	}


	@Override
	public int fieldsLastIndex() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	



	@Override
	public String toString() {
		return "EvaluationReport [result=" + result + ", constraints=" + constraints + ", risks=" + risks
				+ ", estimatedExecutionTime=" + estimatedExecutionTime + ", reportID=" + reportID + ", requestID="
				+ requestID + ", contentLT=" + contentLT + ", place=" + place + "]";
	}
	
	
}
