package com.example.marvin.myadvancerv.song.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.marvin.myadvancerv.R;
import com.example.marvin.myadvancerv.song.adapter.itemtouch.ItemTouchHelperViewHolder;

public class ChordItemViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
    TextView tv;
    LinearLayout linearLayout;
    public ChordItemViewHolder(View itemView) {
        super(itemView);
        tv = (TextView) itemView.findViewById(R.id.etLine);
        linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayoutChord);

    }

    @Override
    public void onItemSelected() {
        itemView.setBackgroundColor(Color.LTGRAY);


    }

    @Override
    public void onItemClear() {
        itemView.setBackgroundColor(0);

    }
}
