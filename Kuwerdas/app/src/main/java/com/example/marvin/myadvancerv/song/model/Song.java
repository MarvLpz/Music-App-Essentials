package com.example.marvin.myadvancerv.song.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;

import java.util.List;

@Entity
public class Song {
    @PrimaryKey
    private int uid;

    @ColumnInfo(name = "song_title")
    private String songTitle;

    @ColumnInfo(name = "artist")
    private String artist;

    @Ignored
    private List<Verse> verses;

    public int getUid() {
        return uid;
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

    @Override
    public String toString(){
        return songTitle + " - " + artist + "\n\n" + verses;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}