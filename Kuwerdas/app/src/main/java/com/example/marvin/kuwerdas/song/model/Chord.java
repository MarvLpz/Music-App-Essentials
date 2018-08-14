package com.example.marvin.kuwerdas.song.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Chord {
    public static final String EMPTY_CHORD = " " + " ";
    private String chord;

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int lineId;

    public Chord(){}

    public Chord(String c) {
        chord = c;
    }

    public String getChord() {
        return chord;
    }

    public void setChord(String chord) {
        this.chord = chord;
    }
    public String toString(){
        return chord;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }
}
