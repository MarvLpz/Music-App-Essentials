package com.example.marvin.myadvancerv.song.adapter;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.marvin.myadvancerv.R;
import com.example.marvin.myadvancerv.song.adapter.itemtouch.ItemTouchHelperViewHolder;
import com.example.marvin.myadvancerv.song.model.Verse;

public class VerseItemViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder{
    // define the View objects
    private RecyclerView recyclerView;
    private EditText textView;

    public LineItemAdapter getAdapter() {
        return adapter;
    }

    private LineItemAdapter adapter;

    public VerseItemViewHolder(View itemView) {
        super(itemView);
        // initialize the View objects
        recyclerView = (RecyclerView) itemView.findViewById(R.id.rvVerses);
        textView = (EditText) itemView.findViewById(R.id.etTitle);

    }

    public void setVerseLinesData(Verse data){
        adapter = new LineItemAdapter(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        recyclerView.setAdapter(adapter);
        textView.setText(data.getTitle());
    }

    @Override
    public void onItemSelected() {
        itemView.setBackgroundColor(Color.LTGRAY);
        recyclerView.clearFocus();
    }

    @Override
    public void onItemClear() {
        itemView.setBackgroundColor(0);
    }
}