package com.example.marvin.myadvancerv.song.adapter.itemtouch;

public interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}