package com.example.marvin.myadvancerv.song.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;

import com.example.marvin.myadvancerv.MyList;
import com.example.marvin.myadvancerv.R;
import com.example.marvin.myadvancerv.song.model.Chord;
import com.example.marvin.myadvancerv.song.model.Line;
import com.example.marvin.myadvancerv.song.model.Verse;
import com.example.marvin.myadvancerv.song.view.ChordCell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LineItemAdapter extends RecyclerView.Adapter<LineItemViewHolder> {
    private List<Line> verseLines;
    private boolean onBind;

    private int focusPosition;
    private int currentPosition;
    private View itemView;

    public LineItemAdapter(Verse data) {
        verseLines = data.getLines();
    }

    @Override
    public LineItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.line_layout, parent, false);
        return new LineItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LineItemViewHolder holder, final int position) {
        onBind = true;

        final EditText etLine = holder.getLineLyricsEditText();
        Line line = verseLines.get(holder.getAdapterPosition());
        etLine.setText(line.getLyrics());

        final int pos = holder.getAdapterPosition();
        etLine.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                if(focused){
                    currentPosition = pos;
                    Log.d("TAGGY","focused on position: " + pos);
                    Log.d("TAGGY","verselines" + verseLines.size());
                }
            }
        });

        etLine.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (!onBind) {
                    Log.d("TAGGY","onKeyPressed: " + currentPosition + " - " + pos);
                    Log.d("CurrentPosition",String.valueOf(currentPosition));
                    if (i == KeyEvent.KEYCODE_DEL && currentPosition == pos) {
                        //Perform action for backspace
                        int cursorPosition = etLine.getSelectionStart();
                        if (cursorPosition == 0) {
                            if(currentPosition>0) {
                                String lineLyrics = verseLines.get(currentPosition).getLyrics();

                                verseLines.get(currentPosition-1).setLyrics(verseLines.get(currentPosition - 1).getLyrics() + " " + lineLyrics);
                                verseLines.remove(currentPosition);
                                notifyItemRemoved(currentPosition);
                                notifyDataSetChanged();
                                Log.d("CurrentPosition",String.valueOf(currentPosition) + " - "+focusPosition);
//                                notifyItemRemoved(currentPosition);
                            }
                        }
                    }
                }
                return false;
            }
        });
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
                    List<String> arr = Arrays.asList(charSequence.toString().split("\n"));
                    verseLines.set(currentPosition, new Line(arr.get(0)));

                    if (arr.size() > 1) {
                        for (String a : arr.subList(1, arr.size())) {
                            verseLines.add(currentPosition + 1, new Line(a));
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


        if(position == focusPosition){
            holder.getLineLyricsEditText().requestFocus();
        }

        //TODO fix chords
        holder.setChordList(line.getChordSet());
        onBind = false;

    }

    @Override
    public int getItemCount() {
        return verseLines.size();
    }
}