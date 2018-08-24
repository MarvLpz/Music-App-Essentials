package com.example.marvin.kuwerdas.song.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.marvin.kuwerdas.R;
import com.example.marvin.kuwerdas.db.SongDatabaseUtils;
import com.example.marvin.kuwerdas.song.SongFragment;
import com.example.marvin.kuwerdas.song.model.Line;
import com.example.marvin.kuwerdas.song.model.Verse;

import java.util.ArrayList;
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
        return new LineItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LineItemViewHolder holder, final int position) {
        onBind = true;

        holder.getRvChords().setAlpha(SongFragment.mode.equals(SongFragment.SongEditMode.EDIT) ? .5f : 1f);
        final EditText etLine = holder.getLineLyricsEditText();
        final Line line = verseLines.get(holder.getAdapterPosition());
        etLine.setText(line.getLyrics());
        etLine.setFocusable(SongFragment.mode.equals(SongFragment.SongEditMode.EDIT));
//        ((VerseItemViewHolder) holder).setFocusable(SongFragment.mode.equals(SongFragment.SongEditMode.EDIT));

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
                    if (i == KeyEvent.KEYCODE_DEL && currentPosition == pos) {
                        //Perform action for backspace
                        int cursorPosition = etLine.getSelectionStart();
                        if (cursorPosition == 0) {
                            if(currentPosition>0){
                                String lineLyrics = verseLines.get(currentPosition).getLyrics();

                                verseLines.get(currentPosition-1).setLyrics(verseLines.get(currentPosition - 1).getLyrics() + " " + lineLyrics);
                                new SongDatabaseUtils.DeleteLineFromDatabaseTask(verseLines.get(currentPosition)).execute();
                                verseLines.remove(currentPosition);

                                notifyDataSetChanged();
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

                    List<String> arr = new ArrayList<>();

                    for(int n=0;n<etLine.getLayout().getLineCount();n++){

                        List<String> m = Arrays.asList(etLine.getText().subSequence(etLine.getLayout().getLineStart(n),etLine.getLayout().getLineEnd(n)).toString().split("\n"));
                        Log.d("CHORDES","text change: " + m);
                        arr.addAll(m);
                        Log.d("CHORDES","arr change: " + arr);
                    }
//                    List<String> arr = Arrays.asList(charSequence.toString().split("\n"));
//                    verseLines.set(currentPosition, new Line(arr.get(0)));
                    Log.d("CHORDESS","verseLines before: " + verseLines);
                    Log.d("CHORDESS","arr before: " + arr);
                    line.setLyrics(arr.get(0));

                    if (arr.size() >= 1) {
                        for(int n=1;n<arr.size();n++){
                            String a = arr.get(n);
                            Log.d("CHORDES","adding str: " + a);
                            verseLines.add(currentPosition + n, new Line(a));
                        }
                        Log.d("CHORDESS","verseLines after: " + verseLines);


//                        notifyItemChanged(currentPosition);
//                        notifyItemRangeInserted(currentPosition+1,arr.size());

                        notifyDataSetChanged();
//                        for (String a : arr.subList(1, arr.size())) {
//                            Log.d("CHORDES","adding str: " + a);
//                            verseLines.add(currentPosition + 1, new Line(a));
//                            notifyDataSetChanged();
//                        }
                        focusPosition = currentPosition + arr.size() - 1;
                    } else
                        focusPosition = -1;
                }
            }


            @Override
            public void afterTextChanged(Editable editable) {
                SongFragment.isSongEdited = true;
            }
        });


        if(position == focusPosition){
            holder.getLineLyricsEditText().requestFocus();
        }

        holder.setChordList(line.getChordSet());
        onBind = false;

    }

    @Override
    public int getItemCount() {
        return verseLines.size();
    }
}
