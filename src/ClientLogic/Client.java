package ClientLogic;

import java.util.HashMap;

import Utility.VoidFunc;
import Utility.StringFunc;
import Utility.ObjectFunc;
import javafx.application.Platform;
import ocsf.client.*;

public class Client extends AbstractClient {

	private static Client instance;
	private static HashMap<String, VoidFunc> serverExceptionEvents;
	private static HashMap<String, VoidFunc> serverConnectionClosedEvents;
	private static HashMap<String, VoidFunc> serverConnectionEstablishedEvents;

	private static HashMap<String, ObjectFunc> objectRecievedFromServerEvents;
	private static HashMap<String, StringFunc> stringRecievedFromServerEvents;

	static {
		instance = new Client("10.0.0.127", 5555);
		serverExceptionEvents = new HashMap<String, VoidFunc>();
		serverConnectionClosedEvents = new HashMap<String, VoidFunc>();
		serverConnectionEstablishedEvents = new HashMap<String, VoidFunc>();
		objectRecievedFromServerEvents = new HashMap<String, ObjectFunc>();
		stringRecievedFromServerEvents = new HashMap<String, StringFunc>();
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
				for (ObjectFunc f : objectRecievedFromServerEvents.values()) {
					if (f != null)
						f.call(msg);
				}

				if (msg instanceof String) {
					String str = (String) msg;
					for (StringFunc f : stringRecievedFromServerEvents.values()) {
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
				for (VoidFunc voidFunc : serverExceptionEvents.values()) {
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
				for (VoidFunc voidFunc : serverConnectionClosedEvents.values()) {
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
				for (VoidFunc voidFunc : serverConnectionEstablishedEvents.values()) {
					if (voidFunc != null)
						voidFunc.call();
				}
			}
		});
	}

	public static void addServerConnectionEstablishedEvent(String key, VoidFunc voidFunc) {
		serverConnectionEstablishedEvents.put(key, voidFunc);
	}

	public static void addServerConnectionClosedEvent(String key, VoidFunc voidFunc) {
		serverConnectionClosedEvents.put(key, voidFunc);
	}

	public static void addServerExceptionEvent(String key, VoidFunc voidFunc) {
		serverExceptionEvents.put(key, voidFunc);
	}

	public void addStringRecievedFromServer(String key, StringFunc stringFunc) {
		stringRecievedFromServerEvents.put(key, stringFunc);
	}

	public void addObjectRecievedFromServer(String key, ObjectFunc objectFunc) {
		objectRecievedFromServerEvents.put(key, objectFunc);
	}
}
