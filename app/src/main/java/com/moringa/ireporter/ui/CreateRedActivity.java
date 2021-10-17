package com.moringa.ireporter.ui;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;
import com.moringa.ireporter.Constants;
import com.moringa.ireporter.MainActivity;
import com.moringa.ireporter.R;
import com.moringa.ireporter.adapters.RedFlagAdapter;
import com.moringa.ireporter.models.DecodeJWT;
import com.moringa.ireporter.models.RedFlag;
import com.moringa.ireporter.network.IreporterClientApi;
import com.moringa.ireporter.network.UploadService;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Multipart;
import retrofit2.http.Url;

public class CreateRedActivity extends AppCompatActivity implements View.OnClickListener, ActivityCompat.OnRequestPermissionsResultCallback {
    private static final int PERMISSION_REQUEST_CODE = 200;

    @BindView(R.id.subjectRed) EditText mSubject;
    @BindView(R.id.descriptionRed) EditText mDescription;
    @BindView(R.id.locationRed) EditText mLocation;
    @BindView(R.id.uploadImg) Button mUploadImage;
    @BindView(R.id.createRed) Button mCreateRedFlag;
    @BindView(R.id.image) ImageView mImage;

    String imagePath ;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_red);
        ButterKnife.bind(this);
        mUploadImage.setOnClickListener(this);
        mCreateRedFlag.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mUploadImage) {
            Intent intent = new Intent(Intent.ACTION_PICK).setType("image/*");
            if (!checkPermission()) {
                getImageUri.launch(intent);
            } else {
                if (checkPermission()) {
                    requestPermissionAndContinue();
                } else {
                    getImageUri.launch(intent);
                }
            }
        }

        if (v == mCreateRedFlag) {

            if (imagePath == null) {
                Toast.makeText(getApplicationContext(),"Image required",Toast.LENGTH_LONG).show();
                return;
            } else if (mSubject.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(),"Subject required",Toast.LENGTH_LONG).show();
                mSubject.setError("Required");
                return;
            } else if (mDescription.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(),"Description required",Toast.LENGTH_LONG).show();
                mDescription.setError("Required");
                return;
            }else {
                uploadRedFlag();
            }
        }

    }

    private void uploadRedFlag() {

        File file = new File(imagePath);
        RequestBody img = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part reqBody = MultipartBody.Part.createFormData("redFlag_image", file.getName(),img);
        RequestBody title = RequestBody.create(MediaType.parse("text/plain"),mSubject.getText().toString());
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"),mDescription.getText().toString());
        RequestBody location = RequestBody.create(MediaType.parse("text/plain"),mLocation.getText().toString());

        // Get token
        SharedPreferences sharedPrefs = getSharedPreferences(Constants.SHARED_PREFS,MODE_PRIVATE);
        String token = sharedPrefs.getString(Constants.USER_TOKEN,"");

        Retrofit retrofit = IreporterClientApi.getRetrofit();
        UploadService uploadService = retrofit.create(UploadService.class);
        Call call = uploadService.upload(reqBody,description,title,location,"Bearer " + token);
        call.enqueue(new Callback<RedFlag>() {
            @Override
            public void onResponse(Call call, Response response) {
                Toast.makeText(getApplicationContext(), "Red Flag saved",Toast.LENGTH_LONG).show();
                startActivity(new Intent(CreateRedActivity.this, MainActivity.class));
            }

            @Override
            public void onFailure(Call call, Throwable t) {
               Log.d("Failure",t.toString());

            }
        });
    }

    ActivityResultLauncher<Intent> getImageUri = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Uri selectedImageUri = data.getData( );
                        String picturePath = getPath(getApplicationContext( ), selectedImageUri );
                        imagePath = picturePath;
                        //mImage.setImageURI(selectedImageUri);
                        Glide.with(getApplicationContext()).load(selectedImageUri).into(mImage);
                    }
                }
            }
    );

    public String getPath(Context context, Uri uri ) {
        String result = null;
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getApplicationContext(),uri,filePathColumn,null,null,null);
        Cursor cursor = loader.loadInBackground();
        int col_index = cursor.getColumnIndexOrThrow( MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        result = cursor.getString(col_index);
        cursor.close();
        return result;
    }

    private boolean checkPermission() {

        return ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ;
    }

    private void requestPermissionAndContinue() {
        if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, WRITE_EXTERNAL_STORAGE)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, READ_EXTERNAL_STORAGE)) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle("getString(R.string.permission_necessary)");
                alertBuilder.setMessage("R.string.storage_permission_is_encessary_to_wrote_event");
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(CreateRedActivity.this, new String[]{WRITE_EXTERNAL_STORAGE
                                , READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                    }
                });
                AlertDialog alert = alertBuilder.create();
                alert.show();
                Log.e("", "permission denied, show dialog");
            } else {
                ActivityCompat.requestPermissions(CreateRedActivity.this, new String[]{WRITE_EXTERNAL_STORAGE,
                        READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }
        } else {
            openActivity();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (permissions.length > 0 && grantResults.length > 0) {

                boolean flag = true;
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        flag = false;
                    }
                }
                if (flag) {
                    openActivity();
                } else {
                    finish();
                }

            } else {
                finish();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void openActivity() {
        //add your further process after giving permission or to download images from remote server.
    }

}