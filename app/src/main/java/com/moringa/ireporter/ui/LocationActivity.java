package com.moringa.ireporter.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.moringa.ireporter.R;

public class LocationActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mbtn;
    private TextView textView;
    private EditText mLocationPick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        textView = (TextView) findViewById(R.id.tv);
        mLocationPick = (EditText) findViewById(R.id.subjectPick);
        mbtn = (Button) findViewById(R.id.locationpickBtn);
        mbtn.setOnClickListener(this);


        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = mLocationPick.getText().toString();
                GeoCodingLocation locationAddress = new GeoCodingLocation();
                locationAddress.getAddressFromLocation(address,getApplicationContext(),new GeocoderHandler());
            }
        });
    }

    @Override
    public void onClick(View view) {

    }


    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what){
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    Log.d("latttt", locationAddress);
                    break;
                default:
                    locationAddress = null;
            }
            textView.setText(locationAddress);
        }
    }

}

