package shared.communication.toServer.game;

public class Commands {
	private String commandlist;
	public Commands(String c){
		commandlist=c;
	}
	public String getCommandlist() {
		return commandlist;
	}
	public void setCommandlist(String commandlist) {
		this.commandlist = commandlist;
	}
	
}
