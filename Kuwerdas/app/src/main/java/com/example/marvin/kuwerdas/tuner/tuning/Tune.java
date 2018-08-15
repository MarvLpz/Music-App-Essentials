package com.example.marvin.kuwerdas.tuner.tuning;

import com.example.marvin.kuwerdas.tuner.frequencynotes.Note;

public class Tune {
    private Note closestNote;
    private Float pitch;
    private Double centInterval;


    public Tune(Note closestNote, Float pitch) {
        this.closestNote = closestNote;
        this.pitch = pitch;
        setCentInterval();
    }

    public Note getClosestNote() {
        return closestNote;
    }

    public Float getPitch() {
        return pitch;
    }

    public Double getCentInterval() {
        return (centInterval!=null) ? centInterval : 0d;
    }

    public void setClosestNote(Note closestNote) {
        this.closestNote = closestNote;
        setCentInterval();
    }

    public void setPitch(Float pitch) {
        this.pitch = pitch;
        setCentInterval();
    }

    private void setCentInterval(){
        if(closestNote!=null)
            centInterval = GuitarTuning.getCentInterval(closestNote.getFrequency(),pitch);
    }

    public String toString(){
        return "Note: " + closestNote + "\n"
                + "Interval: " + String.format("%.2f",centInterval) + " c";
    }
}
