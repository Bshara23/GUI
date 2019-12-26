package Entities;

public class Employee extends SystemUser {

	
	public long empNumber;
	public String empDepartment, organizationalRole;
	
	
	

	private static Employee emptyInstance = new Employee(null, null, null, null, null, null, 0, null, null);
	public static Employee getEmptyInstance() {
		return emptyInstance;
	}



	public Employee(String userName, String password, String email, String firstName, String lastName, String phoneNo,
			long empNumber, String empDepartment, String organizationalRole) {
		super(userName, password, email, firstName, lastName, phoneNo);
		this.empNumber = empNumber;
		this.empDepartment = empDepartment;
		this.organizationalRole = organizationalRole;
	}



	
	public long getEmpNumber() {
		return empNumber;
	}




	public void setEmpNumber(long empNumber) {
		this.empNumber = empNumber;
	}




	public String getEmpDepartment() {
		return empDepartment;
	}




	public void setEmpDepartment(String empDepartment) {
		this.empDepartment = empDepartment;
	}




	public String getOrganizationalRole() {
		return organizationalRole;
	}




	public void setOrganizationalRole(String organizationalRole) {
		this.organizationalRole = organizationalRole;
	}




	@Override
	public int getPrimaryKeyIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getForeignKeyIndex() {
		// TODO Auto-generated method stub
		return 3;
	}


	@Override
	public String getReferenceTableName() {
		return "SystemUser";
	}

	@Override
	public boolean hasForeignKey() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getReferenceTableForeignKeyName() {
		// TODO Auto-generated method stub
		return "userName";
	}
	
	@Override
	public int fieldsLastIndex() {
		// TODO Auto-generated method stub
		return 3;
	}
}
