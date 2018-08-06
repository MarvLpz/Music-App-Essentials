package com.example.marvin.myadvancerv.song;

import android.app.Application;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ClipData;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marvin.myadvancerv.OnStartDragListener;
import com.example.marvin.myadvancerv.R;
//import com.example.marvin.myadvancerv.db.SongDatabase;
import com.example.marvin.myadvancerv.song.adapter.ChordItemAdapter;
import com.example.marvin.myadvancerv.song.adapter.VerseItemAdapter;
import com.example.marvin.myadvancerv.song.model.Chord;
import com.example.marvin.myadvancerv.song.model.Song;
import com.example.marvin.myadvancerv.song.model.Verse;
import com.example.marvin.myadvancerv.song.util.SongUtil;

import java.util.ArrayList;
import java.util.List;

public class SongActivity extends AppCompatActivity  implements OnStartDragListener {

    private VerseItemAdapter adapter;
    private RecyclerView recyclerView;

    private List<Chord> myChord;
    TextView tv_DragChord;

    float dX;
    float dY;
    int lastAction;

//    private ItemTouchHelper itemTouchHelper;
    private final String DATABASE_NAME = "SONG_DATABASE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        init();
        tv_DragChord = (TextView) findViewById(R.id.tv_dragChord);
        tv_DragChord.setOnTouchListener(new ChoiceTouchListener());
        ChordItemAdapter getTheChord = new ChordItemAdapter();
        getTheChord.getChord(tv_DragChord.getText().toString());
    }

    private void init(){
//        SongDatabase database = Room.databaseBuilder(getApplicationContext(), SongDatabase.class, DATABASE_NAME).build();

        recyclerView = findViewById(R.id.rvSong);
        String lyrics = "Nitong umaga lang, Pagka lambing-lambing\n" +
                "Ng iyong mga matang, Hayup kung tumingin.\n" +
                "Nitong umaga lang, Pagka galing-galing\n" +
                "Ng iyong sumpang, Walang aawat sa atin.\n" +
                "\n" +
                "O kay bilis namang maglaho ng Pag-ibig mo sinta,\n" +
                "Daig mo pa ang isang kisapmata.\n" +
                "Kaninay nariyan lang o ba't Bigla namang nawala.\n" +
                "Daig mo pa ang isang kisapmata.\n" +
                "\n" +
                "Kani-kanina lang, Pagka ganda-ganda\n" +
                "Ng pagkasabi mong Sana'y tayo na nga.\n" +
                "Kani-kanina lang, Pagka saya-saya\n" +
                "Ng buhay kong Bigla na lamang nagiba.\n" +
                "\n" +
                "O kay bilis namang maglaho ng Pag-ibig mo sinta,\n" +
                "Daig mo pa ang isang kisapmata.\n" +
                "Kaninay nariyan lang o ba't Bigla namang nawala.\n" +
                "Daig mo pa ang isang kisapmata.";


        Song song = SongUtil.asSong("Kisapmata","Rivermaya", lyrics);
        adapter = new VerseItemAdapter(song.getVerses());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        ItemTouchHelper.Callback callback = new EditItemTouchHelperCallback(adapter,recyclerView);
//        itemTouchHelper = new ItemTouchHelper(callback);
//        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);

//        List<Song> songs = database.songDao().getAll();
//        Log.d("TAGGY","Songs: " + songs);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
//        itemTouchHelper.startDrag(viewHolder);
    }



    public final class ChoiceTouchListener implements View.OnTouchListener{


        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN){
                ClipData data = ClipData.newPlainText("","");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                v.startDrag(data,shadowBuilder,v,0);
                return true;
            }
            else {
                return false;
            }
        }
    }
}
