package client.communication;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

/**
 * Michael Rhodes
 * CS 240
 * Section 4
 * Dr. Rodham
 */
public class Serializer {

//******************************************* Static Fields/Methods *************************************************//
    public static Serializer SINGLETON;

    public static Serializer getSINGLETON() {
        if (SINGLETON == null) {
            SINGLETON = new Serializer();
        }
        return SINGLETON;
    }

//******************************************* Other Fields/Methods **************************************************//
    private Gson gson;

    private Serializer() {
        gson = new Gson();
    }

    public String serialize(Object o) {
        return gson.toJson(o);
    }

    public Object deserialize(String s) {
        return gson.fromJson(s, Object.class);
    }

    public Object deserialize(InputStream is) {
        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(is, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String result = writer.toString();
        return deserialize(result);
    }
}
