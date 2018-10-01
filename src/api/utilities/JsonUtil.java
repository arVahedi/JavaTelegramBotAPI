package api.utilities;

import api.json.JSONObject;
import com.google.gson.Gson;

/**
 * Created by Gladiator on 8/26/2016 AD.
 */
public class JsonUtil {

    public static String toJsonSerializable(Object object) {
        return new Gson().toJson(object);
    }

    public static Object fromJsonSerializable(String json, Class klass) {
        return new Gson().fromJson(json, klass);
    }

}
