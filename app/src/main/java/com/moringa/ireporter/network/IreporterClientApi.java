package com.moringa.ireporter.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class IreporterClientApi {

    public static String BASE_URL ="https://ireporter-a.herokuapp.com/api/";
    private static Retrofit retrofit = null;
    public static IreporterSearchApi getClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        IreporterSearchApi api = retrofit.create(IreporterSearchApi.class);
        return api;
    }


}
