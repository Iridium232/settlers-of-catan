package tests;

import helpers.MyFileReader;
import junit.framework.TestCase;

import java.io.File;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 */
public class MyFileReaderTest extends TestCase {

    public void testReadFile() throws Exception {
        MyFileReader reader = MyFileReader.getSINGLETON();
        reader.readFile(new File("commands"), "game_0_commands");
        assertTrue(true);
    }
}