package com.example.marvin.kuwerdas.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.util.Log;

import com.example.marvin.kuwerdas.song.model.Chord;
import com.example.marvin.kuwerdas.song.model.Line;
import com.example.marvin.kuwerdas.song.model.Song;
import com.example.marvin.kuwerdas.song.model.Verse;

import java.util.List;

@Dao
public abstract class SongDao {

    public int insertSong(Song song){
        int id = _insertSong(song).intValue();
        List<Verse> verses = song.getVerses();

        for(Verse verse : verses){
            verse.setSongId(id);
            int verseId = insertVerse(verse).intValue();
            for(Line line : verse.getLines()){
                line.setVerseId(verseId);
                int lineId = insertLine(line).intValue();
                for(Chord chord : line.getChordSet()){
                    chord.setLineId(lineId);
                }
                insertAllChords(line.getChordSet());
            }
        }
        song.setVerses(verses);

        return id;
    }

    @Delete
    public abstract void deleteSong(Song song);

    @Insert
    abstract Long insertLine(Line line);

    @Insert
    abstract Long insertChord(Chord chord);

    @Insert
    abstract Long _insertSong(Song song);

    @Query("DELETE FROM song")
    public abstract void deleteAllSongs();

    @Query("DELETE FROM verse")
    public abstract void deleteAllVerses();

    @Query("DELETE FROM line")
    public abstract void deleteAllLines();

    @Query("DELETE FROM chord")
    public abstract void deleteAllChords();

    @Insert
    abstract void insertAllChords(List<Chord> chords);

    @Insert
    abstract void insertAllVerses(List<Verse> verses);

    @Insert
    abstract Long insertVerse(Verse verse);

    @Insert
    abstract void insertAllLines(List<Line> line);

    @Insert
    abstract void insertAll(List<Song> song);

    @Query("SELECT * FROM song")
    public abstract List<Song> getAllSongs();

    @Query("SELECT * FROM song WHERE song_title LIKE + :search or artist LIKE :search")
    public abstract List<Song> getSongsFromSearch(String search);

    @Query("SELECT * FROM verse")
    public abstract List<Verse> getAllVerses();

    @Query("SELECT * FROM line")
    public abstract List<Line> getAllLines();

    @Query("SELECT * FROM chord")
    public abstract List<Chord> getAllChords();

    @Query("SELECT * FROM song where uid=:id")
    abstract Song getSong(int id);

    @Query("SELECT * FROM verse where songId=:id")
    abstract List<Verse> getSongVerses(int id);

    @Query("SELECT * FROM line where verseId=:id")
    abstract List<Line> getVerseLines(int id);

    @Query("SELECT * FROM chord where lineId=:id")
    abstract List<Chord> getLineChords(int id);


    public Song getSongWithVerses(int id) {
        Log.d("RETRIEVE SONG","ALL SONGS: " + getAllSongs());
        Log.d("RETRIEVE SONG","ALL VERSES: " + getAllVerses());
        Log.d("RETRIEVE SONG","ALL LINES: " + getAllLines());
        Log.d("RETRIEVE SONG","ALL CHORDS: " + getAllChords());

        Song song = getSong(id);
        Log.d("RETRIEVE SONG", "song id: " + id);
        List<Verse> verses = getSongVerses(song.getUid());
        for(Verse verse :verses){
            List<Line> lines = getVerseLines(verse.getUid());
            for(Line line :lines){
                List<Chord> chords = getLineChords(line.getId());
                line.setChordSet(chords);
                Log.d("RETRIEVE SONG","CHORDSET:" + chords);
            }
            verse.setLines(lines);
            Log.d("RETRIEVE SONG","LINES:" + lines);

        }
        Log.d("RETRIEVE SONG","VERSES:" + verses);

        song.setVerses(verses);

        return song;
    }

}
