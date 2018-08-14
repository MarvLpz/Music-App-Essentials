package com.example.marvin.kuwerdas.search.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.marvin.kuwerdas.R;


public class SongItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private TextView tvTitle;
    private TextView tvArtist;
    private TextView tvDateModified;

    public SongItemViewHolder(View itemView) {
        super(itemView);
        tvTitle = (TextView) itemView.findViewById(R.id.tvSearchTitle);
        tvArtist = (TextView) itemView.findViewById(R.id.tvSearchArtist);
        tvDateModified = (TextView) itemView.findViewById(R.id.tvSearchDateModified);

        itemView.setOnClickListener(this);
    }

    public void setItemData(String title, String artist, String date){
        tvTitle.setText(title);
        tvArtist.setText(artist);
        tvDateModified.setText(date);
    }

    @Override
    public void onClick(View view) {
        SongItemAdapter.clickListener.recyclerViewListItemClicked(view, this.getLayoutPosition());
    }
}