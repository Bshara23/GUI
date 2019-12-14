package Entities;

public class Student extends SystemUser {

	public long stuNumber;
	public String stuDepartment;
	
	public Student(String userName, String password, String email, String firstName, String lastName, String phoneNo,
			long stuNumber, String stuDepartment) {
		super(userName, password, email, firstName, lastName, phoneNo);
		this.stuNumber = stuNumber;
		this.stuDepartment = stuDepartment;
	}

	public long getStuNumber() {
		return stuNumber;
	}

	public void setStuNumber(long stuNumber) {
		this.stuNumber = stuNumber;
	}

	public String getStuDepartment() {
		return stuDepartment;
	}

	public void setStuDepartment(String stuDepartment) {
		this.stuDepartment = stuDepartment;
	}
	
	
	

}
