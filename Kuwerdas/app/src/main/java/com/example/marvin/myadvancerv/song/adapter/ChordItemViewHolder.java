package com.example.marvin.myadvancerv.song.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.marvin.myadvancerv.R;
import com.example.marvin.myadvancerv.song.adapter.itemtouch.ItemTouchHelperViewHolder;
import com.example.marvin.myadvancerv.song.model.Chord;

public class
ChordItemViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder{
    TextView tv;
    LinearLayout linearLayout;

    public ChordItemViewHolder(View itemView) {
        super(itemView);
        tv = (TextView) itemView.findViewById(R.id.etChord);
        linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayoutChord);
    }

    public void getTV(TextView view) {
        tv = view;
    }

    public void setChord(String chord){
        tv.setText(chord);
    }

    @Override
    public void onItemSelected() {
        if (tv.getText().toString() == Chord.EMPTY_CHORD)
            return;
        else
            itemView.setBackgroundColor(Color.LTGRAY);
    }

    @Override
    public void onItemClear() {
        itemView.setBackgroundColor(0);
    }

}
