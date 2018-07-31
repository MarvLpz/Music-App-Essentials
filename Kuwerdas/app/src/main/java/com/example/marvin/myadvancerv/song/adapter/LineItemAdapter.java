package com.example.marvin.myadvancerv.song.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.marvin.myadvancerv.MyList;
import com.example.marvin.myadvancerv.R;
import com.example.marvin.myadvancerv.song.model.Chord;
import com.example.marvin.myadvancerv.song.model.Line;
import com.example.marvin.myadvancerv.song.model.Verse;
import com.example.marvin.myadvancerv.song.view.ChordCell;

import java.util.Arrays;
import java.util.List;

public class LineItemAdapter extends RecyclerView.Adapter<LineItemViewHolder> {
    List<Line> verseLines;
    private boolean onBind;

    private int focusPosition;
    private int currentPosition;

    public LineItemAdapter(Verse data) {
        verseLines = data.getLines();
    }

    @Override
    public LineItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.line_layout, parent, false);
        final EditText etLine = itemView.findViewById(R.id.etLine);
        etLine.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                /*String[] arr = charSequence.toString().split("\n\n");
                for ( String ss : arr) {
                    myData.add (new MyList(ss));
                }*/
                if (!onBind) {

                    List<String> arr = Arrays.asList(charSequence.toString().split("\n\n"));
                    verseLines.set(currentPosition,new Line(arr.get(0)));

                    if (arr.size() > 1) {
//                        int position = myData.get().Item;
                        for (String a : arr.subList(1, arr.size())) {
                            verseLines.add(currentPosition +1, new Line(a));
                            notifyDataSetChanged();
                        }
                        focusPosition = currentPosition + arr.size() - 1;
                    } else
                        focusPosition = -1;
                }
            }



            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return new LineItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LineItemViewHolder holder, final int position) {
        onBind = true;

        Line line = verseLines.get(position);
        holder.getLineLyricsEditText().setText(line.getLyrics());

        holder.getLineLyricsEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                if(focused){
                    currentPosition = position;
                }
            }
        });

        if(position == focusPosition){
            Log.d("TAGGY","position was focused " + focusPosition);
            holder.getLineLyricsEditText().requestFocus();
        }

        //TODO fix chords
        Chord[] chordSet = line.getChordSet();
        for(int i=0;i<Line.CHORD_SET_LENGTH;i++) {
            holder.getGridLayout().addView(new ChordCell(holder.getItemView().getContext(),chordSet[i]));
        }

        onBind = false;

    }

    @Override
    public int getItemCount() {
        return verseLines.size();
    }
}
