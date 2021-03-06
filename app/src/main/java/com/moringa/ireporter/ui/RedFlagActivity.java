package com.moringa.ireporter.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.moringa.ireporter.MainActivity;
import com.moringa.ireporter.R;
import com.moringa.ireporter.adapters.RedFlagAdapter;
import com.moringa.ireporter.models.RedFlag;
import com.moringa.ireporter.network.ApiCalls;
import com.moringa.ireporter.network.IreporterApi;
import com.moringa.ireporter.network.IreporterClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RedFlagActivity extends AppCompatActivity {
    private List<RedFlag> mRedFlags = null;
    RedFlagAdapter mAdapter;
    private Handler mHandler = new Handler();

    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_flag);
        ButterKnife.bind(this);

        setupRecycerView();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.Red);
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
    protected void onResume() {
        super.onResume();
        setupRecycerView();
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
                        , RedFlagCreate.class));
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

    private void setupRecycerView() {
        Retrofit retrofit = IreporterClient.getRetrofit();
        IreporterApi api = retrofit.create(IreporterApi.class);

        Call<List<RedFlag>> call = api.getRedFlags();
        call.enqueue(new Callback<List<RedFlag>>() {
            @Override
            public void onResponse(Call<List<RedFlag>> call, Response<List<RedFlag>> response) {
                if (response.isSuccessful()) {
                    mRedFlags = response.body();
                    mAdapter = new RedFlagAdapter(RedFlagActivity.this, mRedFlags);
                    mRecyclerView.setAdapter(mAdapter);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RedFlagActivity.this);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(Call<List<RedFlag>> call, Throwable t) {
            }
        });
    }



}