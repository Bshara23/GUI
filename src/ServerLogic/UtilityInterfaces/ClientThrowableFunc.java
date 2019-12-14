package ServerLogic.UtilityInterfaces;

import ocsf.server.ConnectionToClient;

public interface ClientThrowableFunc {
	public void call(ConnectionToClient client, Throwable exception);
}
