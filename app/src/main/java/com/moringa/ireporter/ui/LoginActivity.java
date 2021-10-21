package com.moringa.ireporter.ui;
import static com.moringa.ireporter.Constants.SHARED_PREFS;
import static com.moringa.ireporter.Constants.USER_TOKEN;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.moringa.ireporter.Constants;
import com.moringa.ireporter.MainActivity;
import com.moringa.ireporter.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static int SPLASH_SCREEN = 5000;
    SharedPreferences sharedPreferences;

    Animation anima, animaa;
    ImageView image;
    TextView splatext;
    @BindView(R.id.loginregister)
    TextView mLoginRegister;
    @BindView(R.id.loginBtn)
    TextView mLoginBtn;
    @BindView(R.id.loginEmail)
    TextView mEmail;
    @BindView(R.id.LoginPassword)
    TextView mPassword;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mLoginRegister.setOnClickListener(this);
        mLoginBtn.setOnClickListener(this);

        anima = AnimationUtils.loadAnimation(this, R.anim.animaa);
        animaa = AnimationUtils.loadAnimation(this, R.anim.animaa);

        image = findViewById(R.id.loginImage);
        splatext = findViewById(R.id.loginregister);

        image.setAnimation(animaa);
        splatext.setAnimation(animaa);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user exist and log in automatically
        sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        String token = sharedPreferences.getString(USER_TOKEN,"");
        if (!token.equals("")) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View view) {

        if (view == mLoginRegister) {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();
        }

        if (view == mLoginBtn) {

            try {
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                authenticateUser(email, password);
                mProgressBar.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public void authenticateUser(String email, String password) throws Exception {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String postUrl = "https://ireporter-a.herokuapp.com/account/login";

        JSONObject postData = new JSONObject();
        postData.put("email", email);
        postData.put("password", password);
        // Make post request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    mProgressBar.setVisibility(View.GONE);
                    if (response.getString("message").equals("Login Successfull")) {
                        // Save user token to shared preference
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(USER_TOKEN,response.getString("token"));
                        editor.apply();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressBar.setVisibility(View.GONE);
                AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                alertDialog.setTitle("Invalid I-Reporter Credentials");
                alertDialog.setIcon(R.drawable.warning_24);
                alertDialog.setMessage("Check email and/or password.");

                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alertDialog.show();
            }
        });
        queue.add(jsonObjectRequest);
    }
}