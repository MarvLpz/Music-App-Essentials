package com.example.marvin.kuwerdas.song.picker.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.marvin.kuwerdas.song.picker.model.Letter.E;
import static com.example.marvin.kuwerdas.song.picker.model.Letter.F;
import static com.example.marvin.kuwerdas.song.picker.model.Letter.G;
import static com.example.marvin.kuwerdas.song.picker.model.Letter.A;
import static com.example.marvin.kuwerdas.song.picker.model.Letter.B;
import static com.example.marvin.kuwerdas.song.picker.model.Letter.C;
import static com.example.marvin.kuwerdas.song.picker.model.Letter.D;

public class DetailedChordIndex {
    public static List<Letter> flatChords = Arrays.asList(E,B);
    public static List<Letter> sharpChords = Arrays.asList(C,D,F,G,A);

    public static List<Letter> allChords = Arrays.asList(A,B,C,D,E,F,G);

    public static List<Accidental> allAccidentals = Arrays.asList(Accidental.natural,Accidental.flat,Accidental.sharp);

    public static List<Scale> allScales = Arrays.asList(Scale.major,Scale.minor,Scale.sus);

    public static List<Number> susNumbers = Arrays.asList(Number.two,Number.four);
    public static List<Number> defaultNumbers = Arrays.asList(Number.none,Number.six,Number.seven,Number.nine);

    public static List<DetailedChord> getChords(DetailedChord chord){
        List<Letter> letters;
        List<DetailedChord> chords = new ArrayList<>();
        Number number = chord.getNumber();

        if(chord.getAccidental() == Accidental.flat)
            letters = new ArrayList<>(DetailedChordIndex.flatChords);
        else if(chord.getAccidental() == Accidental.sharp)
            letters = new ArrayList<>(DetailedChordIndex.sharpChords);
        else
            letters = new ArrayList<>(DetailedChordIndex.allChords);


        if(chord.getScale() == Scale.sus) {
            number = Number.none;
        }

        for(Letter letter : letters){
            chords.add(new DetailedChord(letter,chord.getAccidental(),chord.getScale(),number));
        }

        return chords;
    }
}
