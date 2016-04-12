package plugin.DAOs;

import plugin.helpers.MyFileReader;
import plugin.helpers.MyFileWriter;
import plugin.helpers.PluginSerializer;
import server.plugin_attachments.IGameDAO;

import java.io.File;
import java.util.HashMap;
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
        Map<Integer, Object> map = new HashMap<>();
        File root = new File("saves");
        if (!root.exists()) {
            return null;
        }
        File source = new File("games");
        File fullSource = new File(root.getName() + File.separator + source.getName());
        String[] files = fullSource.list();
        if (!fullSource.exists()) {
            return null;
        }
        for (String file: files) {
            int id = extractID(file);
            List<Object> game = MyFileReader.getSINGLETON().readFile(source, removeExt(file));
            map.put(id, game.get(0));
        }
        return map;
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
        Map<Integer, List<Object>> map = new HashMap<>();
        File root = new File("saves");
        if (!root.exists()) {
            return null;
        }
        File source = new File("commands");
        File fullSource = new File(root.getName() + File.separator + source.getName());
        String[] files = fullSource.list();
        if (!fullSource.exists()) {
            return null;
        }
        for (String file: files) {
            int id = extractID(file);
            List<Object> commands = MyFileReader.getSINGLETON().readFile(source, removeExt(file));
            map.put(id, commands);
        }
        return map;
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
        File destDir = new File("commands");
        String fileName = "game_" + String.valueOf(game_id) + "_commands";
        MyFileWriter writer = MyFileWriter.getSINGLETON();
        PluginSerializer serializer = PluginSerializer.getSINGLETON();
        writer.writeFile(destDir, fileName, serializer.toJSON(command), true);
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
        File destDir = new File("games");
        String fileName = "game_" + String.valueOf(game_id) + "_model";
        MyFileWriter writer = MyFileWriter.getSINGLETON();
        PluginSerializer serializer = PluginSerializer.getSINGLETON();
        writer.writeFile(destDir, fileName, serializer.toJSON(model), false);

        emptyCommandsForGame(game_id);
    }

    /**
     * Clears all saved commands and games
     * @pre none
     * @post there are no games or commands in the database
     */
    @Override
    public void eraseAll() {
        File root = new File("saves");
        if (!root.exists()) return;
        recursiveErase(root);
    }

    private int extractID(String fileName) {
        String[] strings = fileName.split("_");
        return Integer.parseInt(strings[1]);
    }

    private String removeExt(String s) {
        return s.substring(0, s.indexOf('.'));
    }

    private void emptyCommandsForGame(int game_id) {
        File root = new File("saves");
        if (!root.exists()) return;
        File destDir = new File(root.getName() + File.separator + "commands");
        if (!destDir.exists()) return;
        File[] list = destDir.listFiles();
        for (File file: list) {
            if (extractID(file.getName()) == game_id) {
                file.delete();
            }
        }
    }

    private void recursiveErase(File dir) {
        File[] list = dir.listFiles();
        for (File file: list) {
            if (file.isDirectory() && file.getName().equals("users")) continue;
            if (file.isDirectory()) {
                recursiveErase(file);
            } else {
                file.delete();
            }
        }
    }
}
