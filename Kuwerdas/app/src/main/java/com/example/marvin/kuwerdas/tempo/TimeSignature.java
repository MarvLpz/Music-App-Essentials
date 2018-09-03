package com.example.marvin.kuwerdas.tempo;

public enum TimeSignature{
    four_four("4/4"),
    two_four("2/4"),
    three_four("3/4"),
    six_eight("6/8");

    public String getText(){
        return text;
    }

    private final String text;

    TimeSignature(String text) {
        this.text = text;
    }
}