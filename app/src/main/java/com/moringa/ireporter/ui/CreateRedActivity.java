package com.moringa.ireporter.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.moringa.ireporter.R;
import com.moringa.ireporter.adapters.RedFlagAdapter;
import com.moringa.ireporter.models.RedFlag;
import com.moringa.ireporter.network.IreporterClientApi;
import com.moringa.ireporter.network.IreporterSearchApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateRedActivity extends AppCompatActivity {

    RedFlag redFlagData;
    TextInputLayout subject, description1, location;
    Button createRed;
    com.google.android.material.button.MaterialButton uploadImage, uploadVideo;
    private List<RedFlag> mRedFlag;
    private RecyclerView recyclerViewRed;
    RedFlagAdapter mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_red);

        subject = (TextInputLayout) findViewById(R.id.subjectRed);
        description1 = (TextInputLayout) findViewById(R.id.descriptionRed);
        location = (TextInputLayout) findViewById(R.id.locationRed);
        createRed = (Button) findViewById(R.id.createRed);

        createRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == createRed) {
                    Intent intent = new Intent(CreateRedActivity.this, RedFlagActivity.class);
                    startActivity(intent);
                    createRed();
                }

            }
        });
    }

    private boolean validate(TextInputLayout textInputLayout){
        if (textInputLayout.getEditText().toString().trim().length()>0) {
            return true;
        }
        textInputLayout.setError("Fill this");
        textInputLayout.requestFocus();
        return false;
    }

    private void createRed() {
        (IreporterClientApi.getClient().create(subject.getEditText().toString().trim(),
                description1.getEditText().toString().trim(),
                location.getEditText().toString().trim(),
                "Karuu")).enqueue(new Callback<RedFlag>() {
            @Override
            public void onResponse(Call<RedFlag> call, Response<RedFlag> response) {
                redFlagData = response.body();
                Intent intent = new Intent(CreateRedActivity.this, RedFlagActivity.class);
                startActivity(intent);
                if (response.isSuccessful()) {
                  mAdapter = new RedFlagAdapter(CreateRedActivity.this, mRedFlag);
                  recyclerViewRed.setAdapter(mAdapter);
                  RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CreateRedActivity.this);
                  recyclerViewRed.setLayoutManager(layoutManager);
                  recyclerViewRed.setHasFixedSize(true);

                  showRedFlag();
                }
            }

            @Override
            public void onFailure(Call<RedFlag> call, Throwable t) {
                Log.d("response", t.getStackTrace().toString());

            }
        });
    }

    private void showRedFlag() {
        recyclerViewRed.setVisibility(View.VISIBLE);
    }
}