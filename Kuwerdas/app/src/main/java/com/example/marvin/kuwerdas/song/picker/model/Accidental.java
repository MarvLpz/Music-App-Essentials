package com.example.marvin.kuwerdas.song.picker.model;

public enum Accidental {
    none("",""),
    sharp("♯","sharp"),
    flat("♭","flat"),
    natural("","-");

    String value;
    String label;

    Accidental(String val,String la) {
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
