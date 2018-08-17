package com.example.marvin.kuwerdas.song;

public class Transposer {
    public static String transposeChordUp(String chord){
        if(chord.startsWith("Ab"))
            return chord.replace("Ab","A");
        else if(chord.startsWith("A"))
            return chord.replace("A","Bb");
        else if(chord.startsWith("Bb"))
            return chord.replace("Bb","B");
        else if(chord.startsWith("B"))
            return chord.replace("B","C");
        else if(chord.startsWith("C#"))
            return chord.replace("C#","D");
        else if(chord.startsWith("C"))
            return chord.replace("C","C#");
        else if(chord.startsWith("D"))
            return chord.replace("D","Eb");
        else if(chord.startsWith("Eb"))
            return chord.replace("Eb","E");
        else if(chord.startsWith("E"))
            return chord.replace("E","F");
        else if(chord.startsWith("F#"))
            return chord.replace("F#","G");
        else if(chord.startsWith("F"))
            return chord.replace("F","F#");
        else if(chord.startsWith("G"))
            return chord.replace("G","Ab");

        return chord;
    }

    public static String transposeChordDown(String chord){
        if(chord.startsWith("Ab"))
            return chord.replace("Ab","G");
        else if(chord.startsWith("A"))
            return chord.replace("A","Ab");
        else if(chord.startsWith("Bb"))
            return chord.replace("Bb","A");
        else if(chord.startsWith("B"))
            return chord.replace("B","Bb");
        else if(chord.startsWith("C#"))
            return chord.replace("C#","C");
        else if(chord.startsWith("C"))
            return chord.replace("C","B");
        else if(chord.startsWith("D"))
            return chord.replace("D","C#");
        else if(chord.startsWith("Eb"))
            return chord.replace("Eb","D");
        else if(chord.startsWith("E"))
            return chord.replace("E","Eb");
        else if(chord.startsWith("F#"))
            return chord.replace("F#","F");
        else if(chord.startsWith("F"))
            return chord.replace("F","E");
        else if(chord.startsWith("G"))
            return chord.replace("G","F#");

        return chord;
    }

}
