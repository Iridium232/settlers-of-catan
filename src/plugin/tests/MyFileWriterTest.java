package plugin.tests;


import junit.framework.TestCase;
import org.json.JSONObject;

import plugin.helpers.MyFileWriter;
import plugin.helpers.PluginSerializer;

import java.io.File;
import java.util.ArrayList;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 */
public class MyFileWriterTest extends TestCase {
    public void testWriteFile() throws Exception {
        MyFileWriter writer = MyFileWriter.getSINGLETON();
        PluginSerializer serializer = PluginSerializer.getSINGLETON();
        JSONObject object = new JSONObject();
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(String.valueOf(i));
        }
        object.put("id", 0001234);
        object.put("name", "Test");
        object.put("array", list);
        writer.writeFile(new File("TestDir"), "testCase", serializer.toJSON(object), true);
        File createdFile = new File("saves/TestDir/testcase.txt");
        assertTrue(createdFile.exists());
    }
}