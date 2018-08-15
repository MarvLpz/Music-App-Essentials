package com.example.marvin.kuwerdas.tuner.frequencynotes;
public class Note {
    private Notation PitchNotation;
    private Float Frequency;

    public Note(Notation pitchNotation, Float frequency) {
        PitchNotation = pitchNotation;
        Frequency = frequency;
    }

    public Notation getPitchNotation() {
        return PitchNotation;
    }

    public void setPitchNotation(Notation pitchNotation) {
        PitchNotation = pitchNotation;
    }

    public Float getFrequency() {
        return Frequency;
    }

    public void setFrequency(Float frequency) {
        Frequency = frequency;
    }

    public String toString(){
        return PitchNotation.toString() + " - " + Frequency;
    }

}
