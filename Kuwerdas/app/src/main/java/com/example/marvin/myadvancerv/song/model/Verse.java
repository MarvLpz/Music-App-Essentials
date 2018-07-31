package com.example.marvin.myadvancerv.song.model;

import java.util.List;

public class Verse{

    private String title;
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
}
