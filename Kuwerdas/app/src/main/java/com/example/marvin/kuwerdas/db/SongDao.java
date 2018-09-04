package com.example.marvin.kuwerdas.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import com.example.marvin.kuwerdas.song.adapter.ChordOrderComparator;
import com.example.marvin.kuwerdas.song.model.Chord;
import com.example.marvin.kuwerdas.song.model.Line;
import com.example.marvin.kuwerdas.song.model.Song;
import com.example.marvin.kuwerdas.song.model.Verse;

import java.util.Collections;
import java.util.List;

@Dao
public abstract class SongDao {

    /**************************************** PUBLIC METHODS *****************************************/

    //Call this to insert song to database
    public int insertSong(Song song){

        if (song.getSongTitle().equals("") && song.getArtist().equals("")){
            song.setSongTitle("Unknown Title");
            song.setArtist("Unknown Artist");
        }
        else if (song.getSongTitle().equals("")){
            song.setSongTitle("Unknown Title");
        }
        else if (song.getArtist().equals("")){
            song.setArtist("Unknown Artist");
        }

        int id = _insertSong(song).intValue();
        List<Verse> verses = song.getVerses();

        for(Verse verse : verses){
            verse.setSongId(id);
            int verseId = insertVerse(verse).intValue();
            for(Line line : verse.getLines()){
                line.setVerseId(verseId);
                int lineId = insertLine(line).intValue();
                for(int i=0;i<line.getChordSet().size();i++){
                    Chord chord = line.getChordSet().get(i);
                    chord.setLineId(lineId);
                    chord.setOrder(i);
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
                Collections.sort(line.getChordSet(),new ChordOrderComparator());
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

    @Delete
    public abstract void deleteVerse(Verse verse);

    @Delete
    public abstract void deleteLine(Line line);

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

    @Query("UPDATE song SET tempo = :tempo WHERE uid = :id")
    public abstract void updateTempo(int id, int tempo);

    public void updateSong(Song song){
        int songId = upsertSong(song).intValue();//Update song title, artist, and date modified
        Log.d("UPDATE2","song: " + song.getUid() + " - " + song.getArtist() + " - " + song.getSongTitle());
        int j =0;
        for(Verse verse : song.getVerses()){

            verse.setSongId(songId);
            int verseId = upsertVerse(verse, j).intValue();//Insert or update verse
            Log.d("UPDATE2","verse: " + verse.getUid() + " - " + verse.getTitle());
            ++j;
            for(Line line : verse.getLines()){
                line.setVerseId(verseId);
                Log.d("UPDATE2","line: " + line.getId() + " - " + line.getLyrics());
                int lineId = upsertLine(line).intValue();//Insert or update line
                String chordset = "";

                for(int i=0;i<line.getChordSet().size();i++){
                    Chord chord = line.getChordSet().get(i);
                    chord.setLineId(lineId);
                    chordset += chord.getId() + "[" + chord.getChord() + "] - ";
                    upsertChord(chord,i);//Insert or update chord
                }
                Log.d("UPDATE2","chords: " + chordset);
            }
        }
    }

    /*************************************** DO NOT EDIT BELOW ***************************************/
    @Insert(onConflict = OnConflictStrategy.FAIL)
    abstract Long _insertSong(Song song);

    @Insert(onConflict = OnConflictStrategy.FAIL)
    abstract Long insertVerse(Verse verse);

    @Insert(onConflict = OnConflictStrategy.FAIL)
    abstract Long insertLine(Line line);

    @Insert(onConflict = OnConflictStrategy.FAIL)
    abstract Long insertChord(Chord chord);

    @Insert abstract void insertAllChords(List<Chord> chords);

    @Insert abstract void insertAllVerses(List<Verse> verses);

    @Insert abstract void insertAllLines(List<Line> line);

    @Insert abstract void insertAllSongs(List<Song> song);

    @Query("SELECT * FROM song where uid=:id")
    abstract Song getSong(int id);

    @Query("SELECT * FROM verse where songId=:id")
    abstract List<Verse> getSongVerses(int id);

    @Query("SELECT * FROM line where verseId=:id")
    abstract List<Line> getVerseLines(int id);

    @Query("SELECT * FROM chord where lineId=:id")
    abstract List<Chord> getLineChords(int id);

    @Update(onConflict = OnConflictStrategy.FAIL)
    abstract int _updateSong(Song song);

    @Update(onConflict = OnConflictStrategy.FAIL)
    abstract int updateVerse(Verse verse);

    @Update(onConflict = OnConflictStrategy.FAIL)
    abstract int updateLine(Line line);

    @Update(onConflict = OnConflictStrategy.FAIL)
    abstract int updateChord(Chord chord);

    private Long upsertSong(Song song) {
        try { long id = _insertSong(song); Log.d("UPDATE","insert song: " + id); return id;} catch (SQLiteConstraintException exception) { long id = song.getUid(); _updateSong(song); Log.d("UPDATE","update song: " + id); return id;}
    }

/*    private Long upsertVerse(Verse verse) {
        try { long id = insertVerse(verse);  Log.d("UPDATE","insert verse: " + id); return id;} catch (SQLiteConstraintException exception) { long id = verse.getUid(); updateVerse(verse); Log.d("UPDATE","update verse: " + id); return id;}
    }*/
    private Long upsertVerse(Verse verse, int order) {
        try { long id = insertVerse(verse); setVerseOrder(id,order); Log.d("UPDATE","insert verse: " + id); return id;} catch (SQLiteConstraintException exception) { long id = verse.getUid(); updateVerse(verse);  setVerseOrder(id,order);Log.d("UPDATE","update verse: " + id); return id;}
    }

    private Long upsertLine(Line line) {
        try { long id = insertLine(line); Log.d("UPDATE","insert line: " + id); return id;} catch (SQLiteConstraintException exception) { long id = line.getId(); updateLine(line); Log.d("UPDATE","update line: " + id); return id;}
    }

    private Long upsertChord(Chord chord, int order) {
        try { Long id =insertChord(chord); setChordOrder(id,order); Log.d("UPDATE","insert chord: " + id); return id;} catch (SQLiteConstraintException exception) { long id = chord.getId(); updateChord(chord);  setChordOrder(id,order);Log.d("UPDATE","update chord: " + id); return id;}
    }

    @Query("UPDATE chord set `order`=:mOrder where id=:mChordId")
    abstract void setChordOrder(long mChordId, int mOrder);

    @Query("UPDATE verse set `verseOrder`=:vOrder where uid=:vId")
    abstract void setVerseOrder(long vId, int vOrder);


    /*************************************** DO NOT EDIT ABOVE ***************************************/
}
