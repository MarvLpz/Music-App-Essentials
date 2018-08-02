package com.example.marvin.myadvancerv.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.marvin.myadvancerv.song.model.Song;
import com.example.marvin.myadvancerv.song.model.Verse;

import java.util.List;

@Dao
public abstract class SongDao {
    public void insertVersesForSong(Song song, List<Verse> verses){
        for(Verse verse : verses){
            verse.setSongId(song.getUid());
            insertAllVerses(verses);
        }
    }

    @Insert
    abstract void insertAllVerses(List<Verse> verses);

    @Insert
    abstract void insertAll(List<Song> song);

    @Query("SELECT * FROM song")
    abstract List<Song> getAll();
//
//    @Query("SELECT * FROM song WHERE song_title LIKE :song")
//    Song findBySongTitle(String song);
//
//    @Insert
//    void insertAll(List<Song> songs);
//
//    @Update
//    void update(Song song);
//
//    @Delete
//    void delete(Song song);
}
