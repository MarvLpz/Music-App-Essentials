package com.example.marvin.myadvancerv.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.marvin.myadvancerv.song.model.Verse;
import com.example.marvin.myadvancerv.song.model.Line;

import java.util.List;

@Dao
public abstract class VerseDao {
    @Query("SELECT * FROM verse")
    public abstract List<Verse> getAll();

    @Query("DELETE FROM verse")
    public abstract void deleteAll();

    @Insert
    abstract void _insertVerse(Verse verse);

    @Insert
    abstract void _insertLines(List<Line> lines);

    @Query("SELECT * FROM verse WHERE uid =:id")
    abstract Verse getVerse(int id);

    @Query("SELECT * FROM line WHERE id =:id")
    abstract List<Line> getLines(int id);

    public void insertVerseWithLines(Verse verse) {
        List<Line> lines = verse.getLines();
        for (int i = 0; i < lines.size(); i++) {
            lines.get(i).setVerseId(verse.getUid());
        }

        _insertLines(lines);
        _insertVerse(verse);
    }

    public Verse getVerseWithLines(int id) {
        Verse verse = getVerse(id);
        List<Line> lines = getLines(id);
        verse.setLines(lines);
        return verse;
    }
}
