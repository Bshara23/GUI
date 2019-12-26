package CreationAndBasicTesting;

import java.lang.reflect.Field;

import Entities.Employee;
import Entities.Student;

public class Fieldssss {
	
	public static void main(String[] args) {

		for (Field f : Employee.class.getFields()) {
			System.out.println(f.getName());
		}
	}
}
