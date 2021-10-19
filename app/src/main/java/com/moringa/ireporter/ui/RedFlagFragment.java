package com.moringa.ireporter.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moringa.ireporter.R;
import com.moringa.ireporter.adapters.RedFlagAdapter;
import com.moringa.ireporter.models.RedFlag;
import com.moringa.ireporter.network.IreporterApi;
import com.moringa.ireporter.network.IreporterClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class RedFlagFragment extends Fragment implements  View.OnClickListener{
    private List<RedFlag> mRedFlags = new ArrayList<>();
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    RedFlagAdapter mAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_red_flag, container, false);
        ButterKnife.bind(this, view);

        // Fetch redflags
        // Initialize recyler view
        mAdapter = new RedFlagAdapter(getContext(), mRedFlags);
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setVisibility(View.VISIBLE);
        redflagRes();

        return view;
    }

    private void redflagRes() {
        Retrofit retrofit = IreporterClient.getRetrofit();
        IreporterApi ireporterApi = retrofit.create(IreporterApi.class);
        Call<List<RedFlag>> call = ireporterApi.getRedFlags();
        call.enqueue(new Callback<List<RedFlag>>() {
            @Override
            public void onResponse(Call<List<RedFlag>> call, Response<List<RedFlag>> response) {
                if (response.isSuccessful()) {
                    for (RedFlag redFlag: response.body() ) {
                        mRedFlags.add(redFlag);
                    }
                    //mRedFlags = response.body();
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<RedFlag>> call, Throwable t) {

            }
        });

    }

    @Override
    public void onClick(View view) {

    }
}