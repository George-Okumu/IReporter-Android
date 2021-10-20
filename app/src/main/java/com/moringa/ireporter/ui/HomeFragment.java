package com.moringa.ireporter.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.moringa.ireporter.MainActivity;
import com.moringa.ireporter.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeFragment extends Fragment implements View.OnClickListener{
    @BindView(R.id.redFlagBtn)
    Button mRedFlagBtn;
    @BindView(R.id.intBtn) Button mIntBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        mRedFlagBtn.setOnClickListener(this);
        mIntBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == mRedFlagBtn) {
            Intent intent = new Intent(getActivity(), RedFlagCreate.class);
            getActivity().startActivity(intent);
        }
        if (view == mIntBtn) {
            Intent intent = new Intent(getActivity(), CreateIntActivity.class);
            getActivity().startActivity(intent);
        }

    }
}