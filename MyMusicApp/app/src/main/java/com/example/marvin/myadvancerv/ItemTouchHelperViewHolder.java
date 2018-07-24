package com.example.marvin.myadvancerv;
import android.support.v7.widget.helper.ItemTouchHelper;
public interface ItemTouchHelperViewHolder {

    void onItemSelected();

    /**
     * Called when the {@link ItemTouchHelper} has completed the move or swipe, and the active item
     * state should be cleared.
     */
    void onItemClear();
}
