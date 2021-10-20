package com.moringa.ireporter.network;

import com.moringa.ireporter.models.RedFlag;
import com.moringa.ireporter.models.RedFlagsResponse;
import com.moringa.ireporter.models.RedflagRes;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface IreporterApi {
    @Multipart
    @POST("/api/redflag")
    Call<RedflagRes> upload(
           @Part MultipartBody.Part image,
            @Part("title") RequestBody title,
            @Part("description")RequestBody description,
            @Part("redFlag_location") RequestBody location,
           @Header("Authorization") String token
    );

    @GET("/api/redflag") Call<List<RedFlag>>getRedFlags();

    @GET("/api/redflag/{id}")
    Call<RedFlag>getRedFlagById(
            @Path("id") int id
    );

    @PUT("/api/redflag/{id}")
    Call<RedFlag> updateRedFlagById(
            @Header("Authorization") String token,
            @Path("id") int id,
            @Body RequestBody body
    );

    @DELETE("/api/redflag/{id}")
    Call deleteRedFlagById(
            @Header("Authorization") String token,
            @Path("id") int id
    );

}
