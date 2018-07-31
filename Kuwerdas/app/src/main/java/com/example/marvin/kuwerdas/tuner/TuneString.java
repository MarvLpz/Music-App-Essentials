package com.example.marvin.kuwerdas.tuner;

public class TuneString {
    private String PitchNotation;

    public String getPitchNotation() {
        return PitchNotation;
    }

    public void setPitchNotation(String pitchNotation) {
        PitchNotation = pitchNotation;
    }

    public Float getFrequency() {
        return Frequency;
    }

    public void setFrequency(Float frequency) {
        Frequency = frequency;
    }

    private Float Frequency;

    public TuneString(String pitchNotation, Float frequency) {
        PitchNotation = pitchNotation;
        Frequency = frequency;
    }
}
