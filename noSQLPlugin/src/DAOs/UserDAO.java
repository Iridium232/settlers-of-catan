package DAOs;

import helpers.MyFileReader;
import helpers.MyFileWriter;
import helpers.PluginSerializer;

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
public class UserDAO implements IUserDAO {

    /**
     * gets a list of stored users from a file
     * mapped to their user_id
     * @pre none
     * @post A map of all stored users is returned
     * @return
     */
    @Override
    public Map<Integer, Object> getUsers() {
        Map<Integer, Object> map = new HashMap<>();
        File root = new File("saves");
        if (!root.exists()) {
            return null;
        }
        File source = new File("users");
        File fullSource = new File(root.getName() + File.separator + source.getName());
        String[] files = fullSource.list();
        if (!fullSource.exists()) {
            return null;
        }
        for (String file: files) {
            int id = extractID(removeExt(file));
            List<Object> user = MyFileReader.getSINGLETON().readFile(source, removeExt(file));
            map.put(id, user.get(0));
        }
        return map;
    }

    /**
     * Add the serialization of a User to a file
     *
     * @pre none
     * @post the user is stored according to their user_id
     * @param user
     * @param user_id
     */
    @Override
    public void addUser(Object user, int user_id) {
        File destDir = new File("users");
        String fileName = "user_" + String.valueOf(user_id);
        MyFileWriter writer = MyFileWriter.getSINGLETON();
        PluginSerializer serializer = PluginSerializer.getSINGLETON();
        writer.writeFile(destDir, fileName, serializer.toJSON(user), true);
    }

    /**
     * Clear the list of users in the file
     *
     * @pre none
     * @post there are no stored users left
     */
    @Override
    public void clearAll() {
        File target = new File("saves/users");
        if (!target.exists()) return;
        File[] files = target.listFiles();
        for (File file: files) {
            file.delete();
        }
    }

    private int extractID(String fileName) {
        String[] strings = fileName.split("_");
        return Integer.parseInt(strings[1]);
    }

    private String removeExt(String s) {
        return s.substring(0, s.indexOf('.'));
    }
}
