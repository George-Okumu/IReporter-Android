package com.moringa.ireporter.network;

import com.moringa.ireporter.models.Intervention;
import com.moringa.ireporter.models.InterventionRes;
import com.moringa.ireporter.models.RedFlag;
import com.moringa.ireporter.models.RedflagRes;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface IreporterApiInt {
    @Multipart
    @POST("/api/intervention")
    Call<InterventionRes> upload(
            @Part MultipartBody.Part image,
            @Part("subject") RequestBody subject,
            @Part("description")RequestBody description,
            @Part("location") RequestBody location,
            @Header("Authorization") String token
    );

    @GET("/api/intervention")
    Call<List<Intervention>>getIntervention();
}
