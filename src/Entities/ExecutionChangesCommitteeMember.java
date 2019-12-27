package Entities;

public class ExecutionChangesCommitteeMember extends Employee {
	
	
	private static ExecutionChangesCommitteeMember emptyInstance = new ExecutionChangesCommitteeMember(null, null, null, null, null, null, 0, null, null, false);
	public static Employee getEmptyInstance() {
		return emptyInstance;
	}
	public boolean isManager;



	

	public ExecutionChangesCommitteeMember(String userName, String password, String email, String firstName,
			String lastName, String phoneNo, long empNumber, String empDepartment, String organizationalRole,
			boolean isManager) {
		super(userName, password, email, firstName, lastName, phoneNo, empNumber, empDepartment, organizationalRole);
		this.isManager = isManager;
	}
	
	

	public boolean getIsManager() {
		return isManager;
	}



	public void setIsManager(boolean isManager) {
		this.isManager = isManager;
	}



	@Override
	public int getPrimaryKeyIndex() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getForeignKeyIndex() {
		// TODO Auto-generated method stub
		return 1;
	}


	@Override
	public String getReferenceTableName() {
		return "Employee";
	}

	@Override
	public boolean hasForeignKey() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getReferenceTableForeignKeyName() {
		// TODO Auto-generated method stub
		return "empNumber";
	}
	
	@Override
	public int fieldsLastIndex() {
		// TODO Auto-generated method stub
		return 1;
	}
	
	@Override
	public boolean isPrimaryKeyIncremental() {
		return false;
	}
	
}
