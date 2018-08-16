package com.example.marvin.kuwerdas.song.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Verse.class,
        parentColumns = "uid",
        childColumns = "verseId",
        onDelete = CASCADE))
public class Line {

    @Ignore
    public static final int CHORD_SET_LENGTH = 15;

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "verseId")
    private int verseId;

    @Ignore
    private List<Chord> chordSet;

    @ColumnInfo(name = "lyrics")
    private String lyrics;

    public Line() {
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return "lineId: " + id + " - verseId: " + verseId;
    }

    public int getVerseId() {
        return verseId;
    }

    public void setVerseId(int verseId) {
        this.verseId = verseId;
    }
}