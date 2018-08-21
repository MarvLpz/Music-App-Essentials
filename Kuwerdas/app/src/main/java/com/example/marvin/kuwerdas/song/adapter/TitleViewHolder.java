package com.example.marvin.kuwerdas.song.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.marvin.kuwerdas.R;


public class TitleViewHolder extends RecyclerView.ViewHolder{
    private View itemView;
    private TextView title;
    private TextView artist;
    private TextView tempo;
    private TextView key;

    public TitleViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;

        title = itemView.findViewById(R.id.tvTitle);
        artist = itemView.findViewById(R.id.tvArtist);
        tempo = itemView.findViewById(R.id.tvTempo);
        key = itemView.findViewById(R.id.tvTranspose);
    }

    public void setTitle(String t){
        title.setText(t);
    }

    public void setArtist(String a){
        artist.setText(a);
    }

    public void setTempo(int t){
        tempo.setText("Tempo: " + t + " bpm");
    }

    public void setKey(int k){
        key.setText(k + "");
    }
}