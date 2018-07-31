package com.example.marvin.myadvancerv.song;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.marvin.myadvancerv.OnStartDragListener;
import com.example.marvin.myadvancerv.R;
import com.example.marvin.myadvancerv.song.adapter.VerseItemAdapter;
import com.example.marvin.myadvancerv.song.adapter.itemtouch.EditItemTouchHelperCallback;
import com.example.marvin.myadvancerv.song.model.Verse;
import com.example.marvin.myadvancerv.song.util.SongUtil;

import java.util.ArrayList;

public class SongActivity extends AppCompatActivity  implements OnStartDragListener {

    private VerseItemAdapter adapter;
    private RecyclerView recyclerView;
    private ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        init();
    }

    private void init(){
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


        ArrayList<Verse> verses = SongUtil.asVerses(lyrics);

        adapter = new VerseItemAdapter(verses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemTouchHelper.Callback callback = new EditItemTouchHelperCallback(adapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        itemTouchHelper.startDrag(viewHolder);
    }
}
