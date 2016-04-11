package server.commands;


import client.communication.IServer;

/**
 * @post the player has a city on the board. The city count is decremented and the settlment count is incremented. The player paid 2 wheat and 3 ore
 * @author Doug
 *
 */
public class BuildCity extends Command 
{
    shared.communication.toServer.moves.BuildCity args;

    public BuildCity(int gameID) {
		super(gameID);
	}

    @Override
    public void execute(IServer server)
    {
        server.buildCityCommand(args,gameID);
    }

	public void setParams(shared.communication.toServer.moves.BuildCity move) 
	{
		args = move;
	}
}
