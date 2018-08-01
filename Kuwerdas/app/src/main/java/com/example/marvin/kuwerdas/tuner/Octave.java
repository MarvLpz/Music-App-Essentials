package com.example.marvin.kuwerdas.tuner;

import java.util.List;

public class Octave {
    int number;
    float min;
    float max;
    TuneString[] notes;

    public Octave(int number, float min, float max) {
        this.number = number;
        this.min = min;
        this.max = max;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public TuneString[] getNotes() {
        return notes;
    }

    public void setNotes(TuneString[] notes) {
        this.notes = notes;
    }

}
