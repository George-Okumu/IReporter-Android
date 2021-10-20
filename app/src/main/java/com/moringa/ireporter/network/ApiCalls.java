package com.moringa.ireporter.network;

import android.util.Log;

import com.moringa.ireporter.models.RedFlag;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ApiCalls {
    public static List<RedFlag> redFlags;
    public static RedFlag redFlag;
    private static Retrofit retrofit = IreporterClient.getRetrofit();;
    private static IreporterApi ireporterApi = retrofit.create(IreporterApi.class);

    // Testing a simpler way
    public static Retrofit retrofit1 = IreporterClient.getRetrofit();
    public static IreporterApi ireporter1 = retrofit1.create(IreporterApi.class);


    public static void getRedFlags() {
        Call<List<RedFlag>> call = ireporterApi.getRedFlags();
        call.enqueue(new Callback<List<RedFlag>>() {
            @Override
            public void onResponse(Call<List<RedFlag>> call, Response<List<RedFlag>> response) {
                if (response.isSuccessful()) {
                    redFlags = new ArrayList<>();
                    for (RedFlag redFlag: response.body() ) {
                        redFlags.add(redFlag);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<RedFlag>> call, Throwable t) {

            }
        });
    }

    public static Callback<RedFlag> getRedFlagById (int id) {
        Call <RedFlag> call = ireporterApi.getRedFlagById(id);
        Callback res = new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        };
        return res;

    }
}
