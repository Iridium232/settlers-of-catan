package helpers;

import java.io.*;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 */
public class MyFileWriter {
    private static MyFileWriter SINGLETON;

    public static MyFileWriter getSINGLETON() {
        if (SINGLETON == null) {
            SINGLETON = new MyFileWriter();
        }
        return SINGLETON;
    }

    private PrintWriter output;

    private MyFileWriter() {}

    public boolean writeFile(File destDir, String fileName, String data, boolean append) {
        File savesRoot = new File("saves");
        if (!savesRoot.exists()) {
            savesRoot.mkdir();
        }
        destDir = new File(savesRoot.getPath() + File.separator + destDir.getName());
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        File destination = new File(savesRoot.getPath() + File.separator + destDir.getName() +
                File.separator + fileName + ".txt");

        try {
            output = new PrintWriter(new BufferedWriter(new FileWriter(destination, append)));
            output.print(data);
            output.println("\n");
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
