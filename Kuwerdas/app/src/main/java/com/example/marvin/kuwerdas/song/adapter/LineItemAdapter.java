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

        holder.getRvChords().setAlpha(SongFragment.isSongEditable() ? .5f : 1f);
        final EditText etLine = holder.getLineLyricsEditText();
        final Line line = verseLines.get(holder.getAdapterPosition());
        etLine.setText(line.getLyrics());
        etLine.setFocusable(SongFragment.isSongEditable() && SongFragment.mode.equals(SongFragment.SongEditMode.EDIT_MODE_LYRICS));
//        ((VerseItemViewHolder) holder).setFocusable(SongFragment.mode.equals(SongFragment.SongEditMode.EDIT));

        final int pos = holder.getAdapterPosition();
        holder.setChordVisibility(!SongFragment.isSongEditable() || SongFragment.isSongInChordMode());

        //TODO :::: OPTIONAL
//        if(!SongFragment.isSongEditable())
//            holder.setChordBackground(true);
//        else if(SongFragment.isSongInChordMode())
//            holder.setChordBackground(false);


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
                    if (currentPosition == verseLines.size() - 1) {

                        List<String> arr = new ArrayList<>();

                        for (int n = 0; n < etLine.getLayout().getLineCount(); n++) {
                            List<String> m = Arrays.asList(etLine.getText().subSequence(etLine.getLayout().getLineStart(n), etLine.getLayout().getLineEnd(n)).toString().split("\n"));
                            arr.addAll(m);
                        }

//                    List<String> arr = Arrays.asList(charSequence.toString().split("\n"));
//                    verseLines.set(currentPosition, new Line(arr.get(0)));
                        line.setLyrics(arr.get(0));

                        if (arr.size() > 1) {
                            for (int n = 1; n < arr.size(); n++) {
                                String a = arr.get(n);
                                Log.d("CHORDES", "adding str: " + a);
                                verseLines.add(currentPosition + n, new Line(a));
                            }
                            Log.d("CHECKTHIS", "Display: " + verseLines);
                            notifyItemChanged(currentPosition);
                            notifyItemRangeInserted(currentPosition + 1, arr.size());

//                        notifyDataSetChanged();
//                        for (String a : arr.subList(1, arr.size())) {
//                            Log.d("CHORDES","adding str: " + a);
//                            verseLines.add(currentPosition + 1, new Line(a));
//                            notifyDataSetChanged();
//                        }
                            focusPosition = currentPosition + arr.size() - 1;
                        } else
                            focusPosition = -1;
                    }
                    else {
                        if(etLine.getText().toString().contains("\n")) {
                            line.setLyrics(etLine.getText().toString().replace("\n", ""));
                            notifyItemChanged(currentPosition);
                        }
                    }
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
