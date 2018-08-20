package com.example.marvin.kuwerdas;

import com.example.marvin.kuwerdas.song.model.Song;

public interface OnChangeFragment {
    public enum Frags{
        TEMPO,
        TUNER,
        SONG,
        SEARCH
    }
    public void change(Frags frag);
}
