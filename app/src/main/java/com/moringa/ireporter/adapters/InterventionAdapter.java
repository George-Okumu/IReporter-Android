package com.moringa.ireporter.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moringa.ireporter.R;
import com.moringa.ireporter.models.Intervention;
import com.moringa.ireporter.models.RedFlag;
import com.moringa.ireporter.ui.GeoCodingLocation;
import com.moringa.ireporter.ui.LocationActivity;
import com.moringa.ireporter.ui.RedFlagItemDetail;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InterventionAdapter extends RecyclerView.Adapter<InterventionAdapter.InterventionViewHolder> {
    private List<Intervention> mIntervention;
    private Context mContext;
    public InterventionAdapter(Context Context , List<Intervention> interventions){
        this.mIntervention = interventions;
        this.mContext = Context;
    }
    @NonNull
    @Override
    public InterventionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.intervention_item,parent,false);
        InterventionViewHolder viewHolder = new InterventionViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InterventionAdapter.InterventionViewHolder holder, int position) {
        holder.bindIntervention(mIntervention.get(position));
    }

    @Override
    public int getItemCount() {
        return mIntervention.size();
    }

    public  class InterventionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.imageInt) ImageView mImageInt;
        @BindView(R.id.descriptionInt)TextView mDescriptionInt;
        @BindView(R.id.subjectInt) TextView mSubjectInt;
        @BindView(R.id.locationInt) TextView mLocationInt;
        @BindView(R.id.attachGeo)
        Button mAttach;


        private Context mContext;

        public InterventionViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        public void bindIntervention(Intervention intervention){
            mSubjectInt.setText(intervention.getSubject());
            mDescriptionInt.setText(intervention.getDescription());
            mLocationInt.setText(intervention.getLocation());


            // Add image
            Picasso.get().load(intervention.getImageUrl())
                    .fit()
                    .centerCrop()
                    .into(mImageInt);
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, RedFlagItemDetail.class);
            intent.putExtra("position", itemPosition);
            intent.putExtra("intervention", Parcels.wrap(mIntervention.get(itemPosition)));
            mContext.startActivity(intent);
            if (v == mAttach){
                Intent intent1 = new Intent(mContext, LocationActivity.class);
                intent.putExtra("position", itemPosition);
                intent.putExtra("intervention", Parcels.wrap(mIntervention.get(itemPosition)));
                mContext.startActivity(intent1);
            }
        }

    }
}
