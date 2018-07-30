package com.example.marvin.myadvancerv;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SongRecyclerViewHolder extends RecyclerView.ViewHolder {
    // define the View objects
    private RecyclerView recyclerView;

    public VerseRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    private VerseRecyclerViewAdapter adapter;

    public SongRecyclerViewHolder(View itemView) {
        super(itemView);
        // initialize the View objects
        recyclerView = (RecyclerView) itemView.findViewById(R.id.rvVerses);

    }

    public void setVerseLinesData(Verse data){
        adapter = new VerseRecyclerViewAdapter(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        recyclerView.setAdapter(adapter);
    }
}