package com.example.marvin.kuwerdas.song.util;

import com.example.marvin.kuwerdas.song.picker.model.Accidental;

public class Transposer {
    public static String transposeChordUp(String chord){
        if(chord.startsWith("A" + Accidental.flat.getValue()))
            return chord.replace("A" + Accidental.flat.getValue(),"A");
        else if(chord.startsWith("A"))
            return chord.replace("A","B" + Accidental.flat.getValue());
        else if(chord.startsWith("B" + Accidental.flat.getValue()))
            return chord.replace("B" + Accidental.flat.getValue(),"B");
        else if(chord.startsWith("B"))
            return chord.replace("B","C");
        else if(chord.startsWith("C" + Accidental.sharp.getValue()))
            return chord.replace("C" + Accidental.sharp.getValue(),"D");
        else if(chord.startsWith("C"))
            return chord.replace("C","C" + Accidental.sharp.getValue());
        else if(chord.startsWith("D"))
            return chord.replace("D","E" + Accidental.flat.getValue());
        else if(chord.startsWith("E" + Accidental.flat.getValue()))
            return chord.replace("E" + Accidental.flat.getValue(),"E");
        else if(chord.startsWith("E"))
            return chord.replace("E","F");
        else if(chord.startsWith("F" + Accidental.sharp.getValue()))
            return chord.replace("F" + Accidental.sharp.getValue(),"G");
        else if(chord.startsWith("F"))
            return chord.replace("F","F" + Accidental.sharp.getValue());
        else if(chord.startsWith("G"))
            return chord.replace("G","A" + Accidental.flat.getValue());

        return chord;
    }

    public static String transposeChordDown(String chord){
        if(chord.startsWith("A" + Accidental.flat.getValue()))
            return chord.replace("A" + Accidental.flat.getValue(),"G");
        else if(chord.startsWith("A"))
            return chord.replace("A","A" + Accidental.flat.getValue());
        else if(chord.startsWith("B" + Accidental.flat.getValue()))
            return chord.replace("B" + Accidental.flat.getValue(),"A");
        else if(chord.startsWith("B"))
            return chord.replace("B","B" + Accidental.flat.getValue());
        else if(chord.startsWith("C" + Accidental.sharp.getValue()))
            return chord.replace("C" + Accidental.sharp.getValue(),"C");
        else if(chord.startsWith("C"))
            return chord.replace("C","B");
        else if(chord.startsWith("D"))
            return chord.replace("D","C" + Accidental.sharp.getValue());
        else if(chord.startsWith("E" + Accidental.flat.getValue()))
            return chord.replace("E" + Accidental.flat.getValue(),"D");
        else if(chord.startsWith("E"))
            return chord.replace("E","E" + Accidental.flat.getValue());
        else if(chord.startsWith("F" + Accidental.sharp.getValue()))
            return chord.replace("F" + Accidental.sharp.getValue(),"F");
        else if(chord.startsWith("F"))
            return chord.replace("F","E");
        else if(chord.startsWith("G"))
            return chord.replace("G","F" + Accidental.sharp.getValue());

        return chord;
    }

}
