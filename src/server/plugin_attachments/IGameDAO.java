package server.plugin_attachments;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import server.commands.Command;
import shared.model.Fascade;

public interface IGameDAO 
{
	/**
	 * Gets a map of Game models referenced by game_id
	 * @pre none
	 * @post gets a map of games that is stored in the database
	 * @return
	 */
	public Map<Integer,Fascade> getGames();
	
	/**
	 * @pre none
	 * @post returns a map of command lists by game_id
	 * @return
	 */
	public Map<Integer,List<Command>> getCommands();
	
	/**
	 * Adds a command to that game in the database
	 * 
	 * @pre none
	 * @post saves a command to the command list for the game specified
	 * @param command
	 * @param game_id
	 */
	public void saveCommand(Command command, int game_id);
	
	/**
	 * Saves the model and empties the commands in the database
	 * @pre none
	 * @post the model is written to the database and the command list is emptied
	 * 
	 * @param model
	 * @param game_id
	 */
	public void saveModelAndEmptyCommands(
			shared.communication.fromServer.game.CommunicationModel model, 
			int game_id);
	
	/**
	 * Clears all commands and games
	 * @pre none
	 * @post there are no games or commands in the database
	 */
	public void eraseAll();	
}
