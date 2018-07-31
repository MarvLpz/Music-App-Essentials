package com.example.marvin.myadvancerv.song.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marvin.myadvancerv.R;
import com.example.marvin.myadvancerv.song.view.ChordCell;
import com.example.marvin.myadvancerv.song.model.Chord;
import com.example.marvin.myadvancerv.song.model.Line;
import com.example.marvin.myadvancerv.song.model.Verse;

import java.util.List;

public class VerseRecyclerViewAdapter extends RecyclerView.Adapter<VerseRecyclerViewHolder> {
    List<Line> verseLines;

    public VerseRecyclerViewAdapter(Verse data) {
        verseLines = data.getLines();
    }

    @Override
    public VerseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.line_layout, parent, false);
        return new VerseRecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VerseRecyclerViewHolder holder, int position) {
        Line line = verseLines.get(position);
        holder.getLineLyricsEditText().setText(line.getLyrics());

        //TODO fix chords
        Chord[] chordSet = line.getChordSet();
        for(int i=0;i<Line.CHORD_SET_LENGTH;i++) {
            holder.getGridLayout().addView(new ChordCell(holder.getItemView().getContext(),chordSet[i]));
        }
    }

    @Override
    public int getItemCount() {
        return verseLines.size();
    }
}
