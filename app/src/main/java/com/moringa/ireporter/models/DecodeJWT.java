package com.moringa.ireporter.models;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;

public class DecodeJWT {
    public static void decoded(String JWTEncoded){
        String[] split = JWTEncoded.split("\\.");
        Log.d("JWT_DECODED", "Header: " + getJson(split[0]));
        Log.d("JWT_DECODED", "Body: " + getJson(split[1]));
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
