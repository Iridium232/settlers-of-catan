package server.commands;

import shared.communication.toServer.moves.Year_of_Plenty_;
import client.communication.IServer;

/**
 * An object to save and execute a when a year of plenty card is played
 * @post THe user obtained the selected two new resource cards. 
 * @author Doug
 *
 */
public class Year_of_Plenty extends Command {

	private Year_of_Plenty_ params;
	
	public Year_of_Plenty(IServer s,int gameID) {
		super(s, gameID);
	}

	@Override
	public void execute()
	{
		this.server.playYearOfPlentyCardCommand(params);
	}

	public void setParams(Year_of_Plenty_ move) 
	{
		this.params = move;
	}
}
