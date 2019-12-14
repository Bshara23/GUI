package Protocol;

import java.io.Serializable;

public class ServerClientMessage implements Serializable {

	private Command command;
	private Object data;
	
	public ServerClientMessage(Command command, Object data) {
		this.command = command;
		this.data = data;
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
