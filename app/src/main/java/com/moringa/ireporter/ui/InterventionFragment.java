package com.moringa.ireporter.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moringa.ireporter.R;
import com.moringa.ireporter.adapters.InterventionAdapter;
import com.moringa.ireporter.adapters.RedFlagAdapter;
import com.moringa.ireporter.models.Intervention;
import com.moringa.ireporter.models.RedFlag;
import com.moringa.ireporter.network.IreporterApi;
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


public class InterventionFragment extends Fragment implements  View.OnClickListener{

    private List<Intervention> mIntervention = new ArrayList<>();
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    InterventionAdapter mAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_intervention, container, false);
        ButterKnife.bind(this, view);

        // Fetch intervention
        // Initialize recyler view
        mAdapter = new InterventionAdapter(getContext(), mIntervention);
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setVisibility(View.VISIBLE);
        interventionRes();

        return view;
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
    public void onClick(View view) {

    }
}