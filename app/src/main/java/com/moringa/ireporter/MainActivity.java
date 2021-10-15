package com.moringa.ireporter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.moringa.ireporter.adapters.RedFlagAdapter;
import com.moringa.ireporter.models.RedFlag;
import com.moringa.ireporter.ui.CreateIntActivity;
import com.moringa.ireporter.ui.CreateRedActivity;
import com.moringa.ireporter.ui.InterventionActivity;
import com.moringa.ireporter.ui.LoginActivity;
import com.moringa.ireporter.ui.RedFlagActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private List<RedFlag> mRedFlags = new ArrayList<>();
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    RedFlagAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Fetch redflags
        mRedFlags.add(new RedFlag("Corruption in Homabay","Officer1 taking bribe","Homabay"));
        mRedFlags.add(new RedFlag("Corruption in Kisumu County","Officer2 taking bribe","Kisumu",R.drawable.corruption2));

        // Initialize recyler view
        mAdapter = new RedFlagAdapter(MainActivity.this, mRedFlags);
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setVisibility(View.VISIBLE);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.Home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Home:
                        return true;

                    case R.id.Red:
                        startActivity(new Intent(getApplicationContext()
                                ,RedFlagActivity.class));
                        overridePendingTransition(0, 0);
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


        //Bottom Nav
//        BottomNavigationView btnNav = findViewById(R.id.bottomNavigationview);
//        btnNav.setOnNavigationItemReselectedListener(navListener);
//
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragment_layout, new HomeFragment()).commit();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.overflow, menu);
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_logout:
                SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFS,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Constants.USER_TOKEN,"");
                editor.apply();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
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