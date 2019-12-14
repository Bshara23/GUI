package ServerLogic;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import ServerLogic.UtilityInterfaces.ClientFunc;
import ServerLogic.UtilityInterfaces.ClientThrowableFunc;
import ServerLogic.UtilityInterfaces.ObjectClientFunc;
import ServerLogic.UtilityInterfaces.ThrowableFunc;
import Utility.Func;
import Utility.VoidFunc;
import javafx.application.Platform;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

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
	public MySQL getDB(){
		if(db == null)
			System.err.println("Database has not been initialized, please initialize!");
		return db;
	}
	
	private Server(int port) {
		super(port);
		try {
			inetAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Initialize the client
	public void initialize(int port, String username, String password, String schemaName) {
		sqlException = false;
		instance.setPort(port);
		
		try {
			instance.listen();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		VoidFunc f = () -> {

			try {
				Server.getInstance().close();
				sqlException = true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
		db = new MySQL(username, password, schemaName, f);
		
	}
	
	public boolean isSqlException() {
		return sqlException;
	}
	
	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {

		for (ObjectClientFunc f : objectRecievedFromClientsEvents) {
			if (f != null)
				f.call(msg, client);
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


}
