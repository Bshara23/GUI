package ServerLogic.UtilityInterfaces;

import ocsf.server.ConnectionToClient;

public interface StringClientFunc {
	public void call(String str, ConnectionToClient client);
}
