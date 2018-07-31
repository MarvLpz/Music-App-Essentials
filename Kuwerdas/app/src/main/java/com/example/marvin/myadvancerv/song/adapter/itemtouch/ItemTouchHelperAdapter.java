package com.example.marvin.myadvancerv.song.adapter.itemtouch;

import android.support.v7.widget.RecyclerView;

public interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(RecyclerView.ViewHolder viewHolder, RecyclerView recyclerView);
}