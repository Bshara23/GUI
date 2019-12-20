package Controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class test {

	public static void main(String[] args) {

		Calendar calendar = new GregorianCalendar(2013, 1, 28);
		

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH); // Jan = 0, dec = 11
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		

		System.out.println(dayOfMonth + ":" + month + ":" + year);
	}

}
