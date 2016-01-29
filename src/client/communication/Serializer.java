package client.communication;

import com.google.gson.Gson;
import org.json.JSONObject;

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
    private Serializer() {
    }

    public JSONObject serialize(Object o) {
        Gson gson = new Gson();

        return null;
    }
}
