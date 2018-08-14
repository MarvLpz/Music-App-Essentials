package com.example.marvin.kuwerdas.song.view;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.marvin.kuwerdas.song.model.Chord;

public class ChordCell extends android.support.v7.widget.AppCompatEditText {

    private Chord chord;
    private Context context;

    public ChordCell(@NonNull Context context) {
        super(context);
        this.context = context;
        chord = null;

        init();
    }

    public ChordCell(@NonNull Context context, Chord chord) {
        super(context);
        this.context = context;
        this.chord = chord;

        init();
    }

    private void init(){
//        setBackgroundResource(android.R.color.transparent);
//        addChord(chord);
    }

    public boolean addChord(Chord chord){
        if(chord == null) {
            setText("");
            return false;
        }
        else if(this.chord == null){
            this.chord = chord;
            setText(chord.getChord());
            return true;
        }
        return false;
    }

    public void clear(){
        chord = null;
        setText("");
    }
}
