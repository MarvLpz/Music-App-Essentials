package com.example.marvin.kuwerdas.song.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Chord {
    public static final String EMPTY_CHORD = "  ";

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int lineId;

    private String chord;

    public Chord(int id, int lineId, String chord) {
        this.id = id;
        this.lineId = lineId;
        this.chord = chord;
    }

    public Chord() {
    }

    public Chord(String c) {
        chord = c;
    }

    public Chord(int id, String chord) {
        this.id = id;
        this.chord = chord;
    }


    public String getChord() {
        return chord;
    }

    public void setChord(String chord) {
        this.chord = chord;
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

    public String toString(){
        return "chordId: " + id + " - lineId: " + lineId + " - chord: " + chord;
    }
}
