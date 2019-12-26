package Entities;

public class ExecutionChangesCommitteeMember extends Employee {
	
	public boolean isManager;

	public ExecutionChangesCommitteeMember(String userName, String password, String email, String firstName,
			String lastName, String phoneNo, long empNumber, String empDepartment, String organizationalRole,
			boolean isManager) {
		super(userName, password, email, firstName, lastName, phoneNo, empNumber, empDepartment, organizationalRole);
		this.isManager = isManager;
	}

	boolean isManager() {
		return isManager;
	}

	void setManager(boolean isManager) {
		this.isManager = isManager;
	}
	

	
	
	
	
}
