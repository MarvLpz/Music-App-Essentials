package com.example.marvin.myadvancerv.song.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.marvin.myadvancerv.R;
import com.example.marvin.myadvancerv.song.model.Verse;

public class VerseItemViewHolder extends RecyclerView.ViewHolder {
    // define the View objects
    private RecyclerView recyclerView;
    private TextView textView;

    public LineItemAdapter getAdapter() {
        return adapter;
    }

    private LineItemAdapter adapter;

    public VerseItemViewHolder(View itemView) {
        super(itemView);
        // initialize the View objects
        recyclerView = (RecyclerView) itemView.findViewById(R.id.rvVerses);
        textView = (TextView) itemView.findViewById(R.id.tvTitle);

    }

    public void setVerseLinesData(Verse data){
        adapter = new LineItemAdapter(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        recyclerView.setAdapter(adapter);
        textView.setText(data.getTitle());
    }
}