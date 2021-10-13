package com.moringa.ireporter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.moringa.ireporter.adapters.RedFlagAdapter;
import com.moringa.ireporter.models.RedFlag;
import com.moringa.ireporter.ui.HomeFragment;
import com.moringa.ireporter.ui.InterventionFragment;
import com.moringa.ireporter.ui.RedFlagFragment;

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

    private BottomNavigationView.OnNavigationItemReselectedListener navListener = new
            BottomNavigationView.OnNavigationItemReselectedListener() {
                @Override
                public void onNavigationItemReselected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.item1:
                            selectedFragment = new HomeFragment();
                            break;

                        case R.id.item2:
                            selectedFragment = new RedFlagFragment();
                            break;

                        case R.id.item3:
                            selectedFragment = new InterventionFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.recyclerView
                                    ,selectedFragment).commit();

                }
            };
}