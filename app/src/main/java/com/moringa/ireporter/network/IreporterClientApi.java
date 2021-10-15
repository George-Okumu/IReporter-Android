package com.moringa.ireporter.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class IreporterClientApi {

    public static String BASE_URL ="https://ireporter-a.herokuapp.com/api/";
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit() {
        OkHttpClient okHttpClient  = new OkHttpClient.Builder().build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    public static IreporterSearchApi getClient(){
        if(retrofit == null){

        }
        IreporterSearchApi api = retrofit.create(IreporterSearchApi.class);
        return api;
    }


}
