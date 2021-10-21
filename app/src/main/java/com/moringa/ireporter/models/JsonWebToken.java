package com.moringa.ireporter.models;

import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.UnsupportedEncodingException;

public class JsonWebToken {
    private static final String TAG = "JsonWebToken";

    private String header;

    private  String body;
    private String username;
    private int id;
    private String userType;

    public JsonWebToken(String token) {
        decoded(token);
    }

    // {"username":"philip","id":1,"exp":1637307706,"email":"philip.owino@student.moringaschool.com","userType":"USER"}

    private void decoded(String JWTEncoded){
        String[] split = JWTEncoded.split("\\.");
        //header = getJson(split[0]);
        body = getJson(split[1]);

        JsonObject bodyObj = new Gson().fromJson(body,JsonObject.class);

        username = bodyObj.get("username").getAsString();
        id = bodyObj.get("id").getAsInt();
        userType = bodyObj.get("userType").getAsString();
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public String getUserType() {
        return userType;
    }

    private static String getJson(String strEncoded) {
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        try {
            return new String(decodedBytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    };
}
