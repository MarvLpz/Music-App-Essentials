package com.example.marvin.kuwerdas.song.util;

import com.example.marvin.kuwerdas.song.model.Line;
import com.example.marvin.kuwerdas.song.model.Song;
import com.example.marvin.kuwerdas.song.model.Verse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

    public static Song asSong(String title, String artist, String lyrics){
        Song song = new Song();
        ArrayList<Verse> verses = new ArrayList<>();

        song.setSongTitle(title);
        song.setArtist(artist);
        song.setVerses(verses);
        song.setDateModified((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).format(new Date()));

        String[] strVerses = lyrics.split("\n\n");
        for(String v : strVerses){
            Verse verse;
            ArrayList<Line> linesInVerse = new ArrayList<>();

            String[] strLines = v.split("\n");
            for(String l : strLines){
                linesInVerse.add(new Line(l));
            }

            verses.add(new Verse(linesInVerse));
        }

        return song;
    }
}
