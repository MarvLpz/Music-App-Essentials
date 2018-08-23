package com.example.marvin.kuwerdas.song.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Song.class,
        parentColumns = "uid",
        childColumns = "songId",
        onDelete = CASCADE))
public class Verse{

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "songId")
    private int songId;

    @ColumnInfo(name = "verseTitle")
    private String title;

    @Ignore
    private List<Line>
            lines;



    @ColumnInfo(name = "verseOrder")
    private int verseOrder;

    public Verse(List<Line> lines) {
        this.lines = lines;
        title = "Verse";//TODO fix title
    }

    public Verse() {
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    @Override
    public String toString(){
        return "verseId: " + uid + " - songId: " + songId;
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

    public int getVerseOrder() {
        return verseOrder;
    }

    public void setVerseOrder(int verseOrder) {
        this.verseOrder = verseOrder;
    }
}
