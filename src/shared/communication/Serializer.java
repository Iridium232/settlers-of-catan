package shared.communication;

import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

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

    public JSONObject deserialize(String s) {
        JSONObject result = null;
        try {
            if (s.equals("Success")) {
                return new JSONObject().put("Success", s);
            } else if (s.equals("true")) {
                return new JSONObject().put("True", s);
            }
            result = new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.print("\nCould not deserialize " +s+ "\n");
        }
        return result;
    }

    public JSONObject deserialize(InputStream is) {
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
