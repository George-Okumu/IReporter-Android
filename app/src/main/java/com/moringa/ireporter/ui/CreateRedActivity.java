package com.moringa.ireporter.ui;

import static android.os.Environment.getExternalStoragePublicDirectory;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import com.google.android.material.textfield.TextInputLayout;
import com.moringa.ireporter.Constants;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CreateRedActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 11;

    String filepth="dummy";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 2) {
                imageUri = data.getData();
                mImage.setImageURI(imageUri);


                String[] filePathCol = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(imageUri,filePathCol,null,null,null);
                if ( cursor != null) {
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndexOrThrow(filePathCol[0]);
                    filepth = cursor.getString(columnIndex);
                    cursor.close();
                    Log.d("filepath",filepth);
                }



            }
        }
    }

    RedFlag redFlagData;
    TextInputLayout subject, description1, location;
    com.google.android.material.button.MaterialButton uploadImage, uploadVideo;
    private List<RedFlag> mRedFlag;
    private RecyclerView recyclerViewRed;
    RedFlagAdapter mAdapter;

    @BindView(R.id.subjectRed) EditText mSubject;
    @BindView(R.id.descriptionRed) EditText mDescription;
    @BindView(R.id.locationRed) EditText mLocation;
    @BindView(R.id.uploadImg) Button mUploadImage;
    @BindView(R.id.createRed) Button mCreateRedFlag;
    @BindView(R.id.image) ImageView mImage;

    ActivityResultLauncher<String> getImageUri;
    String imagePath ;
    Uri imageUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_red);
        ButterKnife.bind(this);
        mUploadImage.setOnClickListener(this);
        mCreateRedFlag.setOnClickListener(this);

//        getImageUri = registerForActivityResult(new ActivityResultContracts.GetContent(),
//                new ActivityResultCallback<Uri>() {
//                    @Override
//                    public void onActivityResult(Uri uri) {
//                        // Handle the returned Uri
//                        SharedPreferences prefs = getSharedPreferences(Constants.SHARED_PREFS,MODE_PRIVATE);
//
//                        DecodeJWT.decoded( prefs.getString(Constants.USER_TOKEN,""));
//                        JWT parsedJWT = new JWT(prefs.getString(Constants.USER_TOKEN,""));
//                        Claim subscriptionMetaData = parsedJWT.getClaim("username");
//                        String parsedValue = subscriptionMetaData.asString();
//                        mUploadImage.setText(parsedValue + uri.getLastPathSegment());
//                    }
//                });
    }

    @Override
    public void onClick(View v) {
        if (v == mUploadImage) {

            Intent galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent,2);
        }

        if (v == mCreateRedFlag) {

            if (imageUri == null) {
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

    private void dispatchPictureTakenAction() {
        Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePic.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            photoFile = createPhotoFile();
            if (photoFile != null) {
                imagePath = photoFile.getAbsolutePath();
                Uri photoUri = FileProvider.getUriForFile(CreateRedActivity.this,"sss",photoFile);
                takePic.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                startActivityForResult(takePic,1);
            }


        }
    }

    private File createPhotoFile() {
        String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS);
        File image = null;
        try {
            image = File.createTempFile(name,".jpg",storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private void uploadRedFlag() {
        File file = new File(filepth);

        Log.d("file",imageUri.getPath());
       // RequestBody imageData = RequestBody.create(MediaType.parse("image/*"),file);
        //MultipartBody.Part part = MultipartBody.Part.createFormData("redFlag_image", file.getName(),imageData);

         RequestBody imageData = RequestBody.create(MediaType.parse(getContentResolver().getType(imageUri)),file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("redFlag_image", file.getName(),imageData);


        RequestBody subject = RequestBody.create(MediaType.parse("text/plain"),mSubject.getText().toString());
        RequestBody desc = RequestBody.create(MediaType.parse("text/plain"),mDescription.getText().toString());
        RequestBody location = RequestBody.create(MediaType.parse("text/plain"),mLocation.getText().toString());

        Retrofit retrofit = IreporterClientApi.getRetrofit();
        UploadService uploadService = retrofit.create(UploadService.class);
        Call call = uploadService.upload(part,desc,subject,location,"Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6IkthcnV1MiIsImlzX2FkbWluIjpmYWxzZSwiaWQiOjExLCJleHAiOjE2MzY4ODAwNDMsImVtYWlsIjoiYnJpYW4ubXVjaG9yaTFAc3R1ZGVudC5tb3Jpbmdhc2Nob29sLmNvbSJ9.u8hSLVEvnUGuxVQG1RHwzhrwd3qJ0VTRtLLqwXL0yZ8" );
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.d("Response",response.toString());
            }

            @Override
            public void onFailure(Call call, Throwable t) {
               Log.d("Response",t.toString());

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