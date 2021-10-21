package com.moringa.ireporter.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.moringa.ireporter.Constants;
import com.moringa.ireporter.R;
import com.moringa.ireporter.models.JsonWebToken;
import com.moringa.ireporter.models.RedFlag;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RedFlagItemDetail extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RedFlagItemDetail" ;
    private RedFlag mRedFlag;
    private SharedPreferences sharePrefs;
    private String token;
    private JsonWebToken jwt;


    @BindView(R.id.tv_status) TextView mStatus;
    @BindView(R.id.tv_subject)
    EditText mSubject;
    @BindView(R.id.tv_description) TextView mDescription;
    @BindView(R.id.tv_location) TextView mLocation;
    @BindView(R.id.img_image) ImageView mImage;
    @BindView(R.id.btn_edit) Button mEditBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_flag_item_detail);
        ButterKnife.bind(this);
        sharePrefs = getSharedPreferences(Constants.SHARED_PREFS,MODE_PRIVATE);
        token = sharePrefs.getString(Constants.USER_TOKEN,"");
        mRedFlag = Parcels.unwrap(getIntent().getParcelableExtra("redflag"));
        jwt = new JsonWebToken(token);
        assignViews(mRedFlag);

        mEditBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        if ( v == mEditBtn) {
            if(mRedFlag.getUser().equals(jwt.getUsername()) && mRedFlag.getStatus().equals("received")){
                Intent intent = new Intent(RedFlagItemDetail.this, RedFlagEditActitvity.class);
                intent.putExtra("redflag", Parcels.wrap(mRedFlag));
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(RedFlagItemDetail.this, "You Cannot Edit this", Toast.LENGTH_SHORT).show();
            }
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
        JsonWebToken jwt = new JsonWebToken(token);
        if (mRedFlag.getUser().equals(jwt.getUsername()) && mRedFlag.getStatus().equals("received")) {
        }else {
            mEditBtn.setAlpha((float) 0.5);
        }
    }
}