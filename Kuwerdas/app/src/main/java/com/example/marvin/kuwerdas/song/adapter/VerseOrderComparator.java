package com.example.marvin.kuwerdas.song.adapter;

import com.example.marvin.kuwerdas.song.model.Chord;
import com.example.marvin.kuwerdas.song.model.Verse;

import java.util.Comparator;

public class VerseOrderComparator implements Comparator<Verse>{
    @Override
    public int compare(Verse o1, Verse o2) {
        return o1.getVerseOrder() - o2.getVerseOrder();
    }
}
