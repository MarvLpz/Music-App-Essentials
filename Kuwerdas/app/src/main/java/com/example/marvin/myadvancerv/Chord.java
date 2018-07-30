package com.example.marvin.myadvancerv;

public class Chord {
    private Character chord;

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
