package com.example.marvin.myadvancerv;

public class Line {
    public static final int CHORD_SET_LENGTH = 10;
    private Chord[] chordSet;
    private String lyrics;

    public Line(String lyrics) {
        this.lyrics = lyrics;
        chordSet = new Chord[]{null,null,null,null,null,null,null,null,null,null};
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
