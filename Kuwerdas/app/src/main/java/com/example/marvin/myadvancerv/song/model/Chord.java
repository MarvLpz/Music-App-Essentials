package com.example.marvin.myadvancerv.song.model;

public class Chord {
    public static final String EMPTY_CHORD = "  ";
    private String chord;

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
}
