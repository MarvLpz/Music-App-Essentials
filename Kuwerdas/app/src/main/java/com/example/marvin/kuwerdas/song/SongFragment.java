package com.example.marvin.kuwerdas.song;

import android.content.ClipData;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.marvin.kuwerdas.db.SongDatabaseUtils;
import com.example.marvin.kuwerdas.song.adapter.itemtouch.OnStartDragListener;
import com.example.marvin.kuwerdas.R;
import com.example.marvin.kuwerdas.db.SongDatabase;
import com.example.marvin.kuwerdas.search.SearchFragment;
import com.example.marvin.kuwerdas.song.adapter.ChordItemAdapter;
import com.example.marvin.kuwerdas.song.adapter.VerseItemAdapter;
import com.example.marvin.kuwerdas.song.model.Chord;
import com.example.marvin.kuwerdas.song.model.Line;
import com.example.marvin.kuwerdas.song.model.Song;
import com.example.marvin.kuwerdas.song.model.Verse;

import java.text.SimpleDateFormat;
import java.util.Date;

import top.defaults.view.PickerView;

public class SongFragment extends Fragment implements OnStartDragListener,SearchFragment.OnChangeSong {

    private VerseItemAdapter adapter;
    private RecyclerView recyclerView;
    private ItemTouchHelper itemTouchHelper;
    private SongDatabase database;
    private View view;

    private static Song song;
    private static boolean isLoadedFromDB = false;
    public static boolean isSongEdited = false;

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

//    private BottomNavigationView mBottomNavigationView;

    LinearLayout linearLayout;

    //    private ItemTouchHelper itemTouchHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        view = inflater.inflate(R.layout.fragment_song, null);
        init();
        instantiate();
        showPicker();
        SearchFragment.callback = this;

        return view;
    }

    private void init(){

        database = SongDatabase.getSongDatabase(getContext());

        recyclerView = view.findViewById(R.id.rvSong);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        (view.findViewById(R.id.tvNoSong)).setVisibility(song==null ? View.VISIBLE : View.GONE);
        if(song!=null){
            Log.d("SONG","found song" + song);
            if(isLoadedFromDB) {
                new GetSongDetailsDatabaseTask().execute();
            }
            onChangeSong(song);
        }

    }

    @Override
    public void onDetach() {
        saveSongToDatabase();
//        song = null;
        super.onDetach();
    }

    @Override
    public void onPause() {
        saveSongToDatabase();
//        song = null;
        super.onPause();
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

    public void Transpose(){
        Log.d("SONG","song: " + song.getArtist() + " - " + song.getSongTitle());
        for(Verse verse : song.getVerses()){
            Log.d("SONG","verse " + verse.getUid() + ": " + verse.getTitle());
            for(Line line : verse.getLines()){
                String chordset = "";
                for(Chord chord : line.getChordSet()){
                    chord.setChord(Transposer.transposeChordUp(chord.getChord()));
                    chordset += "[" + chord.getChord() + "]";
                }
                Log.d("SONG","line: " + line.getLyrics());
                Log.d("SONG","chords: " + chordset);

            }
        }
        adapter.notifyDataSetChanged();
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
//                FloatDelete.setClickable(false);
            }
            else {
                linearLayout.setVisibility(View.INVISIBLE);
                clicked = false;
                FloatAdd.setAlpha(127);
//                FloatDelete.setClickable(true);
            }

        }
    }
    public class floatdelBtn implements View.OnClickListener{
        boolean delClicked = false;
        @Override
        public void onClick(View v) {
            Transpose();

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
    public void instantiate(){

        tv_DragChord1 = (TextView) view.findViewById(R.id.tv_dragChord1);
        tv_DragChord2 = (TextView) view.findViewById(R.id.tv_dragChord2);
        tv_DragChord3 = (TextView) view.findViewById(R.id.tv_dragChord3);
        tv_DragChord4 = (TextView) view.findViewById(R.id.tv_dragChord4);
        tv_DragChord5 = (TextView) view.findViewById(R.id.tv_dragChord5);
        tv_DragChord6 = (TextView) view.findViewById(R.id.tv_dragChord6);
        tv_DragChord7 = (TextView) view.findViewById(R.id.tv_dragChord7);
        tv_DragChord1.setOnTouchListener(new ChoiceTouchListener());
        tv_DragChord2.setOnTouchListener(new ChoiceTouchListener());
        tv_DragChord3.setOnTouchListener(new ChoiceTouchListener());
        tv_DragChord4.setOnTouchListener(new ChoiceTouchListener());
        tv_DragChord5.setOnTouchListener(new ChoiceTouchListener());
        tv_DragChord6.setOnTouchListener(new ChoiceTouchListener());
        tv_DragChord7.setOnTouchListener(new ChoiceTouchListener());

        FloatAdd = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        FloatDelete = (FloatingActionButton) view.findViewById(R.id.floatingDeleteButton);
        FloatAdd.setOnClickListener(new floataddBtn());
        FloatAdd = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        FloatDelete.setOnClickListener(new floatdelBtn());
        FloatAdd.setAlpha(127);
        FloatDelete.setAlpha(80);

        linearLayout = (LinearLayout) view.findViewById(R.id.frame_place);
        linearLayout.setOnTouchListener(new touchMe());
        linearLayout.setVisibility(View.INVISIBLE);
    }

    public void showPicker() {
        Picker picker = new Picker();
        PickerView accidental = view.findViewById(R.id.accidental);

        accidental.setTextSize(40);

        accidental.setItems(picker.pickerAccidentals, new PickerView.OnItemSelectedListener<PickerView.PickerItem>() {
            @Override
            public void onItemSelected(PickerView.PickerItem item) {
                if (item.getText() == "x") { get_accidental = "";}
                else {
                    get_accidental = item.getText();
                }

//                Toast.makeText(this.getBaseContext(),item.getText() + " is chosen",Toast.LENGTH_SHORT).show();
            }
        });

        PickerView scale = view.findViewById(R.id.scale);
        scale.setTextSize(40);

        scale.setItems(picker.pickerScale, new PickerView.OnItemSelectedListener<PickerView.PickerItem>() {
            @Override
            public void onItemSelected(PickerView.PickerItem item) {
                if (item.getText() == "x") {get_scale = ""; }
                else {
                    get_scale = item.getText();
                }
//                Toast.makeText(getBaseContext(),item.getText() + " is chosen",Toast.LENGTH_SHORT).show();
            }
        });

        PickerView number = view.findViewById(R.id.number);
        number.setTextSize(40);
        number.setItems(picker.pickerNumber, new PickerView.OnItemSelectedListener<PickerView.PickerItem>() {
            @Override
            public void onItemSelected(PickerView.PickerItem item) {

                if (item.getText() == "x") { get_number = "";}
                else {
                    get_number = item.getText();
                }
//                Toast.makeText(getBaseContext(),item.getText() + " is chosen",Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        itemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onChangeSong(Song item) {
        if(item!=null){
            song = item;
            isLoadedFromDB = song.getUid()!=0;
            adapter = new VerseItemAdapter(song.getVerses());
            recyclerView.setAdapter(adapter);
            isSongEdited = false;
        }
        else
            isLoadedFromDB = false;
    }

    private class GetSongDetailsDatabaseTask extends AsyncTask<Void,Void,Song> {

        @Override
        protected Song doInBackground(Void... voids) {
            if(song!=null)
                return database.songDao().getSongWithVerses(song.getUid());

            return null;
        }

        @Override
        protected void onPostExecute(Song s) {
            super.onPostExecute(s);
            if(s!=null)
                onChangeSong(s);
        }
    }
    private void saveSongToDatabase() {
        if (song != null && isSongEdited) {
            song.setDateModified((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).format(new Date()));

            if (!isLoadedFromDB) {
                (new SongDatabaseUtils.InsertSongDatabaseTask(song)).execute();
                isLoadedFromDB = true;
            } else {
                (new SongDatabaseUtils.UpdateSongDatabaseTask(song)).execute();
            }

            Toast.makeText(getContext(), "Saved changes to \'" + song.getSongTitle() + "\'", Toast.LENGTH_SHORT).show();
            isSongEdited = false;
        }
    }
}
