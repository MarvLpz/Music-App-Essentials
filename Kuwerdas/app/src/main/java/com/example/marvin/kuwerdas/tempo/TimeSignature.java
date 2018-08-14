package com.example.marvin.kuwerdas.tempo;

import top.defaults.view.PickerView;

public enum TimeSignature implements PickerView.PickerItem {
    four_four("4/4"),
    six_eight("6/8"),
    two_four("2/4");

    public String getText(){
        return text;
    }

    private final String text;

    TimeSignature(String text) {
        this.text = text;
    }
}