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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.moringa.ireporter.Constants;
import com.moringa.ireporter.MapsActivity;
import com.moringa.ireporter.R;
import com.moringa.ireporter.models.Intervention;
import com.moringa.ireporter.models.RedFlag;
import com.moringa.ireporter.network.IreporterApi;
import com.moringa.ireporter.network.IreporterApiInt;
import com.moringa.ireporter.network.IreporterClient;
import com.moringa.ireporter.network.IreporterIntClient;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CreateIntActivity extends AppCompatActivity  implements View.OnClickListener, ActivityCompat.OnRequestPermissionsResultCallback  {

    private static final int PERMISSION_REQUEST_CODE = 200;

    @BindView(R.id.subjectInt) EditText mSubject;
    @BindView(R.id.descriptionInt) EditText mDescription;
    @BindView(R.id.locationInt) EditText mLocation;
    @BindView(R.id.uploadImgint)
    Button mUploadImage;
    @BindView(R.id.createInt) Button mCreateIntervention;
    @BindView(R.id.imageInt)
    ImageView mImage;
    @BindView(R.id.locationBtnint) Button mLocationBtn;


    String imagePath ;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_int);
        ButterKnife.bind(this);

        mUploadImage.setOnClickListener(this);
        mCreateIntervention.setOnClickListener(this);
        mLocationBtn.setOnClickListener(this);
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

        if (v == mCreateIntervention) {

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
            }else if (mLocation.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(),"Location required",Toast.LENGTH_LONG).show();
                mLocation.setError("Required");
                return;
            }   else {
                uploadIntervention();
            }
        }

        if (v == mLocationBtn) {
            Intent intent = new Intent(CreateIntActivity.this, MapsActivity.class);
            startActivity(intent);
        }

    }

    private void uploadIntervention() {

        File file = new File(imagePath);
        RequestBody img = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part reqBody = MultipartBody.Part.createFormData("intervention_image", file.getName(),img);
        RequestBody subject = RequestBody.create(MediaType.parse("text/plain"),mSubject.getText().toString());
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"),mDescription.getText().toString());
        RequestBody location = RequestBody.create(MediaType.parse("text/plain"),mLocation.getText().toString());



        // Get token
        SharedPreferences sharedPrefs = getSharedPreferences(Constants.SHARED_PREFS,MODE_PRIVATE);
        String token = sharedPrefs.getString(Constants.USER_TOKEN,"");

        Retrofit retrofit = IreporterIntClient.getRetrofit();
        IreporterApiInt ireporterApi = retrofit.create(IreporterApiInt.class);
        Call call = ireporterApi.upload(reqBody,subject,description,location,"Bearer " + token);
        call.enqueue(new Callback<Intervention>() {
            @Override
            public void onResponse(Call call, Response response) {
                Toast.makeText(getApplicationContext(), "Intervention saved",Toast.LENGTH_LONG).show();
                startActivity(new Intent(CreateIntActivity.this, InterventionActivity.class));
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
                        ActivityCompat.requestPermissions(CreateIntActivity.this, new String[]{WRITE_EXTERNAL_STORAGE
                                , READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                    }
                });
                AlertDialog alert = alertBuilder.create();
                alert.show();
                Log.e("", "permission denied, show dialog");
            } else {
                ActivityCompat.requestPermissions(CreateIntActivity.this, new String[]{WRITE_EXTERNAL_STORAGE,
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