package plugin.helpers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 */
public class MyFileReader {
    private static MyFileReader SINGLETON;

    public static MyFileReader getSINGLETON() {
        if (SINGLETON == null) {
            SINGLETON = new MyFileReader();
        }
        return SINGLETON;
    }

    private Scanner input;

    private MyFileReader() {}

    public List<Object> readFile(File destDir, String fileName) {
        File savesRoot = new File("saves");
        if (!savesRoot.exists()) {
            return null;
        }
        destDir = new File(savesRoot.getPath() + File.separator + destDir.getName());
        if (!destDir.exists()) {
            return null;
        }
        File destination = new File(savesRoot.getPath() + File.separator + destDir.getName() +
                File.separator + fileName + ".txt");

        List<Object> list = new ArrayList<>();
        try {
            input = new Scanner(new BufferedInputStream(new FileInputStream(destination)));
            input.useDelimiter("\n\n");
            String holder;
            while (input.hasNext()) {
                holder = input.next();
                list.add(PluginSerializer.getSINGLETON().fromJSON(holder));
            }
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }
}
