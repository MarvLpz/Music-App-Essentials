package com.example.marvin.kuwerdas.song.picker.model;

import java.util.ArrayList;
import java.util.List;

public class DetailedChord {
    Accidental accidental;
    Scale scale;
    Number number;

    Letter chord;

    public DetailedChord(Letter chord, Accidental accidental, Scale scale, Number number) {
        this.accidental = accidental;
        this.scale = scale;
        this.number = number;
        this.chord = chord;
    }

    public DetailedChord(Accidental accidental, Scale scale, Number number) {
        this.accidental = accidental;
        this.scale = scale;
        this.number = number;
    }

    public void setValue(DetailedChord detailedChord) {
        if(detailedChord!=null) {
            this.accidental = detailedChord.getAccidental();
            this.scale = detailedChord.getScale();
            this.number = detailedChord.getNumber();
            this.chord = detailedChord.getLeter();
        } else {
            this.accidental = Accidental.none;
            this.scale = Scale.none;
            this.number = Number.none;
            this.chord = Letter.none;
        }
    }

    public Accidental getAccidental() {
        return accidental;
    }

    public void setAccidental(Accidental accidental) {
        this.accidental = accidental;
    }

    public Scale getScale() {
        return scale;
    }

    public void setScale(Scale scale) {
        this.scale = scale;
    }

    public Number getNumber() {
        return number;
    }

    public void setNumber(Number number) {
        this.number = number;
    }

    public Letter getLeter() {
        return chord;
    }

    public void setLetter(Letter chord) {
        this.chord = chord;
    }

    public String getChord() {
        return chord.name() + accidental.getValue() + scale.getValue() + number.getValue();
    }
}
