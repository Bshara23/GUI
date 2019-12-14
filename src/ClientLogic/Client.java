package ClientLogic;

import java.util.ArrayList;

import Utility.VoidFunc;
import Utility.StringFunc;
import Utility.ObjectFunc;
import javafx.application.Platform;
import ocsf.client.*;

public class Client extends AbstractClient {

	private static Client instance;
	private static ArrayList<VoidFunc> serverExceptionEvents;
	private static ArrayList<VoidFunc> serverConnectionClosedEvents;
	private static ArrayList<VoidFunc> serverConnectionEstablishedEvents;

	private static ArrayList<ObjectFunc> objectRecievedFromServerEvents;
	private static ArrayList<StringFunc> stringRecievedFromServerEvents;


	static {
		instance = new Client("10.0.0.127", 5555);
		serverExceptionEvents = new ArrayList<VoidFunc>();
		serverConnectionClosedEvents = new ArrayList<VoidFunc>();
		serverConnectionEstablishedEvents = new ArrayList<VoidFunc>();
		objectRecievedFromServerEvents = new ArrayList<ObjectFunc>();
		stringRecievedFromServerEvents = new ArrayList<StringFunc>();
	}

	public static Client getInstance() {

		return instance;
	}
	
	

	// Initialize the client
	public void initialize(String host, int port) {
		instance.setHost(host);
		instance.setPort(port);
	}

	private Client(String host, int port) {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		// TODO: might be a slow way to change the UI, try using AnimationTimeline
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				for (ObjectFunc f : objectRecievedFromServerEvents) {
					if (f != null)
						f.call(msg);
				}
				
				if (msg instanceof String) {
					String str = (String) msg;
					for (StringFunc f : stringRecievedFromServerEvents) {
						if (f != null)
							f.call(str);
					}
				}
			}
		});

	}

	@Override
	protected void connectionException(Exception exception) {
		// TODO Auto-generated method stub
		super.connectionException(exception);

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				for (VoidFunc voidFunc : serverExceptionEvents) {
					if (voidFunc != null)
						voidFunc.call();
				}
			}
		});
	}

	
	@Override
	protected void connectionClosed() {
		
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				for (VoidFunc voidFunc : serverConnectionClosedEvents) {
					if (voidFunc != null)
						voidFunc.call();
				}
			}
		});
		
	}
	
	@Override
	protected void connectionEstablished() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				for (VoidFunc voidFunc : serverConnectionEstablishedEvents) {
					if (voidFunc != null)
						voidFunc.call();
				}
			}
		});
	}
	
	
	
	
	public static void addServerConnectionEstablishedEvent(VoidFunc voidFunc) {
		serverConnectionEstablishedEvents.add(voidFunc);
	}
	
	public static void addServerConnectionClosedEvent(VoidFunc voidFunc) {
		serverConnectionClosedEvents.add(voidFunc);
	}
	
	public static void addServerExceptionEvent(VoidFunc voidFunc) {
		serverExceptionEvents.add(voidFunc);
	}

	public void addStringRecievedFromServer(StringFunc stringFunc) {
		stringRecievedFromServerEvents.add(stringFunc);
	}
	
	public void addObjectRecievedFromServer(ObjectFunc objectFunc) {
		objectRecievedFromServerEvents.add(objectFunc);
	}
}
