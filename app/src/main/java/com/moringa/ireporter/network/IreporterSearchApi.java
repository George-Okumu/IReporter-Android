package com.moringa.ireporter.network;

import com.moringa.ireporter.models.RedFlag;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IreporterSearchApi {

    String BASE_URL = "https://ireporter-a.herokuapp.com/api/";

    @FormUrlEncoded
    @POST("redflag")
    Call<RedFlag> create(@Field("title")String  title,
                         @Field("description")String description,
                         @Field("redflag_image")String redflag_image,
                         @Field("user")String user);


}
