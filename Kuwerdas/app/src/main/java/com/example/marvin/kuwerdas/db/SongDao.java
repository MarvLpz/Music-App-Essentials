package com.example.marvin.kuwerdas.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import com.example.marvin.kuwerdas.song.model.Chord;
import com.example.marvin.kuwerdas.song.model.Line;
import com.example.marvin.kuwerdas.song.model.Song;
import com.example.marvin.kuwerdas.song.model.Verse;

import java.util.List;

@Dao
public abstract class SongDao {

    /**************************************** PUBLIC METHODS *****************************************/

    //Call this to insert song to database
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

    //Call this to retrieve complete song
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

    //Call this when searching for song (***Note: verses and lines are null***)
    @Query("SELECT * FROM song WHERE song_title LIKE + :search or artist LIKE :search")
    public abstract List<Song> getSongsFromSearch(String search);

    @Delete
    public abstract void deleteSong(Song song);

    @Query("DELETE FROM song")
    public abstract void deleteAllSongs();

    @Query("SELECT * FROM song")
    public abstract List<Song> getAllSongs();

    @Query("SELECT * FROM verse")
    public abstract List<Verse> getAllVerses();

    @Query("SELECT * FROM line")
    public abstract List<Line> getAllLines();

    @Query("SELECT * FROM chord")
    public abstract List<Chord> getAllChords();

    public void updateSong(Song song){
        _updateSong(song);//Update song title, artist, and date modified
        for(Verse verse : song.getVerses()){
            upsert((Entity) verse);//Insert or update verse
            for(Line line : verse.getLines()){
                upsert((Entity) line);//Insert or update line
                for(Chord chord : line.getChordSet()){
                    upsert((Entity) chord);//Insert or update chord
                }
            }
        }
    }

    /*************************************** DO NOT EDIT BELOW ***************************************/
    @Insert abstract Long insertLine(Line line);

    @Insert abstract Long insertChord(Chord chord);

    @Insert abstract Long _insertSong(Song song);

    @Insert abstract void insertAllChords(List<Chord> chords);

    @Insert abstract void insertAllVerses(List<Verse> verses);

    @Insert abstract void insertAllLines(List<Line> line);

    @Insert abstract void insertAllSongs(List<Song> song);

    @Insert abstract Long insertVerse(Verse verse);

    @Query("SELECT * FROM song where uid=:id")
    abstract Song getSong(int id);

    @Query("SELECT * FROM verse where songId=:id")
    abstract List<Verse> getSongVerses(int id);

    @Query("SELECT * FROM line where verseId=:id")
    abstract List<Line> getVerseLines(int id);

    @Query("SELECT * FROM chord where lineId=:id")
    abstract List<Chord> getLineChords(int id);

    @Update abstract void _updateSong(Song song);

    @Update abstract void updateVerses(List<Verse> verses);

    @Update abstract void updateLines(List<Line> lines);

    @Update abstract void updateChords(List<Chord> chords);

    @Insert(onConflict = OnConflictStrategy.FAIL)
    abstract void insert(Entity entity);

    @Update(onConflict = OnConflictStrategy.FAIL)
    abstract void update(Entity entity);

    void upsert(Entity entity) {
        try {
            insert(entity);
        } catch (SQLiteConstraintException exception) {
            update(entity);
        }
    }

    /*************************************** DO NOT EDIT ABOVE ***************************************/
}
