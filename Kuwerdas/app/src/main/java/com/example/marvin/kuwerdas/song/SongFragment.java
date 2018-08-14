package com.example.marvin.kuwerdas.song;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.marvin.kuwerdas.OnStartDragListener;
import com.example.marvin.kuwerdas.R;
import com.example.marvin.kuwerdas.db.SongDatabase;
import com.example.marvin.kuwerdas.search.SearchFragment;
import com.example.marvin.kuwerdas.song.adapter.VerseItemAdapter;
import com.example.marvin.kuwerdas.song.model.Song;

import java.util.Objects;

public class SongFragment extends Fragment implements OnStartDragListener,SearchFragment.OnChangeSong {

    private VerseItemAdapter adapter;
    private RecyclerView recyclerView;
    private ItemTouchHelper itemTouchHelper;
    private final String DATABASE_NAME = "SONG_DATABASE";
    private SongDatabase database;
    public static Song song;
    private View view;

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
        SearchFragment.callback = this;
        return view;
    }


    private void init(){
        database = Room.databaseBuilder(Objects.requireNonNull(getActivity()), SongDatabase.class, DATABASE_NAME).build();
        recyclerView = view.findViewById(R.id.rvSong);

//        Bundle bundle = this.getArguments();
//        if (bundle != null) {
//            song = (Song) bundle.getSerializable("Song");
//        }

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        if(song!=null){
            Log.d("SONG","found song" + song);
            new GetSongDetailsDatabaseTask().execute();
            onChangeSong(song);
        }
        else
            Log.d("SONG", "no song found");


    }

    public void instantiate(){
/*        tv_DragChord1 = (TextView) view.findViewById(R.id.tv_dragChord1);
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
        linearLayout = (LinearLayout) findViewById(R.id.frame_place);
        linearLayout.setOnTouchListener(new touchMe());
        linearLayout.setVisibility(View.INVISIBLE);*/
    }
    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        itemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onChangeSong(Song item) {
        song = item;
        adapter = new VerseItemAdapter(song.getVerses());
        recyclerView.setAdapter(adapter);
    }

    private class GetSongDetailsDatabaseTask extends AsyncTask<Void,Void,Song> {

        @Override
        protected Song doInBackground(Void... voids) {
            return database.songDao().getSongWithVerses(song.getUid());
        }

        @Override
        protected void onPostExecute(Song s) {
            super.onPostExecute(s);
            onChangeSong(s);
        }
    }


}
