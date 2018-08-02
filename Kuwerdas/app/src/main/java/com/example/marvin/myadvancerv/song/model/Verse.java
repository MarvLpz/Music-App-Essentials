package com.example.marvin.myadvancerv.song.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;

import java.util.List;

@Entity
public class Verse{

    @PrimaryKey
    private int uid;

    private int songId;

    @ColumnInfo(name = "verseTitle")
    private String title;

    @Relation(parentColumn = "lineId", entityColumn = "verseId", entity = Line.class)
    private List<Line> lines;

    public Verse(List<Line> lines) {
        this.lines = lines;
        title = "Verse";//TODO fix title
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    @Override
    public String toString(){
        return lines.toString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }
}
