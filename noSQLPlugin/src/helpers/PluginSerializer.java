package helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 */
public class PluginSerializer {

    private static PluginSerializer SINGLETON;

    public static PluginSerializer getSINGLETON() {
        if (SINGLETON == null) {
            SINGLETON = new PluginSerializer();
        }
        return SINGLETON;
    }

    private Gson gson;

    private PluginSerializer() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public String toJSON(Object o) {
        return gson.toJson(o);
    }

    public Object fromJSON(String s) {
        return gson.fromJson(s, Object.class);
    }
}
