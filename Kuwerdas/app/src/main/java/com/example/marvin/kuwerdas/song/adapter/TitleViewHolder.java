package com.example.marvin.kuwerdas.song.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.marvin.kuwerdas.MainActivity;
import com.example.marvin.kuwerdas.OnChangeFragment;
import com.example.marvin.kuwerdas.R;
import com.example.marvin.kuwerdas.song.SongFragment;
import com.example.marvin.kuwerdas.song.model.Song;


public class TitleViewHolder extends RecyclerView.ViewHolder {
    private View itemView;
    private TextView title;
    private TextView artist;
    private TextView tempo;
    private TextView key;
    private Button transposeUp;
    private Button transposeDown;

    private ChordTransposer chordTransposer;
    private SongFragment songFragment;

    public TitleViewHolder(View itemView, SongFragment songFragment) {
        super(itemView);
        this.itemView = itemView;
        this.songFragment = songFragment;
        title = itemView.findViewById(R.id.tvTitle);
        artist = itemView.findViewById(R.id.tvArtist);
        tempo = itemView.findViewById(R.id.tvTempo);
        key = itemView.findViewById(R.id.tvTranspose);

        chordTransposer = songFragment;
    }

    public void setSongDetails(final Song song){
        //TITLE
        title.setText(song.getSongTitle());
        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                song.setSongTitle(s.toString());
                SongFragment.isSongEdited = true;
            }
        });

        //ARTIST
        artist.setText(song.getArtist());
        artist.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                song.setArtist(s.toString());
                SongFragment.isSongEdited = true;
            }
        });

        //TEMPO
        tempo.setText("Tempo: " + song.getTempo() + " bpm");
        tempo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.FragmentSwitcher.change(OnChangeFragment.Frags.TEMPO);
            }
        });

        //KEY (TRANSPOSE)
        key.setText("" +song.getKey());
        key.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                song.setKey(0);
                key.setText(Integer.toString(song.getKey()));
                //todo reset transpose chords
                return true;
            }
        });
        transposeUp = itemView.findViewById(R.id.btnKeyUp);
        transposeUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                songFragment.showProgressBar(true);
                song.setKey(song.getKey() + 1 == 12 ? 0 : song.getKey() + 1);
                key.setText(Integer.toString(song.getKey()));
                chordTransposer.transposeUp();
            }
        });

        transposeDown = itemView.findViewById(R.id.btnKeyDown);
        transposeDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                songFragment.showProgressBar(true);
                song.setKey(song.getKey() + 1 == -12 ? 0 : song.getKey() - 1);
                key.setText(Integer.toString(song.getKey()));
                chordTransposer.transposeDown();
            }
        });
    }

    public interface ChordTransposer{
        public void transposeUp();
        public void transposeDown();
    }
}