package com.example.marvin.kuwerdas.tuner.frequencynotes;
public enum Notation {
    C("C"),
    C_sharp("C♯"),
    D("D"),
    E_flat("E♭"),
    E("E"),
    F("F"),
    F_sharp("F♯"),
    G("G"),
    G_sharp("G♯"),
    A("A"),
    B_flat("B♭"),
    B("B");

    private final String name;

    private Notation(String name) {
        this.name = name;
    }

    public boolean equalsName(String n) {

        return name.equals(n);
    }

    public String toString() {
        return this.name;
    }
}
