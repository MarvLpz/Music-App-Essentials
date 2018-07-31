package com.example.marvin.myadvancerv.song.model;

public class Chord {
    private Character chord;

    public Chord() {
        chord = 'C';
    }

    public Character getChord() {
        return chord;
    }

    public boolean setChord(Character chord) {
        if (Character.isAlphabetic(chord)) {
            this.chord = chord;
            return true;
        }
        return false;
    }
}
