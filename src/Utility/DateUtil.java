package Utility;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class DateUtil {

	public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	public static final Timestamp NA = Timestamp.valueOf(LocalDateTime.of(1999, 1, 1, 1, 0));
	// public static final Timestamp ONE_DAY = Timestamp.;

	static {
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Jerusalem"));
	}

	public static Timestamp now() {
		return Timestamp.valueOf(LocalDateTime.now());
	}

	public static String toString(Timestamp ts) {
		return sdf.format(ts);
	}

	/**
	 * Returns the difference between a and b, meaning a - b.
	 */
	public static String difference(Timestamp a, Timestamp b) {
		Timestamp diff = new Timestamp(a.getTime() - b.getTime());
		return sdf.format(diff);
	}

	public static Timestamp add(Timestamp a, int days, int hours) {

		LocalDateTime date = a.toLocalDateTime();
		date = date.plusDays(days);
		date = date.plusHours(hours);

		return Timestamp.valueOf(date);
	}
	
	public static Timestamp add(Timestamp a, int days, int hours, int minutes) {
		LocalDateTime date = a.toLocalDateTime();
		date = date.plusDays(days);
		date = date.plusHours(hours);
		date = date.plusMinutes(minutes);

		return Timestamp.valueOf(date);
	}

	public static void main(String[] args) {

		// System.out.println(toString(NA));
		System.out.println(now());

		System.out.println(add(now(), 1, 3));

	}

	

}
