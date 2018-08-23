package com.example.marvin.kuwerdas.song.picker.model;

public enum Scale {
    none("",""),
    major("","-"),
    minor("m","minor"),
    sus("sus","sus");

    String value;
    String label;

    Scale(String val,String la) {
        value = val;
        label = la;
    }

    public String getValue(){
        return value;
    }

    public String getLabel(){
        return label;
    }
}
