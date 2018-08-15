package com.example.marvin.kuwerdas.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.marvin.kuwerdas.song.model.Chord;
import com.example.marvin.kuwerdas.song.model.Line;

import java.util.List;

@Dao
public abstract class LineDao {
    @Query("SELECT * FROM line")
    public abstract List<Line> getAll();

    @Query("DELETE FROM line")
    public abstract void deleteAll();


    @Insert
    abstract void _insertLine(Line line);

    @Insert
    abstract void _insertChords(List<Chord> chords);

    @Query("SELECT * FROM line WHERE id =:id")
    abstract Line getLine(int id);

    @Query("SELECT * FROM chord WHERE id =:id")
    abstract List<Chord> getChords(int id);

    public void insertLineWithChords(Line line) {
        List<Chord> chords = line.getChordSet();
        for (int i = 0; i < chords.size(); i++) {
            chords.get(i).setLineId(line.getId());
        }

        _insertChords(chords);
        _insertLine(line);
    }

    public Line getLineWithChords(int id) {
        Line line = getLine(id);
        List<Chord> chords = getChords(id);
        line.setChordSet(chords);
        return line;
    }
}
