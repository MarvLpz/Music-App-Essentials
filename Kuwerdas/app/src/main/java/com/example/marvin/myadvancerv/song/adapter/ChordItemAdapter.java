package com.example.marvin.myadvancerv.song.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.marvin.myadvancerv.R;
import com.example.marvin.myadvancerv.song.model.Chord;

import java.util.List;

public class ChordItemAdapter extends RecyclerView.Adapter<ChordItemViewHolder>{
    Context con;
    private List<Chord> myChord;
    private ItemClickCallback itemClickCallback;

    float dX;
    float dY;
    int lastAction;

    TextView testview;
    Character DragChord;


    private class ChoiceDragListener implements View.OnDragListener{
        int positionChord;
        @Override
        public boolean onDrag( View v, DragEvent event) {
            switch (event.getAction()){
                case DragEvent.ACTION_DRAG_STARTED:
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.d("DRAGEVENT", "Drag Entered");

                    Log.d("DRAG TAG", String.valueOf(v.getTag()));
                    positionChord = (int) v.getTag();
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.d("DRAGEVENT", "Drag Exited");
                    break;
                case DragEvent.ACTION_DROP:

                    Log.d("TESTING",String.valueOf(DragChord));

                    myChord.set(positionChord ,new Chord(DragChord));

                    ((TextView)v).setText(String.valueOf(DragChord));
//                    notifyDataSetChanged();

                    Log.d("DRAG","Drag to Place");
                    Log.d("DRAG",(String.valueOf((TextView)v)  ));
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    break;
            }

            return true;
        }
    }
    public interface ItemClickCallback{
        void onItemClick(int p);
        void onSecondaryIconClick(int p);
    }

    public void setItemClickCallback(final ItemClickCallback itemClickCallback){
        this.itemClickCallback = itemClickCallback;
    }

    public ChordItemAdapter(Context context, List<Chord> _myChord, Character _DragChord){
        con = context;
        myChord = _myChord;
        DragChord = _DragChord;
    }

    @Override
    public ChordItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(con);
        view = inflater.inflate(R.layout.chord_layout,parent,false);
        TextView  ChoiceTextView = (TextView) view.findViewById(R.id.etChord);
        ChoiceTextView.setOnDragListener(new ChoiceDragListener());


        return new ChordItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChordItemViewHolder holder, int position) {
        holder.tv.setText(myChord.get(position).getChord());
        holder.tv.setTag(position);
    }

    @Override
    public int getItemCount() {
        return 11;
    }

}
