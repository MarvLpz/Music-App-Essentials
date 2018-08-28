package com.example.marvin.kuwerdas.song.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.marvin.kuwerdas.R;
import com.example.marvin.kuwerdas.song.SongFragment;
import com.example.marvin.kuwerdas.song.adapter.itemtouch.ItemClickCallback;
import com.example.marvin.kuwerdas.song.model.Chord;
import com.example.marvin.kuwerdas.song.model.Line;

import java.util.ArrayList;
import java.util.List;

public class ChordItemAdapter extends RecyclerView.Adapter<ChordItemViewHolder>{
    Context con;
    private List<Chord> myChord;
    private ItemClickCallback itemClickCallback;

    float dX;
    float dY;
    int lastAction;

    TextView testview;
    static String DragChord;
    static boolean delClicked;
    public ChordItemAdapter(Context context, List<Chord> _myChord ){
        con = context;
        myChord = _myChord;
    }

    public ChordItemAdapter(){

    }


    public void getChord(String Chord){
        DragChord = Chord;
    }

    public static void getTriggerDelBtn(boolean d){
        delClicked = d;
    }

    private class ChoiceDragListener implements View.OnDragListener{
        int positionChord;
        @Override
        public boolean onDrag( View v, DragEvent event) {

            switch (event.getAction()){
                case DragEvent.ACTION_DRAG_STARTED:
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    positionChord = (int) v.getTag();
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    myChord.get(positionChord).setChord(DragChord);
                    SongFragment.isSongEdited = true;
//                    ((TextView)v).setText(String.valueOf(DragChord));

//                    notifyDataSetChanged();
                    notifyItemChanged(positionChord);

                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    break;
            }

            return true;
        }
    }


    public void setItemClickCallback(final ItemClickCallback itemClickCallback){
        this.itemClickCallback = itemClickCallback;
    }

    private class ChoiceClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if (delClicked){
                //TODO fix tag
                myChord.get((int) v.getTag() ).setChord(Chord.EMPTY_CHORD);
                SongFragment.isSongEdited = true;
                notifyItemChanged((int)v.getTag());

            }
        }
    }

    @Override
    public ChordItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(con);
        view = inflater.inflate(R.layout.chord_layout,parent,false);
        TextView  ChoiceTextView = (TextView) view.findViewById(R.id.etChord);
        ChoiceTextView.setOnClickListener(new ChoiceClickListener());
        ChoiceTextView.setOnDragListener(new ChoiceDragListener());

        return new ChordItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChordItemViewHolder holder, int position) {
        if(myChord != null) {
            holder.tv.setText(myChord.get(position).getChord());
            holder.tv.setTag(position);
            holder.setColor(SongFragment.isInDeleteMode ? Color.RED : Color.DKGRAY);
        }

    }

    @Override
    public int getItemCount() {
        return Line.CHORD_SET_LENGTH;
    }
}
