package com.example.marvin.kuwerdas.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.marvin.kuwerdas.song.model.Chord;
import com.example.marvin.kuwerdas.song.model.Line;
import com.example.marvin.kuwerdas.song.model.Song;
import com.example.marvin.kuwerdas.song.model.Verse;

@Database(entities = {Song.class,Verse.class,Line.class,Chord.class}, version = 1)
public abstract class SongDatabase extends RoomDatabase {
     public abstract SongDao songDao();

     private static SongDatabase instance;
     public static SongDatabase getSongDatabase(Context context) {
          if (instance == null) {
               instance = Room.databaseBuilder(context.getApplicationContext(),
                       SongDatabase.class,
                       "SONG_DATABASE")
                       .allowMainThreadQueries()
                       .build();
          }
          return instance;
     }
}