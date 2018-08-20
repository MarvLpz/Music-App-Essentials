package com.example.marvin.kuwerdas.search.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.marvin.kuwerdas.MainActivity;
import com.example.marvin.kuwerdas.OnChangeFragment;
import com.example.marvin.kuwerdas.R;
import com.example.marvin.kuwerdas.song.util.SongUtil;


public class HeaderViewHolder extends RecyclerView.ViewHolder{
//    private CardView cardView;
    private View itemView;
    private TextView textView;

    public HeaderViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
//        cardView = itemView.findViewById(R.id.cvNewSong);
        textView = itemView.findViewById(R.id.tvNewSong);
    }

    public void setOnClickListener(View.OnClickListener listener){
        itemView.setOnClickListener(listener);
    }

    public void setText(String text){
        textView.setText(text);
    }

}