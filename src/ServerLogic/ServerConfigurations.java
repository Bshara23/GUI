package ServerLogic;

public class ServerConfigurations {

	public static void InjectEvents() {
		
		
		Server.addClientConnectedEvent(client -> {
			System.out.println("Client " + client.getInetAddress() + " connected");

		});
		
		
		Server.addClientDisconnectedEvent(client -> {
			System.out.println("Client " + client.getInetAddress() + " disconnected");

		});
		
		Server.addClientExceptionEvent((client, exception) -> {
			System.out.println("Client " + client.getInetAddress() + " exception with " + exception.toString());

		});
		
		
		Server.addServerStoppedEvent(() -> {
			System.out.println("Server stopped");
		});
		

	}
}
