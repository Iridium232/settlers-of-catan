package DAOs;

import helpers.MyFileWriter;
import helpers.PluginSerializer;

import java.io.File;
import java.util.List;
import java.util.Map;


/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 */
public class GameDAO implements IGameDAO {
    /**
     * Loads a game from a file and Gets a map of Game models referenced by game_id
     * @pre none
     * @post A map of all stored users is returned
     * @return
     */
    @Override
    public Map<Integer, Object> getGames() {
        return null;
    }

    /**
     * Loads a list of commands from a file specific to a particular game and returns them
     * 
     * @pre none
     * @post returns a map of command lists by game_id
     * @return
     */
    @Override
    public Map<Integer, List<Object>> getCommands() {
        return null;
    }

    /**
     * Saves a serialization of the command to a file specific to a particular game
     *
     * @pre none
     * @post saves a command to the command list for the game specified
     * @param command
     * @param game_id
     */
    @Override
    public void saveCommand(Object command, int game_id) {
        File destDir = new File("Commands");
        String fileName = "game_" + String.valueOf(game_id) + "_commands";
        MyFileWriter writer = MyFileWriter.getSINGLETON();
        PluginSerializer serializer = PluginSerializer.getSINGLETON();
        writer.writeFile(destDir, fileName, serializer.toJSON(0), true);
    }

    /**
     * Saves a serialization of the model to a file and empties the commands in the related file
     * @pre none
     * @post the model is written to the database and the command list is emptied
     *
     * @param model
     * @param game_id
     */
    @Override
    public void saveModelAndEmptyCommands(Object model, int game_id) {

    }

    /**
     * Clears all saved commands and games
     * @pre none
     * @post there are no games or commands in the database
     */
    @Override
    public void eraseAll() {

    }
}
