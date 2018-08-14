package com.example.marvin.myadvancerv.song.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Relation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
public class Line {

    @Ignore
    public static final int CHORD_SET_LENGTH = 22;

    @Relation(parentColumn = "chordId", entityColumn = "lineId", entity = Chord.class)
    private List<Chord> chordSet;

    @ColumnInfo(name = "lyrics")
    private String lyrics;

    private void initializeEmptyChordSet(){
        chordSet = new ArrayList<>();
        Chord emptyChord = new Chord(Chord.EMPTY_CHORD);
        for(int i=0;i<CHORD_SET_LENGTH;i++)
            chordSet.add(emptyChord);
    }

    public Line(String lyrics) {
        this.lyrics = lyrics;
        initializeEmptyChordSet();
    }
    public Line(){
    }

    public boolean addChord(Chord chord, int pos){
        if(pos>=0 && pos<=9){
            chordSet.set(pos,chord);
            return true;
        }
        return false;
    }

    public List<Chord> getChordSet() {
        return chordSet;
    }

    public void setChordSet(List<Chord> chordSet) {
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
