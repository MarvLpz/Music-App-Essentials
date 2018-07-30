package com.example.marvin.myadvancerv;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.EditText;
import android.widget.FrameLayout;

public class ChordCell extends FrameLayout {

    private Chord chord;
    private EditText mChordEditText;

    public ChordCell(@NonNull Context context) {
        super(context);
        mChordEditText = (EditText) findViewById(R.id.glChords);
    }

    public ChordCell(@NonNull Context context, Chord chord) {
        super(context);
        this.chord = chord;

        mChordEditText = (EditText) findViewById(R.id.glChords);
    }

    public boolean addChord(Chord chord){
        if(this.chord == null){
            this.chord = chord;
            mChordEditText.setText(chord.getChord());
            return true;
        }
        return false;
    }

    public void clear(){
        chord = null;
        mChordEditText.setText("");
    }
}
