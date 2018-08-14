package com.example.marvin.myadvancerv.song;

import android.app.Application;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marvin.myadvancerv.Fragment.ChordDrawer;
import com.example.marvin.myadvancerv.Fragment.FloatingWindow;
import com.example.marvin.myadvancerv.OnStartDragListener;
import com.example.marvin.myadvancerv.R;
//import com.example.marvin.myadvancerv.db.SongDatabase;
import com.example.marvin.myadvancerv.song.adapter.ChordItemAdapter;
import com.example.marvin.myadvancerv.song.adapter.LineItemAdapter;
import com.example.marvin.myadvancerv.song.adapter.LineItemViewHolder;
import com.example.marvin.myadvancerv.song.adapter.VerseItemAdapter;
import com.example.marvin.myadvancerv.song.model.Chord;
import com.example.marvin.myadvancerv.song.model.Line;
import com.example.marvin.myadvancerv.song.model.Song;
import com.example.marvin.myadvancerv.song.model.Verse;
import com.example.marvin.myadvancerv.song.util.SongUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import top.defaults.view.PickerView;

public class SongActivity extends AppCompatActivity  implements OnStartDragListener {

    private VerseItemAdapter adapter;
    private RecyclerView recyclerView;

//    private List<Chord> myChord;
    TextView tv_DragChord1;
    TextView tv_DragChord2;
    TextView tv_DragChord3;
    TextView tv_DragChord4;
    TextView tv_DragChord5;
    TextView tv_DragChord6;
    TextView tv_DragChord7;

    String get_accidental = "",get_scale= "",get_number= "";

    float dX;
    float dY;
    int lastAction;

    private FloatingActionButton FloatAdd;
    private FloatingActionButton FloatDelete;
    private FloatingActionButton FloatTranspose;

    private Song song;
//    private BottomNavigationView mBottomNavigationView;

    LinearLayout linearLayout;

//    private ItemTouchHelper itemTouchHelper;
    private final String DATABASE_NAME = "SONG_DATABASE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        instantiate();
        init();
        showPicker();

/*        FloatAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final Dialog fbDialogue = new Dialog(SongActivity.this);
                fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(0, 0, 0, 0)));
                fbDialogue.setContentView(R.layout.fragment_chord_drawer);
                fbDialogue.setCancelable(true);
                fbDialogue.show();
            }
        });*/
     }
/*     public void showFragment(View view) {
        Fragment fragment;

        *//*if (view == findViewById(R.id.floatingActionButton)){
            fragment = new ChordDrawer();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft =  fm.beginTransaction();
            ft.replace(R.id.fragment_place, fragment);
            ft.commit();
        }*//*
     }*/

    static boolean transClicked;

    public void getTriggerTransposeBtn(boolean transClicked){
        this.transClicked = transClicked;
    }

    public void Transpose(){
//        int pos1,pos2,pos3;
//        VerseItemAdapter verseItemAdapter = new VerseItemAdapter();
//        LineItemAdapter line = new LineItemAdapter();
//        ChordItemAdapter chordAdapt = new ChordItemAdapter();

        if (transClicked){
            /*if (holder.tv.getText().equals("A")){
                holder.tv.setText("B");
            }*/

            for(Verse verse : song.getVerses()){
                for(Line line: verse.getLines()){
                    for(Chord chord : line.getChordSet()){
                        String letter = chord.getChord();
                        if(letter.equals("A")){
                            letter = "B";
                            Log.d("TRANSPOSE","letter was transposed");
                        }

                        chord.setChord(letter);
                    }
                }
            }

            adapter.updateItems(song.getVerses());

//            for (pos1 = 0;pos1 < verseItemAdapter.getSize; ++pos1){
//                Log.d("CHECKCONTAINERSTART","START " + String.valueOf(pos1));
//                for (pos2 = 0;pos2 < line.getSize; ++pos2){
//                    Log.d("CHECKCONTAINERSECOND",String.valueOf(pos2));
//                    for(pos3 = 0;pos3 < chordAdapt.getItemCount(); ++pos3){
//                        Log.d("CHECKCONTAINER",String.valueOf(pos3));
//                   Log.d("CHECKCHORD",String.valueOf(chordAdapt.myChord.get(pos3).getChord()));
//                   if(chordAdapt.myChord.get(pos3).getChord().equals("A")){
//                       Log.d("CHECKCHORDTorF","TRUE");
//                       chordAdapt.myChord.set(pos3, new Chord("B"));
//                       chordAdapt.notifyDataSetChanged();
//                   }
//               /* if (myChord.get(pos).getChord() == "A"){
//                    Log.d("CHECK CONTAINER","CONTAINS A");
//                }*/
//                    }
//                }
//            }


            Log.d("TRANSCLICK",String.valueOf(transClicked));
        }
    }

private class floatTransposeBtn implements View.OnClickListener{

    boolean clickedTranspose = false;
    @Override
    public void onClick(View v) {
        Log.d("TRANSCLICK", "clicked");
        if (clickedTranspose == false){
            clickedTranspose = true;
            Log.d("TRANSCLICK TorF", String.valueOf(clickedTranspose));
//            VerseItemAdapter verseItemAdapter = new VerseItemAdapter();
            getTriggerTransposeBtn(clickedTranspose);
            Transpose();
//            verseItemAdapter.Transpose();
        }
        clickedTranspose = false;
//        clickedTranspose = true;
        }
    }



    private class floataddBtn implements View.OnClickListener{

        boolean clicked = false;
        @Override
        public void onClick(View v) {
            if (clicked == false){
                linearLayout.setVisibility(View.VISIBLE);
                Log.d("Position X and Y",linearLayout.getX() + " " + linearLayout.getY());
                linearLayout.setX(16);
                linearLayout.setY(880);
                FloatAdd.setAlpha(255);
                clicked = true;
                FloatDelete.setClickable(false);
            }
            else {
                linearLayout.setVisibility(View.INVISIBLE);
                clicked = false;
                FloatAdd.setAlpha(127);
                FloatDelete.setClickable(true);
            }
        }
    }

    public class floatdelBtn implements View.OnClickListener{
        boolean delClicked = false;
        @Override
        public void onClick(View v) {
            if (delClicked == false){
                ChordItemAdapter triggerClicked = new ChordItemAdapter();
                delClicked = true;
                triggerClicked.getTriggerDelBtn(delClicked);
                FloatDelete.setAlpha(255);
                linearLayout.setVisibility(View.INVISIBLE);
                FloatAdd.setClickable(false);
//                FloatAdd.setAlpha(127);
            }
            else if (delClicked == true){
                ChordItemAdapter triggerClicked = new ChordItemAdapter();
                delClicked = false;
                triggerClicked.getTriggerDelBtn(delClicked);
                FloatDelete.setAlpha(80);
                FloatAdd.setClickable(true);
//                FloatAdd.setAlpha(255);
            }

        }
    }
    public void instantiate(){
        tv_DragChord1 = (TextView) findViewById(R.id.tv_dragChord1);
        tv_DragChord2 = (TextView) findViewById(R.id.tv_dragChord2);
        tv_DragChord3 = (TextView) findViewById(R.id.tv_dragChord3);
        tv_DragChord4 = (TextView) findViewById(R.id.tv_dragChord4);
        tv_DragChord5 = (TextView) findViewById(R.id.tv_dragChord5);
        tv_DragChord6 = (TextView) findViewById(R.id.tv_dragChord6);
        tv_DragChord7 = (TextView) findViewById(R.id.tv_dragChord7);
        tv_DragChord1.setOnTouchListener(new ChoiceTouchListener());
        tv_DragChord2.setOnTouchListener(new ChoiceTouchListener());
        tv_DragChord3.setOnTouchListener(new ChoiceTouchListener());
        tv_DragChord4.setOnTouchListener(new ChoiceTouchListener());
        tv_DragChord5.setOnTouchListener(new ChoiceTouchListener());
        tv_DragChord6.setOnTouchListener(new ChoiceTouchListener());
        tv_DragChord7.setOnTouchListener(new ChoiceTouchListener());

        FloatAdd = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        FloatDelete = (FloatingActionButton) findViewById(R.id.floatingDeleteButton);
        FloatAdd.setOnClickListener(new floataddBtn());
        FloatDelete.setOnClickListener(new floatdelBtn());
        FloatAdd.setAlpha(127);
        FloatDelete.setAlpha(80);
        FloatTranspose = (FloatingActionButton) findViewById(R.id.floatingTransposeButton);
        FloatTranspose .setOnClickListener(new floatTransposeBtn());
        linearLayout = (LinearLayout) findViewById(R.id.frame_place);
        linearLayout.setOnTouchListener(new touchMe());
        linearLayout.setVisibility(View.INVISIBLE);
    }
    public void showPicker() {
        Picker picker = new Picker();
        PickerView accidental = findViewById(R.id.accidental);

        accidental.setTextSize(40);

        accidental.setItems(picker.pickerAccidentals, new PickerView.OnItemSelectedListener<PickerView.PickerItem>() {
            @Override
            public void onItemSelected(PickerView.PickerItem item) {
                if (item.getText() == "x") { get_accidental = "";}
                else {
                    get_accidental = item.getText();
                }

                Toast.makeText(getBaseContext(),item.getText() + " is chosen",Toast.LENGTH_SHORT).show();
            }
        });

        PickerView scale = findViewById(R.id.scale);
        scale.setTextSize(40);

        scale.setItems(picker.pickerScale, new PickerView.OnItemSelectedListener<PickerView.PickerItem>() {
            @Override
            public void onItemSelected(PickerView.PickerItem item) {
                if (item.getText() == "x") {get_scale = ""; }
                else {
                    get_scale = item.getText();
                }
                Toast.makeText(getBaseContext(),item.getText() + " is chosen",Toast.LENGTH_SHORT).show();
            }
        });

        PickerView number = findViewById(R.id.number);
        number.setTextSize(40);
        number.setItems(picker.pickerNumber, new PickerView.OnItemSelectedListener<PickerView.PickerItem>() {
            @Override
            public void onItemSelected(PickerView.PickerItem item) {

                if (item.getText() == "x") { get_number = "";}
                else {
                    get_number = item.getText();
                }
                Toast.makeText(getBaseContext(),item.getText() + " is chosen",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private final class touchMe implements View.OnTouchListener{

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    dX = view.getX() - event.getRawX();
                    dY = view.getY() - event.getRawY();
                    lastAction = MotionEvent.ACTION_DOWN;

                    /*LinearLayout.LayoutParams params = new LinearLayout.
                            LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(10,10,10,10);
                    view.setLayoutParams(params);*/
                    break;

                case MotionEvent.ACTION_MOVE:
                    view.setY(event.getRawY() + dY);
                    view.setX(event.getRawX() + dX);
                    lastAction = MotionEvent.ACTION_MOVE;
                    break;

                case MotionEvent.ACTION_UP:
                    if (lastAction == MotionEvent.ACTION_DOWN)
//                        Toast.makeText(con, "Clicked!", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    return false;
            }
            return true;

        }
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


        song = SongUtil.asSong("Kisapmata","Rivermaya", lyrics);
        adapter = new VerseItemAdapter(song.getVerses());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        ItemTouchHelper.Callback callback = new EditItemTouchHelperCallback(adapter,recyclerView);
//        itemTouchHelper = new ItemTouchHelper(callback);
//        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);

        ((TextView)findViewById(R.id.etTitle)).setText(song.getSongTitle());
        ((TextView)findViewById(R.id.etArtist)).setText(song.getArtist());

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

                ChordItemAdapter getTheChord = new ChordItemAdapter();
                switch(v.getId()) {
                    case R.id.tv_dragChord1:
                        getTheChord.getChord
                                (tv_DragChord1.getText().toString() + get_accidental + get_scale + get_number);
                        break;
                    case R.id.tv_dragChord2:
                        getTheChord.getChord
                                (tv_DragChord2.getText().toString() + get_accidental + get_scale + get_number);
                        break;
                    case R.id.tv_dragChord3:
                        getTheChord.getChord
                                (tv_DragChord3.getText().toString() + get_accidental + get_scale + get_number);
                        break;
                    case R.id.tv_dragChord4:
                        getTheChord.getChord
                                (tv_DragChord4.getText().toString() + get_accidental + get_scale + get_number);
                        break;
                    case R.id.tv_dragChord5:
                        getTheChord.getChord
                                (tv_DragChord5.getText().toString() + get_accidental + get_scale + get_number);
                        break;
                    case R.id.tv_dragChord6:
                        getTheChord.getChord
                                (tv_DragChord6.getText().toString() + get_accidental + get_scale + get_number);
                        break;
                    case R.id.tv_dragChord7:
                        getTheChord.getChord
                                (tv_DragChord7.getText().toString() + get_accidental + get_scale + get_number);
                        break;
                }


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
