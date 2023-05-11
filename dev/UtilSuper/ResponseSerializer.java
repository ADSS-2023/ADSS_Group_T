package UtilSuper;

import com.google.gson.Gson;

public class ResponseSerializer {

    private static final Gson gson = new Gson();

    public static String serializeToJson(Response response) {
        return gson.toJson(response);
    }

    public static Response deserializeFromJson(String jsonString) {
        return gson.fromJson(jsonString, Response.class);
    }
}
