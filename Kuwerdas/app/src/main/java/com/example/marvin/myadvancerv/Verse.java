package com.example.marvin.myadvancerv;

import java.util.List;

public class Verse{

    List<Line> lines;

    public Verse(List<Line> lines) {
        this.lines = lines;
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
}
