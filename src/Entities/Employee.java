package Entities;

public class Employee extends SystemUser {

	
	public long empNumber;
	public String empDepartment, organizationalRole;
	
	
	

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
	
	
	
}
