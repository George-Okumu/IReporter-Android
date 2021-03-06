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
import com.moringa.ireporter.models.RedFlag;
import com.moringa.ireporter.ui.GeoCodingLocation;
import com.moringa.ireporter.ui.LocationActivity;
import com.moringa.ireporter.ui.RedFlagItemDetail;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RedFlagAdapter extends RecyclerView.Adapter<RedFlagAdapter.RedFlagViewHolder> {
    private List<RedFlag> mRedFlags;
    private Context mContext;
    public RedFlagAdapter(Context Context , List<RedFlag> redFlags){
        this.mRedFlags = redFlags;
        this.mContext = Context;
    }
    @NonNull
    @Override
    public RedFlagViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.red_flag_item,parent,false);
        RedFlagViewHolder viewHolder = new RedFlagViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RedFlagAdapter.RedFlagViewHolder holder, int position) {
        holder.bindRedFlag(mRedFlags.get(position));
    }

    @Override
    public int getItemCount() {
        return mRedFlags.size();
    }

    public  class RedFlagViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.imageRed) ImageView mImageRed;
        @BindView(R.id.descriptionRed)TextView mDescriptionRed;
        @BindView(R.id.subjectRed) TextView mSubjectRed;
        @BindView(R.id.locationRed) TextView mLocationRed;


        private Context mContext;

        public RedFlagViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        public void bindRedFlag(RedFlag redFlag){
            mSubjectRed.setText(redFlag.getTitle());
            mDescriptionRed.setText(redFlag.getDescription());
            mLocationRed.setText(redFlag.getLocation());


            // Add image
            Picasso.get().load(redFlag.getImageUrl())
                    .fit()
                    .centerCrop()
                    .into(mImageRed);
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, RedFlagItemDetail.class);
            //intent.putExtra("position", itemPosition);
            intent.putExtra("redflag", Parcels.wrap(mRedFlags.get(itemPosition)));
            mContext.startActivity(intent);

        }

    }
}
