package com.example.marvin.kuwerdas.song.util;

import com.example.marvin.kuwerdas.song.picker.model.Accidental;

public class Transposer {
    public static String transposeChordUp(String chord){
        if(chord.startsWith("A♭"))
            return chord.replace("A♭","A");
        else if(chord.startsWith("A"))
            return chord.replace("A","B♭");
        else if(chord.startsWith("B♭"))
            return chord.replace("B♭","B");
        else if(chord.startsWith("B"))
            return chord.replace("B","C");
        else if(chord.startsWith("C♯"))
            return chord.replace("C♯","D");
        else if(chord.startsWith("C"))
            return chord.replace("C","C♯");
        else if(chord.startsWith("D"))
            return chord.replace("D","E♭");
        else if(chord.startsWith("E♭"))
            return chord.replace("E♭","E");
        else if(chord.startsWith("E"))
            return chord.replace("E","F");
        else if(chord.startsWith("F♯"))
            return chord.replace("F♯","G");
        else if(chord.startsWith("F"))
            return chord.replace("F","F♯");
        else if(chord.startsWith("G"))
            return chord.replace("G","A♭");

        return chord;
    }

    public static String transposeChordDown(String chord){
        if(chord.startsWith("A♭"))
            return chord.replace("A♭","G");
        else if(chord.startsWith("A"))
            return chord.replace("A","A♭");
        else if(chord.startsWith("B♭"))
            return chord.replace("B♭","A");
        else if(chord.startsWith("B"))
            return chord.replace("B","B♭");
        else if(chord.startsWith("C♯"))
            return chord.replace("C♯","C");
        else if(chord.startsWith("C"))
            return chord.replace("C","B");
        else if(chord.startsWith("D"))
            return chord.replace("D","C♯");
        else if(chord.startsWith("E♭"))
            return chord.replace("E♭","D");
        else if(chord.startsWith("E"))
            return chord.replace("E","E♭");
        else if(chord.startsWith("F♯"))
            return chord.replace("F♯","F");
        else if(chord.startsWith("F"))
            return chord.replace("F","E");
        else if(chord.startsWith("G"))
            return chord.replace("G","F♯");

        return chord;
    }

}
