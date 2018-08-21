package com.example.marvin.kuwerdas.song.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.EditText;

import com.example.marvin.kuwerdas.R;
import com.example.marvin.kuwerdas.song.adapter.itemtouch.ChordItemTouchHelperCallback;
import com.example.marvin.kuwerdas.song.model.Chord;
import com.google.android.flexbox.AlignContent;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.List;

public class LineItemViewHolder extends RecyclerView.ViewHolder {
    // define the View objects
//    private GridLayout gridLayout;
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
//        gridLayout = (GridLayout) itemView.findViewById(R.id.glChords);
        lineLyricsEditText = (EditText) itemView.findViewById(R.id.etLine);
        rvChords = (RecyclerView) itemView.findViewById(R.id.rvChords);
    }

//    public GridLayout getGridLayout() {
//        return gridLayout;
//    }

    public void setChordList( List<Chord> chordList){
//        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(itemView.getContext());
//        layoutManager.setJustifyContent(JustifyContent.);
//        layoutManager.setFlexWrap(FlexWrap.NOWRAP);
//        layoutManager.setAlignItems(AlignItems.STRETCH);
//        rvChords.setLayoutManager(layoutManager);
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