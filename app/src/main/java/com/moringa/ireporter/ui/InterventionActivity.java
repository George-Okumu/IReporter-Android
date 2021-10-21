package com.moringa.ireporter.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.moringa.ireporter.MainActivity;
import com.moringa.ireporter.R;
import com.moringa.ireporter.adapters.InterventionAdapter;
import com.moringa.ireporter.models.Intervention;
import com.moringa.ireporter.network.IreporterApiInt;
import com.moringa.ireporter.network.IreporterClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InterventionActivity extends AppCompatActivity {
    private List<Intervention> mIntervention = new ArrayList<>();
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    InterventionAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intervention);
        ButterKnife.bind(this);


        // Fetch intervention
        // Initialize recyler view
        mAdapter = new InterventionAdapter(InterventionActivity.this, mIntervention);
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(InterventionActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setVisibility(View.VISIBLE);
        interventionRes();

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.Int);

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
                        startActivity(new Intent(getApplicationContext()
                                , RedFlagActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.Int:
                        return true;
                }
                return false;
            }
        });
    }

    private void interventionRes() {
        Retrofit retrofit = IreporterClient.getRetrofit();
        IreporterApiInt ireporterApi = retrofit.create(IreporterApiInt.class);
        Call<List<Intervention>> call = ireporterApi.getIntervention();
        call.enqueue(new Callback<List<Intervention>>() {
            @Override
            public void onResponse(Call<List<Intervention>> call, Response<List<Intervention>> response) {
                if (response.isSuccessful()) {
                    for (Intervention intervention: response.body() ) {
                        mIntervention.add(intervention);
                    }
                    //mRedFlags = response.body();
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Intervention>> call, Throwable t) {

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
}