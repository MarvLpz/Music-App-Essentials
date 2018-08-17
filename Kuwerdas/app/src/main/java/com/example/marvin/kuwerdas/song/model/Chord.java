package com.example.marvin.kuwerdas.song.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Line.class,
        parentColumns = "id",
        childColumns = "lineId",
        onDelete = CASCADE))
public class Chord {
    public static final String EMPTY_CHORD = " " + " ";
    private String chord;

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "lineId")
    private int lineId;

    @ColumnInfo(name = "order")
    private int order = -1;

    public Chord(){}

    public Chord(String c) {
        chord = c;
    }

    public String getChord() {
        return chord;
    }

    public void setChord(String chord) {
        this.chord = chord;
    }
    public String toString(){
        return chord;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
