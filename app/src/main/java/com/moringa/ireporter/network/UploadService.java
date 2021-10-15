package com.moringa.ireporter.network;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UploadService {
    @Multipart
    @POST("redflag")
    Call<RequestBody> upload(
            @Part MultipartBody.Part file,
            @Part("description")RequestBody description,
            @Part("title") RequestBody title,
            @Part("redFlag_location") RequestBody location,
            @Header("Authorization") String token
    );
}
