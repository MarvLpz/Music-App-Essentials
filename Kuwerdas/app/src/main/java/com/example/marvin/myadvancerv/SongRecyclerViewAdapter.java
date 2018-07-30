package com.example.marvin.myadvancerv;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marvin.myadvancerv.R;
import com.example.marvin.myadvancerv.SongRecyclerViewHolder;

import java.util.List;

public class SongRecyclerViewAdapter extends RecyclerView.Adapter<SongRecyclerViewHolder>{
    private static final String TAG = "TAGGY";

    List<Verse> myVerses;

    public SongRecyclerViewAdapter(List<Verse> myVerses){
        this.myVerses = myVerses;
    }

    @Override
    public SongRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.verse_layout, parent, false);
        return new SongRecyclerViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(SongRecyclerViewHolder holder, int position) {
        holder.setVerseLinesData(myVerses.get(position));
//        Tempo tempo = tempoList.get(position);
//        Log.d(TAG,"onBindViewHolder pos " + position + ": " + tempo);
//        holder.title.setText(String.valueOf(tempo.getTempo()));
    }

    @Override
    public int getItemCount() {
        return myVerses.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}

