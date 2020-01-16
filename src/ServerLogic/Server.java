package ServerLogic;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import Entities.ChangeRequest;
import Entities.Employee;
import Entities.File;
import Entities.FileXML;
import Entities.Message;
import Entities.SqlObject;
import Entities.SystemUser;
import Entities.Phase;

import Protocol.Command;
import Protocol.MsgReturnType;
import Protocol.PhaseType;
import Protocol.SRMessage;
import ServerLogic.UtilityInterfaces.ClientFunc;
import ServerLogic.UtilityInterfaces.ClientThrowableFunc;
import ServerLogic.UtilityInterfaces.ObjectClientFunc;
import ServerLogic.UtilityInterfaces.ThrowableFunc;
import Utility.VoidFunc;
import javafx.application.Platform;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import Protocol.SeriObject;

public class Server extends AbstractServer {

	private boolean sqlException;
	public static final int DEFAULT_PORT = 5555;
	private static Server instance;
	private InetAddress inetAddress;
	private static ArrayList<ObjectClientFunc> objectRecievedFromClientsEvents;
	private static ArrayList<ClientFunc> clientConnectedEvents;
	private static ArrayList<ClientThrowableFunc> clientExceptionEvents;
	private static ArrayList<ClientFunc> clientDisconnectedEvents;
	private static ArrayList<ThrowableFunc> serverExceptionEvents;
	private static ArrayList<VoidFunc> serverClosedEvents;
	private static ArrayList<VoidFunc> serverStartedEvents;
	private static ArrayList<VoidFunc> serverStoppedEvents;
	private static MySQL db;
	// private static ExecutorService executorService;
	static {

		instance = new Server(5555);
		objectRecievedFromClientsEvents = new ArrayList<ObjectClientFunc>();
		clientConnectedEvents = new ArrayList<ClientFunc>();
		clientExceptionEvents = new ArrayList<ClientThrowableFunc>();
		clientDisconnectedEvents = new ArrayList<ClientFunc>();
		serverExceptionEvents = new ArrayList<ThrowableFunc>();
		serverClosedEvents = new ArrayList<VoidFunc>();
		serverStartedEvents = new ArrayList<VoidFunc>();
		serverStoppedEvents = new ArrayList<VoidFunc>();

		ServerConfigurations.InjectEvents();

	}

	public static Server getInstance() {
		return instance;
	}

	// TODO: is this valid or should just throw an exception?
	public MySQL getDB() {
		if (db == null)
			System.err.println("Database has not been initialized, please initialize!");
		return db;
	}

	private Server(int port) {
		super(port);
		try {
			inetAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {

			e.printStackTrace();
		}
	}

	@Override
	protected void finalize() throws Throwable {

//		executorService.shutdown();
//		try {
//			executorService.awaitTermination(10, TimeUnit.SECONDS);
//		} catch (InterruptedException e1) {
//		}
//		if (executorService.isTerminated())
//			System.out.println("All threads are done.");
//		else
//			System.out.println("Tired of waiting.");
		super.finalize();
	}

	// Initialize the client
	public void initialize(int port, String username, String password, String schemaName, int poolSize) {

		// executorService = Executors.newFixedThreadPool(poolSize);

		sqlException = false;
		instance.setPort(port);

		try {
			instance.listen();
		} catch (IOException e) {

			e.printStackTrace();
		}

		VoidFunc f = () -> {

			try {
				Server.getInstance().close();
				sqlException = true;
			} catch (IOException e) {

				e.printStackTrace();
			}
		};
		db = new MySQL(username, password, schemaName, f);

	}

	public boolean isSqlException() {
		return sqlException;
	}

	private Integer result;

	@SuppressWarnings("unchecked")
	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		SRMessage srMsg = (SRMessage) msg;
		System.out.println("Message Received from client: " + srMsg.getCommand().toString());
		// Execute in a separate thread

		Command command = srMsg.getCommand();
		switch (command) {

		case GetMyIssuedRequests:

			String forUsername = (String) srMsg.getAttachedData()[0];

			int startingRow = (int) srMsg.getAttachedData()[1];
			int size = (int) srMsg.getAttachedData()[2];

			ArrayList<ChangeRequest> crs = db.getChangeRequests(forUsername, startingRow, size);

			sendMessageToClient(client, command, crs);

			break;

		case GetMyIssuedRequestsCount:

			String countOfMyRequestsCondition = (String) srMsg.getAttachedData()[0];
			int countOfMyIssuedRequests = db.getCountOf(ChangeRequest.getEmptyInstance(), countOfMyRequestsCondition);
			sendMessageToClient(client, command, countOfMyIssuedRequests);

			break;

		case getPermissionsData:

			String username23 = (String) srMsg.getAttachedData()[0];
			int countOfPhasesForUsername = db.getCountOfPhaseForEmpByUsername(username23);
			boolean isManager = db.isUserManager(username23);

			boolean hasAtleastOnePhaseToManage = countOfPhasesForUsername > 0;

			sendMessageToClient(client, command, isManager, hasAtleastOnePhaseToManage);

			break;

		case getFullNameByUsername:

			String username45 = (String) srMsg.getAttachedData()[0];
			String fullName = db.getFullNameByUsername(username45);
			sendMessageToClient(client, command, fullName);

			break;
		case getEmployeeByEmployeeNumber:
			long empId = (long) srMsg.getAttachedData()[0];
			Employee emp = db.getEmployeeByEmpNumber(empId);
			sendMessageToClient(client, command, emp);
			break;

		case getSystemUserByRequest:

			long requestId2 = (long) srMsg.getAttachedData()[0];
			SystemUser sysUser = db.getSystemUserByRequestID(requestId2);
			sendMessageToClient(client, command, sysUser);

			break;

		case getPhasesOfRequestWithTimeExtensionsIfPossible:

			long requestIDforPhase = (long) srMsg.getAttachedData()[0];

			ArrayList<Phase> requestedPhases = db.getPhasesOfRequest(requestIDforPhase);

			sendMessageToClient(client, command, requestedPhases);

		case getCountOfPhasesTypes:

			long empNumberForPhases = (long) srMsg.getAttachedData()[0];

			int cntSupervision = db.isEmployeeIsSupervisor(empNumberForPhases) ? 1 : 0;
			int cntEvaluation = db.getCountOfPhasesByType(empNumberForPhases, PhaseType.Evaluation);
			int cntDecision = db.getCountOfPhasesByType(empNumberForPhases, PhaseType.Decision);
			int cntExecution = db.getCountOfPhasesByType(empNumberForPhases, PhaseType.Execution);
			int cntExamination = db.getCountOfPhasesByType(empNumberForPhases, PhaseType.Examination);

			sendMessageToClient(client, command, cntSupervision, cntEvaluation, cntDecision, cntExecution,
					cntExamination);

			break;

		case updateMessage:

			Message msgToUpdate = (Message) srMsg.getAttachedData()[0];
			db.updateMessage(msgToUpdate);

			break;

		case Update:

			SqlObject updateObj = (SqlObject) srMsg.getAttachedData()[0];
			db.updateByObject(updateObj);

			break;
		case deleteObjects:
			String MESSAGES_DELETED_LIST_OF_MESSAGES_RESPONSE = "messagesDeletedListOfMessagesResponse";

			ArrayList<SqlObject> objs = (ArrayList<SqlObject>) srMsg.getAttachedData()[0];

			int res = 1;
			for (SqlObject sqlObject : objs) {
				res *= db.deleteObject(sqlObject);
			}

			MsgReturnType retType = res == 1 ? MsgReturnType.Success : MsgReturnType.Failure;
			sendMessageToClient(client, command, MESSAGES_DELETED_LIST_OF_MESSAGES_RESPONSE, retType);

			break;
		case getMessagesPrimary:

			// TODO for now this is for all messages, not just primary
			String userName = (String) srMsg.getAttachedData()[0];
			int startingRowMessages = (int) srMsg.getAttachedData()[1];
			int sizeMessages = (int) srMsg.getAttachedData()[2];

			ArrayList<Message> msgs = db.getMessages(userName, startingRowMessages, sizeMessages);
			// TODO add failure and success cases

			sendMessageToClient(client, command, msgs);

			break;
		case countOfObjects:

			String cooCondition = (String) srMsg.getAttachedData()[0];
			SqlObject sqlObject = (SqlObject) srMsg.getAttachedData()[1];
			int countOfObject = db.getCountOf(sqlObject, cooCondition);
			sendMessageToClient(client, command, countOfObject);

			break;
		case insertFile:

			File fileToInsert = (File) srMsg.getAttachedData()[0];
			db.insertFile(fileToInsert);

			break;

		case getFile:

			int fileID = (int) srMsg.getAttachedData()[0];

			// TODO: this has to run in a different thread since it might get the server
			// stuck, other clients wont be able to receive messages
			// Make multiple threads or a thread queue?
			File downloadedFile = db.getFile(fileID);

			sendMessageToClient(client, command, downloadedFile);

			break;

		case debug_simulateBigCalculations:
			ArrayList<String> stra = (ArrayList<String>) srMsg.getAttachedData()[0];

			for (int i = 0; i < 500000; i++) {
				System.out.println(stra.toString());
			}

			break;

		case insertRequest:

			ChangeRequest changeRequest = (ChangeRequest) srMsg.getAttachedData()[0];
			// Set a new max id
			changeRequest.setRequestID(db.getNewMaxID(changeRequest));

			result = 1;
			result = db.insertObject(changeRequest); // TODO: make it return a boolean

			sendBooleanResultMessage(client, command, result);

			break;

		case insertRequestWithFiles:

			ChangeRequest changeRequestWithFiles = (ChangeRequest) srMsg.getAttachedData()[0];
			// Set a new max id
			changeRequestWithFiles.setRequestID(db.getNewMaxID(changeRequestWithFiles));

			ArrayList<File> files = (ArrayList<File>) srMsg.getAttachedData()[1];

			result = 1;
			result *= db.insertObject(changeRequestWithFiles); // TODO: make it return a boolean
			for (File file : files) {
				// Set the request id for the file
				file.setRequestID(changeRequestWithFiles.getRequestID());
				result *= db.insertFile(file);
			}

			sendBooleanResultMessage(client, command, result);

			break;

		case GetMyRequests:

			// TODO: fix in my requests list
			PhaseType phaseType = (PhaseType) srMsg.getAttachedData()[0];

			switch (phaseType) {
			case Supervision:

				ArrayList<ChangeRequest> requestsWithCurrentPhase = db.getChangeRequestWithCurrentPhase();

				sendMessageToClient(client, command, phaseType, requestsWithCurrentPhase);
				break;
			case Decision:
			case Evaluation:
			case Examination:
			case Execution:

				long empNum = (long) srMsg.getAttachedData()[1];

				ArrayList<ChangeRequest> crSupervision = db.getChangeRequestPhaseByEmployee(empNum, phaseType);

				sendMessageToClient(client, command, phaseType, crSupervision);

				break;

			default:
				System.err.println("Error, the RequestsType " + phaseType.toString() + " is not defined!");
				break;
			}

			break;
			
			/**
			 * @author EliaB
			 * get sum of length (estimatedTimeOfCompletion - deadline) where time has extended from two different dates
			 * */
		case GetSumOfTwoDiffernceDateBetweenTwoDates:
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Timestamp from = (Timestamp) srMsg.getAttachedData()[0];
		Timestamp to = (Timestamp) srMsg.getAttachedData()[1];
		
		System.out.println("data of analytic sent to client[Manager] date range :"+sdf.format(from).toString()+"-"+sdf.format(to).toString());
		
		ArrayList<HashMap<String,Integer>> data = new ArrayList<HashMap<String,Integer>>();
		
		int range=(int) ((TimeUnit.DAYS.convert(to.getTime() - from.getTime(), TimeUnit.MILLISECONDS))+1);
		for (int i = 0; i < range; i++) {
			
			data.add(db.GetSumOfTwoDiffernceDateBetweenTwoDates(from,to,range-i-1));
			if(range>10) {
				if(i+10>range)i+=range%10;
				else i+=10;
			}

		}
		System.out.println(data.toString());
		// System.out.println(AmountOfData);
		sendMessageToClient(client, command, data);
		
		
		break;
		/**
		 * @author EliaB
		 * Get phases counter by status from date range
		 *
		 * */
		case GetCounterOfPhasesByStatusDateRange:
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			Timestamp from1 = (Timestamp) srMsg.getAttachedData()[0];
			Timestamp to1= (Timestamp) srMsg.getAttachedData()[1];
			
			System.out.println("data of analytic sent to client[Manager] date range :"+sdf1.format(from1).toString()+"-"+sdf1.format(to1).toString());
			
			ArrayList<HashMap<String,Integer>> data1=(ArrayList<HashMap<String, Integer>>) GetDataByTwoDates(from1,to1);
			System.out.println(data1.toString());
			// System.out.println(AmountOfData);
			sendMessageToClient(client, command, data1);
			
			break;
		/**
		 * @Author EliaB Creating request "counter of request by status"
		 */

		case GetCounterOfPhasesByStatus:

			int index = (int) srMsg.getAttachedData()[0];

			System.out.println("data of analytic sent to client[Manager]");
			ArrayList<ArrayList<Integer>> p = new ArrayList<ArrayList<Integer>>();

			for (int i = 0; i < index; i++) {
				
				p.add(db.GetCounterOfPhaseByStatus(i));

			}

			// System.out.println(AmountOfData);
			sendMessageToClient(client, command, p);

			break;
		// End case
		/**
		 * 
		 * @author EliaB Logout user from server
		 */
		case LogOut:

			sendMessageToClient(client, command, true);

			break;
			/**
			 * @author EliaB
			 * Save the data of the report
			 * */
		case SaveTheData:
			
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			Timestamp from2 = (Timestamp) srMsg.getAttachedData()[0];
			Timestamp to2= (Timestamp) srMsg.getAttachedData()[1];
			ArrayList<HashMap<String,Integer>> results=(ArrayList<HashMap<String, Integer>>) GetDataByTwoDates(from2,to2);
			
			Timestamp today = new Timestamp(System.currentTimeMillis());
			db.SaveData(results, today);
		
			
		
			break;
		/**
		 * @author EliaB
		 * Get the data of saved report
		 * */
		case GetTheData:
			String reportName = (String) srMsg.getAttachedData()[0];
			ArrayList<HashMap<String,Integer>> results1=db.GetData(reportName);
			sendMessageToClient(client, command, results1);	
			break;
			/**
			 *@author EliaB
			 *get array of reports that's have been saved
			 * */
		case getNameOfReports:
			ArrayList<String> reports=new ArrayList<>();
			reports=db.getNameOfReports();
			System.out.println(reports.toString());
			sendMessageToClient(client, command, reports);
			break;
		default:
			System.err.println("Error, undefine command [" + srMsg.getCommand() + "]");
			break;
		}

		for (ObjectClientFunc f : objectRecievedFromClientsEvents) {
			if (f != null)
				f.call(msg, client);
		}

	}



	private void sendMessageToClient(ConnectionToClient client, Command cmd, Object... objs) {
		try {
			client.sendToClient(new SRMessage(cmd, objs));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void sendBooleanResultMessage(ConnectionToClient client, Command cmd, int result) {

		if (result == 1) {
			sendResultMessageToClient(client, Command.insertRequestWithFiles, MsgReturnType.Success);
		} else {
			sendResultMessageToClient(client, Command.insertRequestWithFiles, MsgReturnType.Failure);
		}
	}

	private void sendResultMessageToClient(ConnectionToClient client, Command cmd, MsgReturnType returnType) {
		sendResultMessageToClient(client, cmd, returnType, null);
	}

	private void sendResultMessageToClient(ConnectionToClient client, Command cmd, MsgReturnType returnType,
			Object... objs) {
		try {
			SRMessage msg = new SRMessage(cmd, objs);
			msg.setReturnType(returnType);
			client.sendToClient(msg);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void clientConnected(ConnectionToClient client) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				for (ClientFunc f : clientConnectedEvents) {
					if (f != null)
						f.call(client);
				}

			}
		});

	}

	@Override
	protected synchronized void clientException(ConnectionToClient client, Throwable exception) {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				for (ClientThrowableFunc f : clientExceptionEvents) {
					if (f != null)
						f.call(client, exception);
				}

			}
		});
	}

	@Override
	protected synchronized void clientDisconnected(ConnectionToClient client) {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {

				for (ClientFunc f : clientDisconnectedEvents) {
					if (f != null)
						f.call(client);
				}

			}
		});
	}

	@Override
	protected void listeningException(Throwable exception) {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				for (ThrowableFunc f : serverExceptionEvents) {
					if (f != null)
						f.call(exception);
				}

			}
		});
	}

	@Override
	protected void serverClosed() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				for (VoidFunc f : serverClosedEvents) {
					if (f != null)
						f.call();
				}

			}
		});
	}

	@Override
	protected void serverStarted() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				for (VoidFunc f : serverStartedEvents) {
					if (f != null)
						f.call();
				}

			}
		});
	}

	@Override
	protected void serverStopped() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				for (VoidFunc f : serverStoppedEvents) {
					if (f != null)
						f.call();
				}

			}
		});
	}

	@Override
	public void sendToAllClients(Object msg) {
		// TODO Auto-generated method stub
		super.sendToAllClients(msg);
	}

	public static void addObjectRecievedFromClientsEvent(ObjectClientFunc ocf) {
		objectRecievedFromClientsEvents.add(ocf);
	}

	public static void addClientConnectedEvent(ClientFunc cf) {
		clientConnectedEvents.add(cf);
	}

	public static void addClientExceptionEvent(ClientThrowableFunc ctf) {
		clientExceptionEvents.add(ctf);
	}

	public static void addClientDisconnectedEvent(ClientFunc cf) {
		clientDisconnectedEvents.add(cf);
	}

	public static void addServerExceptionEvent(ThrowableFunc tf) {
		serverExceptionEvents.add(tf);
	}

	public static void addServerClosedEvent(VoidFunc tf) {
		serverClosedEvents.add(tf);
	}

	public static void addServerStartedEvent(VoidFunc tf) {
		serverStartedEvents.add(tf);
	}

	public static void addServerStoppedEvent(VoidFunc tf) {
		serverStoppedEvents.add(tf);
	}

	public String getHostAddress() {
		return inetAddress.getHostAddress();
	}

	public String getHostName() {
		return inetAddress.getHostName();
	}
	
	public Object GetDataByTwoDates(Timestamp from,Timestamp to) {
		ArrayList<HashMap<String,Integer>> data = new ArrayList<HashMap<String,Integer>>();
		
		int range1=(int) ((TimeUnit.DAYS.convert(to.getTime() - from.getTime(), TimeUnit.MILLISECONDS))+1);
		for (int i = 0; i < range1; i++) {
			
			data.add(db.GetCounterOfPhasesBetweenTwoDates(from,to,range1-i-1));

		}
		return data;
	}
}
