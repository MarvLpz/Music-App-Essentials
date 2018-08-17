package com.example.marvin.kuwerdas.song;

import android.arch.persistence.room.Room;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marvin.kuwerdas.db.DatabaseUtils;
import com.example.marvin.kuwerdas.song.adapter.itemtouch.OnStartDragListener;
import com.example.marvin.kuwerdas.R;
import com.example.marvin.kuwerdas.db.SongDatabase;
import com.example.marvin.kuwerdas.search.SearchFragment;
import com.example.marvin.kuwerdas.song.adapter.VerseItemAdapter;
import com.example.marvin.kuwerdas.song.model.Chord;
import com.example.marvin.kuwerdas.song.model.Line;
import com.example.marvin.kuwerdas.song.model.Song;
import com.example.marvin.kuwerdas.song.model.Verse;
import com.example.marvin.kuwerdas.song.util.SongUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class SongFragment extends Fragment implements OnStartDragListener,SearchFragment.OnChangeSong {

    private VerseItemAdapter adapter;
    private RecyclerView recyclerView;
    private ItemTouchHelper itemTouchHelper;
    private SongDatabase database;
    private View view;

    private static Song song;
    private static boolean isLoadedFromDB = false;
    public static boolean isSongEdited = false;

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
            return database.songDao().getSongWithVerses(song.getUid());
        }

        @Override
        protected void onPostExecute(Song s) {
            super.onPostExecute(s);
            onChangeSong(s);
        }
    }

    private void saveSongToDatabase() {
        if (song != null && isSongEdited) {
            song.setDateModified((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).format(new Date()));

            if (!isLoadedFromDB) {
                (new DatabaseUtils.InsertSongDatabaseTask(song)).execute();
                isLoadedFromDB = true;
            } else {
                (new DatabaseUtils.UpdateSongDatabaseTask(song)).execute();
            }

            Toast.makeText(getContext(), "Saved changes to \'" + song.getSongTitle() + "\'", Toast.LENGTH_SHORT).show();
            isSongEdited = false;
        }
    }
}
