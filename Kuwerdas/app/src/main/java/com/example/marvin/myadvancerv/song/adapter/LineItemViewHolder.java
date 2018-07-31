package com.example.marvin.myadvancerv.song.adapter;

import android.support.v7.widget.GridLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.example.marvin.myadvancerv.R;

public class LineItemViewHolder extends RecyclerView.ViewHolder {
    // define the View objects
    private GridLayout gridLayout;
    private EditText lineLyricsEditText;
    private View itemView;

    public LineItemViewHolder(View itemView) {
        super(itemView);
        // initialize the View objects
        this.itemView = itemView;
        gridLayout = (GridLayout) itemView.findViewById(R.id.glChords);
        lineLyricsEditText = (EditText) itemView.findViewById(R.id.etLine);
    }

    public GridLayout getGridLayout() {
        return gridLayout;
    }

    public EditText getLineLyricsEditText() {
        return lineLyricsEditText;
    }

    public View getItemView() {
        return itemView;
    }
}