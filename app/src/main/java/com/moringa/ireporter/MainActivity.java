package com.moringa.ireporter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.moringa.ireporter.adapters.PagerAdapter;
import com.moringa.ireporter.adapters.RedFlagAdapter;
import com.moringa.ireporter.models.RedFlag;
import com.moringa.ireporter.network.IreporterApi;
import com.moringa.ireporter.network.IreporterClient;
import com.moringa.ireporter.ui.CreateIntActivity;
import com.moringa.ireporter.ui.CreateRedActivity;
import com.moringa.ireporter.ui.InterventionActivity;
import com.moringa.ireporter.ui.LoginActivity;
import com.moringa.ireporter.ui.RedFlagActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
//    private List<RedFlag> mRedFlags = new ArrayList<>();
//    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
//    RedFlagAdapter mAdapter;


    TabLayout tabLayout;
    TabItem mHome,mRedFlagg,mIntervention;
    PagerAdapter pagerAdapter;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        // Fetch redflags
        // Initialize recyler view
//        mAdapter = new RedFlagAdapter(MainActivity.this, mRedFlags);
//        mRecyclerView.setAdapter(mAdapter);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
//        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setVisibility(View.VISIBLE);
//        redflagRes();




//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
//        bottomNavigationView.setSelectedItemId(R.id.Home);
//
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.Home:
//                        return true;
//
//                    case R.id.Red:
//                        startActivity(new Intent(getApplicationContext()
//                                ,RedFlagActivity.class));
//                        overridePendingTransition(0, 0);
//                        return true;
//
//                    case R.id.Int:
//                        startActivity(new Intent(getApplicationContext()
//                                ,InterventionActivity.class));
//                        overridePendingTransition(0, 0);
//                        return true;
//                }
//                return false;
//            }
//        });


//        mToolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);

        mHome = findViewById(R.id.home);
        mRedFlagg = findViewById(R.id.redflagg);
        mIntervention = findViewById(R.id.inter);


        ViewPager viewPager = findViewById(R.id.fragment_container);
        tabLayout = findViewById(R.id.include);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), 3);
        viewPager.setAdapter(pagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0
                        || tab.getPosition() == 1
                        || tab.getPosition() == 2);
                {
                    pagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

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

    @Override
    public void onClick(View view) {

    }


//    private void redflagRes() {
//        Retrofit retrofit = IreporterClient.getRetrofit();
//        IreporterApi ireporterApi = retrofit.create(IreporterApi.class);
//        Call<List<RedFlag>> call = ireporterApi.getRedFlags();
//        call.enqueue(new Callback<List<RedFlag>>() {
//            @Override
//            public void onResponse(Call<List<RedFlag>> call, Response<List<RedFlag>> response) {
//                if (response.isSuccessful()) {
//                    for (RedFlag redFlag: response.body() ) {
//                        mRedFlags.add(redFlag);
//                    }
//                    //mRedFlags = response.body();
//                    mAdapter.notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<RedFlag>> call, Throwable t) {
//
//            }
//        });

//    }

}