package ServerLogic.UtilityInterfaces;

import ocsf.server.ConnectionToClient;

public interface ClientFunc {
	public void call(ConnectionToClient client);
}
