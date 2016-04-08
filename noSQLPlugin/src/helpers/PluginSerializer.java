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
        gson = new Gson();
    }

    public String toJSON(Object o) {
        Gson gson2 = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(gson.toJson(o));
        String prettyJsonString = gson2.toJson(je);
        return prettyJsonString;
    }

    public Object fromJSON(String s) {
        return gson.fromJson(s, Object.class);
    }
}
