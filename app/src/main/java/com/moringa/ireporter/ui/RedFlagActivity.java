package com.moringa.ireporter.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.moringa.ireporter.MainActivity;
import com.moringa.ireporter.R;

public class RedFlagActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_flag);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.Red);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Home:
                        startActivity(new Intent(getApplicationContext()
                                , MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.Red:
                        return true;

                    case R.id.Int:
                        startActivity(new Intent(getApplicationContext()
                                ,InterventionActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.overflow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.Home:
                Toast.makeText(this, "Home Selected", Toast.LENGTH_SHORT);
                return true;

            case R.id.Red:
                Toast.makeText(this, "Red-Flag Selected", Toast.LENGTH_SHORT);
                return true;

            case R.id.Int:
                Toast.makeText(this, "Intervention Selected", Toast.LENGTH_SHORT);
                return true;

            case R.id.CreateRed:
                startActivity(new Intent(getApplicationContext()
                        , CreateRedActivity.class));
                overridePendingTransition(0, 0);
                return true;

            case R.id.CreateInt:
                startActivity(new Intent(getApplicationContext()
                        , CreateIntActivity.class));
                overridePendingTransition(0, 0);
            default:
                return super.onOptionsItemSelected(item);


        }
    }
}