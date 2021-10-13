package com.moringa.ireporter.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.moringa.ireporter.MainActivity;
import com.moringa.ireporter.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    private static int SPLASH_SCREEN = 5000;

    Animation anima, animaa;
    ImageView image;
    TextView splatext;

    @BindView(R.id.signupcreate) TextView mSignUpCreate;
    @BindView(R.id.signupname) EditText mSignUpName;
    @BindView(R.id.signupemail) TextView mSignUpEmail;
    @BindView(R.id.signuppassword) TextView mSignUpPass;
    @BindView(R.id.signupconfirm) TextView mSignUpConfirm;
    @BindView(R.id.signUpBtn) Button mSignUpButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        mSignUpButton.setOnClickListener(this);
        mSignUpCreate.setOnClickListener(this);



        anima = AnimationUtils.loadAnimation(this, R.anim.animaa);
        animaa = AnimationUtils.loadAnimation(this, R.anim.animaa);

        image = findViewById(R.id.signUpImage);
        splatext = findViewById(R.id.signupcreate);

        image.setAnimation(animaa);
        splatext.setAnimation(animaa);
    }

    @Override
    public void onClick(View view) {
        if (view == mSignUpButton) {
            String name = mSignUpName.getText().toString();
            String email = mSignUpEmail.getText().toString();
            String pass = mSignUpPass.getText().toString();
            String passConfirm = mSignUpConfirm.getText().toString();
            if (name.equals("") || email.equals("") || pass.equals("") || passConfirm.equals("")) {
                Toast.makeText(getApplicationContext(),"All fields are Required",Toast.LENGTH_LONG).show();
                return;
            } else if (!pass.equals(passConfirm)) {
                Toast.makeText(getApplicationContext(),"Password does not Match",Toast.LENGTH_LONG).show();
                return;
            }
            try {
                registerUser(name,email,pass);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if (view == mSignUpCreate) {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public void registerUser(String user,String email, String password) throws Exception {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String postUrl = "https://ireporter-a.herokuapp.com/account/register";

        JSONObject postData = new JSONObject();
        postData.put("email", email);
        postData.put("username", user);
        postData.put("password", password);
        // Make post request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // User created
                AlertDialog alertDialog = new AlertDialog.Builder(SignUpActivity.this).create();
                alertDialog.setTitle("Success");
                alertDialog.setIcon(R.drawable.success);
                alertDialog.setMessage("Confirmation sent to your email.\n Please verify to log in.");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                alertDialog.show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Error Alert
                AlertDialog alertDialog = new AlertDialog.Builder(SignUpActivity.this).create();
                alertDialog.setTitle("Failure");
                alertDialog.setIcon(R.drawable.warning_24);
                alertDialog.setMessage("Email and/or Username already taken.");
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