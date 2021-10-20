package com.moringa.ireporter.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.moringa.ireporter.Constants;
import com.moringa.ireporter.R;
import com.moringa.ireporter.models.JsonWebToken;
import com.moringa.ireporter.models.RedFlag;
import com.moringa.ireporter.network.ApiCalls;
import com.moringa.ireporter.network.IreporterApi;
import com.moringa.ireporter.network.IreporterClient;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RedFlagEditActitvity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "RedFlagEditActitvity";

    private RedFlag mRedFlag;
    private String mUserToken;

    @BindView(R.id.tv_status) TextView mStatus;
    @BindView(R.id.tv_subject) EditText mSubject;
    @BindView(R.id.tv_description) EditText mDescription;
    @BindView(R.id.tv_location) EditText mLocation;
    @BindView(R.id.img_image) ImageView mImage;
    @BindView(R.id.btn_edit) Button mCancelBtn;
    @BindView(R.id.btn_save) Button mSaveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_flag_edit_actitvity);
        ButterKnife.bind(this);
        mCancelBtn.setOnClickListener(this);
        mSaveBtn.setOnClickListener(this);
        mRedFlag = Parcels.unwrap(getIntent().getParcelableExtra("redflag"));
        assignViews(mRedFlag);
    }

    @Override
    public void onClick(View v) {
        if (v == mCancelBtn) {
            Intent intent = new Intent(RedFlagEditActitvity.this, RedFlagItemDetail.class);
            intent.putExtra("redflag", Parcels.wrap(mRedFlag));
            startActivity(intent);
            finish();

        }
        if (v == mSaveBtn) {
            // Show spinner bar
            // Save the detail and reload the page with the details
            postDataAndReload();


        }

    }

    private void assignViews(RedFlag redFlag) {
        mStatus.setText(redFlag.getStatus());
        mSubject.setText(redFlag.getTitle());
        mDescription.setText(redFlag.getDescription());
        mLocation.setText(redFlag.getLocation());
        // Load image into mImage view
        Picasso.get().load(redFlag.getImageUrl())
                .fit()
                .centerCrop()
                .into(mImage);
        // Show Edit button as active only if the post belong to this user and the status of redFlag is "received"
        SharedPreferences sharePrefs = getSharedPreferences(Constants.SHARED_PREFS,MODE_PRIVATE);
        String token = sharePrefs.getString(Constants.USER_TOKEN,"");
        mUserToken = token;
        JsonWebToken jwt = new JsonWebToken(token);
    }

    private void postDataAndReload() {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("title",mSubject.getText().toString())
                .addFormDataPart("description", mDescription.getText().toString())
                .addFormDataPart("redFlag_location",mLocation.getText().toString())
                .build();
        Call<RedFlag> call = ApiCalls.ireporter1.updateRedFlagById("Bearer " + mUserToken, mRedFlag.getId(),requestBody);
        call.enqueue(new Callback<RedFlag>() {
            @Override
            public void onResponse(Call<RedFlag> call, Response<RedFlag> response) {
                Log.d(TAG, "onResponse: " + response.body().getUser());
                if (response.isSuccessful()) {
                    mRedFlag = response.body();
                    Intent intent = new Intent(RedFlagEditActitvity.this, RedFlagItemDetail.class);
                    intent.putExtra("redflag", Parcels.wrap(mRedFlag));
                    startActivity(intent);
                    finish();
           }

            }

            @Override
            public void onFailure(Call<RedFlag> call, Throwable t) {

            }
        });
    }

}