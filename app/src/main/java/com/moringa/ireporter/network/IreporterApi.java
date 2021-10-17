package com.moringa.ireporter.network;

import com.moringa.ireporter.models.RedFlag;
import com.moringa.ireporter.models.RedFlagsResponse;
import com.moringa.ireporter.models.RedflagRes;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface IreporterApi {
    @Multipart
    @POST("redflag")
    Call<RedflagRes> upload(
           @Part MultipartBody.Part image,
            @Part("title") RequestBody title,
            @Part("description")RequestBody description,
            @Part("redFlag_location") RequestBody location,
           @Header("Authorization") String token
    );

    @GET("redflag")
    Call<List<RedFlag>>getRedFlags();
}
