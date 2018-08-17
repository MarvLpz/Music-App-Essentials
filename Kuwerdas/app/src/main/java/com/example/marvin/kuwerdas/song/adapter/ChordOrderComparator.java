package com.example.marvin.kuwerdas.song.adapter;

import com.example.marvin.kuwerdas.song.model.Chord;

import java.util.Comparator;

public class ChordOrderComparator implements Comparator<Chord>{
    @Override
    public int compare(Chord o1, Chord o2) {
        return o1.getOrder() - o2.getOrder();
    }
}
