package com.example.marvin.kuwerdas.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.marvin.kuwerdas.song.model.Chord;
import com.example.marvin.kuwerdas.song.model.Line;
import com.example.marvin.kuwerdas.song.model.Song;
import com.example.marvin.kuwerdas.song.model.Verse;

@Database(entities = {Song.class,Verse.class,Line.class,Chord.class}, version = 1)
public abstract class SongDatabase extends RoomDatabase {
     public abstract SongDao songDao();
}