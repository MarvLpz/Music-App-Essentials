package com.example.marvin.myadvancerv.song.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marvin.myadvancerv.R;
import com.example.marvin.myadvancerv.song.adapter.itemtouch.ItemTouchHelperAdapter;
import com.example.marvin.myadvancerv.song.model.Verse;

import java.util.Collections;
import java.util.List;

public class VerseItemAdapter extends RecyclerView.Adapter<VerseItemViewHolder> implements ItemTouchHelperAdapter {
    private static final String TAG = "TAGGY";

    List<Verse> myVerses;

    public VerseItemAdapter(List<Verse> myVerses){
        this.myVerses = myVerses;
    }

    @Override
    public VerseItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.verse_layout, parent, false);
        return new VerseItemViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(VerseItemViewHolder holder, int position) {
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

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < myVerses.size() && toPosition < myVerses.size()) {
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(myVerses, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(myVerses, i, i - 1);
                }
            }
            notifyItemMoved(fromPosition, toPosition);
        }
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        myVerses.remove(position);
        notifyItemRemoved(position);
        Log.d("TAGGY","removed item");
    }
}

