package com.example.marvin.kuwerdas.song.adapter.itemtouch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.marvin.kuwerdas.MainActivity;
import com.example.marvin.kuwerdas.song.SongFragment;
import com.example.marvin.kuwerdas.song.adapter.ChordItemAdapter;
import com.example.marvin.kuwerdas.song.model.Chord;

import java.util.List;

public class ChordItemTouchHelperCallback {
    Context con;
    private List<Chord> myChord;
    ChordItemAdapter adapter2;
    int minId, maxId;

    public ChordItemTouchHelperCallback(Context context, List<Chord> _myChord, ChordItemAdapter _adapter2){
        con = context;
        myChord = _myChord;
        adapter2 = _adapter2;
        if(myChord!=null)
        {
            minId = myChord.get(0).getId();
            maxId = myChord.get(myChord.size()-1).getId();
        }
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
                    public boolean isLongPressDragEnabled() {
                       return SongFragment.isSongEditable()
                               && SongFragment.mode == SongFragment.SongEditMode.EDIT_MODE_CHORD_BAR;
                    }

                    @Override
                    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                        // Set movement flags based on the layout manager
                        final int dragFlags = 0 | 0| ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                        final int swipeFlags = 0;
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
        Chord item = (Chord) myChord.get(oldPos);

        if(item.getChord().equals(Chord.EMPTY_CHORD) || SongFragment.mode.equals(SongFragment.SongEditMode.READ_ONLY))
            return;
        myChord.remove(oldPos);
        myChord.add(newPos,item);
        adapter2.notifyItemMoved(oldPos,newPos);
        SongFragment.isSongEdited = true;
        /*editText2 = (EditText) findViewById(R.id.fl_tv_id);
        editText2.setFocusable(false);
        editText2.setFocusable(true);

        */

    }

    private void deleteItem(final int position) {
        myChord.remove(position);
        adapter2.notifyItemRemoved(position);
    }
}
