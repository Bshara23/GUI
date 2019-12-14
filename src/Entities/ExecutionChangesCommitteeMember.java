package Entities;

public class ExecutionChangesCommitteeMember extends Employee {
	
	public long empNumber;
	public String position;
	
	public ExecutionChangesCommitteeMember(String userName, String password, String email, String firstName,
			String lastName, String phoneNo, long empNumber, String empDepartment, String organizationalRole,
			long empNumber2, String position) {
		super(userName, password, email, firstName, lastName, phoneNo, empNumber, empDepartment, organizationalRole);
		empNumber = empNumber2;
		this.position = position;
	}

	public long getEmpNumber() {
		return empNumber;
	}

	public void setEmpNumber(long empNumber) {
		this.empNumber = empNumber;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	
	
}
