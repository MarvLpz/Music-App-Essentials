package com.example.marvin.kuwerdas.song.adapter.itemtouch;

import android.support.v7.widget.RecyclerView;

public interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(RecyclerView.ViewHolder viewHolder);
}