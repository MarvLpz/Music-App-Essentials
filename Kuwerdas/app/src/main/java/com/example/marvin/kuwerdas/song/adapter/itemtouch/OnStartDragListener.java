package com.example.marvin.kuwerdas.song.adapter.itemtouch;

import android.support.v7.widget.RecyclerView;

public interface OnStartDragListener {

    void onStartDrag(RecyclerView.ViewHolder viewHolder);

    void onStartSwipe(RecyclerView.ViewHolder holder);
}