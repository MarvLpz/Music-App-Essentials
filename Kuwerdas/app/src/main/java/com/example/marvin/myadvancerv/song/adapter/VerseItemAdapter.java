package com.example.marvin.myadvancerv.song.adapter;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marvin.myadvancerv.R;
import com.example.marvin.myadvancerv.song.adapter.itemtouch.ItemClickCallback;
import com.example.marvin.myadvancerv.song.adapter.itemtouch.ItemTouchHelperAdapter;
import com.example.marvin.myadvancerv.song.model.Chord;
import com.example.marvin.myadvancerv.song.model.Line;
import com.example.marvin.myadvancerv.song.model.Verse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VerseItemAdapter extends RecyclerView.Adapter<VerseItemViewHolder> implements ItemTouchHelperAdapter, ItemClickCallback {
    private static final String TAG = "TAGGY";

    List<Verse> mVerses;
    public static int getSize;
    List<Verse> versesToDelete;
    ArrayList<Chord> chordLists = new ArrayList<Chord>();
    public VerseItemAdapter(List<Verse> mVerses){
        this.mVerses = mVerses;
        versesToDelete = new ArrayList<>();
    }

    public VerseItemAdapter(){
    }

    @Override
    public VerseItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.verse_layout, parent, false);
        return new VerseItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VerseItemViewHolder holder, int position) {
        holder.setVerseLinesData(mVerses.get(position));
    }

    @Override
    public int getItemCount() {
        getSize = mVerses.size();
        return mVerses.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < mVerses.size() && toPosition < mVerses.size()) {
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(mVerses, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(mVerses, i, i - 1);
                }
            }
            notifyItemMoved(fromPosition, toPosition);
        }
        return true;
    }

    @Override
    public void onItemDismiss(final RecyclerView.ViewHolder viewHolder, final RecyclerView recyclerView) {
        final int adapterPosition = viewHolder.getAdapterPosition();
        final Verse mVerse = mVerses.get(adapterPosition);
        Snackbar snackbar = Snackbar
                .make(recyclerView, "Verse Deleted", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mVerses.add(adapterPosition, mVerse);
                        notifyItemInserted(adapterPosition);
                        versesToDelete.remove(mVerse);
                    }
                });
        snackbar.show();
        mVerses.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
        versesToDelete.add(mVerse);
    }

    @Override
    public void onItemClick(int p) {
        Chord chord = chordLists.get(p);
    }

    @Override
    public void onSecondaryIconClick(int p) {
        Chord chord = chordLists.get(p);
//        dataAdapter.notifyDataSetChanged();
    }


}

