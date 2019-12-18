package CreationAndBasicTesting;

import java.lang.reflect.Method;
import java.sql.Blob;

import ClientLogic.Client;
import Entities.Message;
import Entities.SystemUser;
import ServerLogic.MySQL;

public class MySqlTableCreator {

	public static void main(String[] args) {

		MySQL db = new MySQL("root", "Aa123456", "ICM", null);
		db.createTable(new SystemUser("", "", "", "", "", ""));
		db.createTable(new Message(1, "", "", "", true));

		
	}

}
