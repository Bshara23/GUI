package ClientLogic;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import Protocol.Command;
import Protocol.SRMessage;
import Utility.VoidFunc;
import Utility.StringFunc;
import Utility.SRMessageFunc;
import javafx.application.Platform;
import ocsf.client.*;

public class Client extends AbstractClient {

	private static Client instance;
	private static HashMap<String, VoidFunc> serverExceptionEvents;
	private static HashMap<String, VoidFunc> serverConnectionClosedEvents;
	private static HashMap<String, VoidFunc> serverConnectionEstablishedEvents;

	private static HashMap<String, SRMessageFunc> messageRecievedFromServerEvents;
	private static HashMap<String, StringFunc> stringRecievedFromServerEvents;
	private static ExecutorService executorService;
	private int poolSize = 5;

	static {
		//instance = new Client("10.0.0.212", 5555);
		instance = new Client("localhost", 5555);

		serverExceptionEvents = new HashMap<String, VoidFunc>();
		serverConnectionClosedEvents = new HashMap<String, VoidFunc>();
		serverConnectionEstablishedEvents = new HashMap<String, VoidFunc>();
		messageRecievedFromServerEvents = new HashMap<String, SRMessageFunc>();
		stringRecievedFromServerEvents = new HashMap<String, StringFunc>();
	}

	public static Client getInstance() {

		return instance;
	}

	@Override
	protected void finalize() throws Throwable {

		executorService.shutdown();
		try {
			executorService.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException e1) {
		}
		if (executorService.isTerminated())
			System.out.println("All threads are done.");
		else
			System.out.println("Tired of waiting.");
		super.finalize();
	}

	// Initialize the client
	public void initialize(String host, int port) {

		executorService = Executors.newFixedThreadPool(poolSize);

		instance.setHost(host);
		instance.setPort(port);
		try {
			instance.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Client(String host, int port) {
		super(host, port);
	}

	public void request(Command cmd, Object... objs) {

		executorService.execute(() -> {
			try {
				SRMessage srMsg = new SRMessage(cmd, objs);
				instance.sendToServer(srMsg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
	public void request(Command cmd) {

		request(cmd, new Object[1]);
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		// TODO: might be a slow way to change the UI, try using AnimationTimeline
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				SRMessage srMsg = (SRMessage) msg;
				
				System.out.println("Message Received from server: " + srMsg.getCommand().toString());

				
				for (SRMessageFunc f : messageRecievedFromServerEvents.values()) {
					if (f != null)
						f.call(srMsg);
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
		serverConnectionEstablishedEvents.remove(key);
		serverConnectionEstablishedEvents.put(key, voidFunc);
	}

	public static void addServerConnectionClosedEvent(String key, VoidFunc voidFunc) {
		serverConnectionClosedEvents.remove(key);
		serverConnectionClosedEvents.put(key, voidFunc);
	}

	public static void addServerExceptionEvent(String key, VoidFunc voidFunc) {
		serverExceptionEvents.remove(key);
		serverExceptionEvents.put(key, voidFunc);
	}

	public static void addStringRecievedFromServer(String key, StringFunc stringFunc) {
		stringRecievedFromServerEvents.remove(key);
		stringRecievedFromServerEvents.put(key, stringFunc);
	}

	public static void addMessageRecievedFromServer(String key, SRMessageFunc sRMessageFunc) {
		messageRecievedFromServerEvents.remove(key);
		messageRecievedFromServerEvents.put(key, sRMessageFunc);
	}
	
	


	public static void removeServerConnectionEstablishedEvent(String key) {
		serverConnectionEstablishedEvents.remove(key);
	}

	public static void removeServerConnectionClosedEvent(String key) {
		serverConnectionClosedEvents.remove(key);
	}

	public static void removeServerExceptionEvent(String key) {
		serverExceptionEvents.remove(key);
	}

	public static void removeStringRecievedFromServer(String key) {
		stringRecievedFromServerEvents.remove(key);
	}

	public static void removeMessageRecievedFromServer(String key) {
		messageRecievedFromServerEvents.remove(key);
	}
}
