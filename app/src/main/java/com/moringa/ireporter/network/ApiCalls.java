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
    private static final String TAG = "ApiCalls";

    public static void getRedFlags() {
        Retrofit retrofit = IreporterClient.getRetrofit();
        IreporterApi ireporterApi = retrofit.create(IreporterApi.class);
        Call<List<RedFlag>> call = ireporterApi.getRedFlags();
        call.enqueue(new Callback<List<RedFlag>>() {
            @Override
            public void onResponse(Call<List<RedFlag>> call, Response<List<RedFlag>> response) {
                if (response.isSuccessful()) {
                    redFlags = new ArrayList<>();
                    for (RedFlag redFlag: response.body() ) {
                        redFlags.add(redFlag);
                    }
                    Log.d(TAG,String.valueOf(redFlags.size()));
                }
            }
            @Override
            public void onFailure(Call<List<RedFlag>> call, Throwable t) {

            }
        });
    }

}
