package com.example.marvin.myadvancerv.song.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Relation;

@Entity
public class Line {

    @Ignore
    private static final int CHORD_SET_LENGTH = 15;

    @Relation(parentColumn = "chordId", entityColumn = "lineId", entity = Chord.class)
    private Chord[] chordSet;

    @ColumnInfo(name = "lyrics")
    private String lyrics;

    public Line(String lyrics) {
        this.lyrics = lyrics;
        chordSet = new Chord[CHORD_SET_LENGTH];
    }

    public boolean addChord(Chord chord, int pos){
        if(pos>=0 && pos<=9){
            chordSet[pos] = chord;
            return true;
        }
        return false;
    }

    public Chord[] getChordSet() {
        return chordSet;
    }

    public void setChordSet(Chord[] chordSet) {
        this.chordSet = chordSet;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    @Override
    public String toString(){
        return lyrics;
    }
}
