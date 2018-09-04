package com.example.marvin.kuwerdas.song;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.marvin.kuwerdas.R;
import com.example.marvin.kuwerdas.song.adapter.VerseItemAdapter;
import com.example.marvin.kuwerdas.song.adapter.VerseItemViewHolder;

public class StanzaDialog extends Dialog implements
        android.view.View.OnClickListener {
    VerseItemAdapter verseItemAdapter;
     Context con;
     String stanza;
    public Button intro, verse,precho,chorus,bridge,outro,instrumental,adlib;
    public StanzaDialog(Context context) {
        super(context);
        con = context;
    }

/*  public StanzaDialog() {
      super(null);
  }*/
/*public void setStanza(String stanza){
}*/

    @Override
    public void onClick(View v) {
        verseItemAdapter = new VerseItemAdapter(SongFragment.song, SongFragment.getInstance(),null);
        switch (v.getId()) {
            case R.id.id_intro:
                verseItemAdapter.setStanza(intro.getText().toString());

                dismiss();
                Log.d("TRY DIALOG", intro.getText().toString());
                break;
            case R.id.id_verse:
                verseItemAdapter.setStanza(verse.getText().toString());
                dismiss();
                Log.d("TRY DIALOG", verse.getText().toString());
                break;
            default:
                break;
        }
        dismiss();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
            intro = (Button) findViewById(R.id.id_intro);
            verse = (Button) findViewById(R.id.id_verse);
            precho = (Button) findViewById(R.id.id_precho);
            chorus = (Button) findViewById(R.id.id_chorus);
            bridge = (Button) findViewById(R.id.id_bridge);
            outro = (Button) findViewById(R.id.id_outro);
            instrumental = (Button) findViewById(R.id.id_instru);
            adlib = (Button) findViewById(R.id.id_adlib);

        intro.setOnClickListener(this);
        verse.setOnClickListener(this);

    }
}
