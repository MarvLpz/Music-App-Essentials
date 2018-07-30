package com.example.marvin.myadvancerv;

import android.util.Log;

import java.util.ArrayList;

public class SongUtil {
    public static ArrayList<Verse> asVerses(String lyrics){
        ArrayList<Verse> song = new ArrayList<>();

        String[] strVerses = lyrics.split("\n\n");
        for(String v : strVerses){
            Verse verse;
            ArrayList<Line> linesInVerse = new ArrayList<>();

            String[] strLines = v.split("\n");
            for(String l : strLines){
                linesInVerse.add(new Line(l));
            }

            song.add(new Verse(linesInVerse));
        }

        return song;
    }
}
