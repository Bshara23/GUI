package CreationAndBasicTesting;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import Entities.*;
import ServerLogic.MySQL;

public class ddwadwadwa {

	private static MySQL db = new MySQL("root", "Aa123456", "ICM", null);

	public static void main(String[] args) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Jerusalem"));

		Message msg = db.getMessage(81011);

		System.out.println(msg.getMessageID());
		System.out.println(sdf.format(msg.getSentAt()));
		msg.setSentAt(Timestamp.valueOf(LocalDateTime.now()));

		db.updateMessage(msg);
		msg = db.getMessage(81011);

		System.out.println(msg.getMessageID());
		System.out.println(sdf.format(msg.getSentAt()));

//		
//
//
//		db.updateMessage(msg);

	}
}
