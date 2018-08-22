package com.example.marvin.kuwerdas.song.adapter.itemtouch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.example.marvin.kuwerdas.song.SongFragment;
import com.example.marvin.kuwerdas.song.adapter.ChordItemAdapter;
import com.example.marvin.kuwerdas.song.adapter.VerseItemAdapter;
import com.example.marvin.kuwerdas.song.model.Chord;
import com.example.marvin.kuwerdas.song.model.Verse;

import java.util.List;

public class VerseItemTouchHelperCallback {
    Context con;
    private List<Verse> myVerse;
    VerseItemAdapter adapter2;
    int minId, maxId;

    public VerseItemTouchHelperCallback(Context context, VerseItemAdapter _adapter2){
/*        con = context;
        myVerse = _myVerse;*/
        myVerse =_adapter2.getItems();
        con = context;
        adapter2 = _adapter2;
    }

    public static final float ALPHA_FULL = 1.0f;
    public ItemTouchHelper.Callback createHelperCallback() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                /*new ItemTouchHelper.SimpleCallback(0|0,
                        ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT)*/
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP| ItemTouchHelper.DOWN,
                        ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT)
                {

                    @Override
                    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                        // Set movement flags based on the layout manager
                        final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                        final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                        return makeMovementFlags(dragFlags, swipeFlags);
                    }
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        moveItem(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                        return true;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        deleteItem(viewHolder.getAdapterPosition());
                    }

                    @Override
                    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                        // We only want the active item to change

                        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                            if (viewHolder instanceof ItemTouchHelperViewHolder) {
                                // Let the view holder know that this item is being moved or dragged
                                ItemTouchHelperViewHolder itemViewHolder = (ItemTouchHelperViewHolder) viewHolder;
                                itemViewHolder.onItemSelected();
                                /* editText2 = (EditText) findViewById(R.id.fl_tv_id);
                                 editText2.setFocusable(false);*/
                            }
                        }
                        super.onSelectedChanged(viewHolder, actionState);
                    }

                    @Override
                    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                        super.clearView(recyclerView, viewHolder);

                        viewHolder.itemView.setAlpha(ALPHA_FULL);

                        if (viewHolder instanceof ItemTouchHelperViewHolder) {
                            // Tell the view holder it's time to restore the idle state
                            ItemTouchHelperViewHolder itemViewHolder = (ItemTouchHelperViewHolder) viewHolder;
                            itemViewHolder.onItemClear();
//                             editText2 = (EditText) findViewById(R.id.fl_tv_id);
//                             editText2.setFocusable(true);

                        }
                    }
                };
        return simpleItemTouchCallback;
    }

    private void moveItem(int oldPos,int newPos){
        Log.d("RETURNSIZE POS",String.valueOf(oldPos));
        Log.d("RETURNSIZE POS",String.valueOf(newPos));

        if (newPos == 0){
            Verse item = (Verse) myVerse.get(oldPos + 1);
            myVerse.remove(oldPos + 1);
            myVerse.add(newPos + 1,item);
            adapter2.notifyItemMoved(oldPos,newPos);
        }
        else if (newPos == myVerse.size()){
            Verse item = (Verse) myVerse.get(oldPos - 3);
            myVerse.remove(oldPos - 3);
            myVerse.add(newPos - 3,item);
            adapter2.notifyItemMoved(oldPos,newPos);
        }

        else{
            Verse item = (Verse) myVerse.get(oldPos - 1);
            myVerse.remove(oldPos - 1);
            myVerse.add(newPos - 1,item);
            adapter2.notifyItemMoved(oldPos,newPos);
        }

        SongFragment.isSongEdited = true;

        /*editText2 = (EditText) findViewById(R.id.fl_tv_id);
        editText2.setFocusable(false);
        editText2.setFocusable(true);

        */

    }

    private void deleteItem(final int position) {
        myVerse.remove(position-1);
        adapter2.notifyItemRemoved(position);
    }
}
