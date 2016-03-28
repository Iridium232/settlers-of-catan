package server.commands;

import client.communication.IServer;

/**
 * @post the player has a city on the board. The city count is decremented and the settlment count is incremented. The player paid 2 wheat and 3 ore
 * @author Doug
 *
 */
public class BuildCity extends Command {
    shared.communication.toServer.moves.BuildCity args;

    public BuildCity(IServer s, shared.communication.toServer.moves.BuildCity args) {
        super(s);
        this.args = args;
    }

    @Override
    public void execute() {
        server.buildCityCommand(args);
    }

}
