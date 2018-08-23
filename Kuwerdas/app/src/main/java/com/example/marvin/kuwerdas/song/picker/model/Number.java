package com.example.marvin.kuwerdas.song.picker.model;

public enum Number {
   six("6","6"),
    seven("7","7"),
    nine("9","9"),
    two("2","2"),
    four("4","4"),
    none("","-");

    String value;
    String label;

    Number(String val,String la) {
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
