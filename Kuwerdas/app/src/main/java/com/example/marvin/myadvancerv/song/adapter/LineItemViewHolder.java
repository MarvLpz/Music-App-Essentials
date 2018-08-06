package com.example.marvin.myadvancerv.song.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.EditText;

import com.example.marvin.myadvancerv.R;
import com.example.marvin.myadvancerv.song.adapter.itemtouch.ChordItemTouchHelperCallback;
import com.example.marvin.myadvancerv.song.model.Chord;

import java.util.List;

public class LineItemViewHolder extends RecyclerView.ViewHolder {
    // define the View objects
    private RecyclerView rvChords;
    private EditText lineLyricsEditText;
    private View itemView;
    private ChordItemTouchHelperCallback itemTouchHelperCallback;
    private ItemTouchHelper itemTouchHelper;
    private ChordItemAdapter adapter;


    public LineItemViewHolder(View itemView) {
        super(itemView);
        // initialize the View objects
        this.itemView = itemView;
        rvChords = (RecyclerView) itemView.findViewById(R.id.rvChords);
        lineLyricsEditText = (EditText) itemView.findViewById(R.id.etLine);
    }

    public void setChordList( List<Chord> chordList){
        adapter = new ChordItemAdapter(itemView.getContext(),chordList);
        rvChords.setLayoutManager(new LinearLayoutManager(itemView.getContext(),LinearLayoutManager.HORIZONTAL,false));
        itemTouchHelperCallback = new ChordItemTouchHelperCallback(itemView.getContext(),chordList,adapter);
        itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback.createHelperCallback());
        itemTouchHelper.attachToRecyclerView(rvChords);
        rvChords.setAdapter(adapter);
    }

    public RecyclerView getRvChords() {
        return rvChords;
    }

    public EditText getLineLyricsEditText() {
        return lineLyricsEditText;
    }

    public View getItemView() {
        return itemView;
    }


}