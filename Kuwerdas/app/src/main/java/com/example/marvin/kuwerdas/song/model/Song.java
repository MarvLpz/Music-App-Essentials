package com.example.marvin.kuwerdas.song.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

@Entity
public class Song implements Serializable{
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "song_title")
    private String songTitle;

    @ColumnInfo(name = "artist")
    private String artist;

    @ColumnInfo(name = "date_modified")
    private String dateModified;

    @ColumnInfo(name = "key")
    private int key;

    @Ignore
    private List<Verse> verses;

    @ColumnInfo(name = "tempo")
    private int tempo;

    @ColumnInfo(name = "timesig")
    private String timesig;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public List<Verse> getVerses() {
        return verses;
    }

    public void setVerses(List<Verse> verses) {
        this.verses = verses;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public int getTempo() {
        return tempo;
    }
    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    public void setTimesig(String timesig) {
        this.timesig = timesig;
    }

    public String getTimesig() {
        return timesig;
    }



    @Override
    public String toString(){
        return "[" + uid + " - " + songTitle + " - " + artist + " ]";
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getKey(){
        return key;
    }
}