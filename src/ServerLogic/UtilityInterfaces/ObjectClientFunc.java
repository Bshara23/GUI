package ServerLogic.UtilityInterfaces;

import ocsf.server.ConnectionToClient;

public interface ObjectClientFunc {
	public void call(Object obj, ConnectionToClient client);
}
