package com.example.marvin.kuwerdas.search;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.marvin.kuwerdas.MainActivity;
import com.example.marvin.kuwerdas.R;
import com.example.marvin.kuwerdas.db.SongDatabase;
import com.example.marvin.kuwerdas.search.adapter.SongItemAdapter;
import com.example.marvin.kuwerdas.song.SongActivity;
import com.example.marvin.kuwerdas.song.model.Song;

import java.util.List;

public class SearchFragment extends Fragment implements SongItemAdapter.RecyclerViewItemClickListener{

    public interface OnClickSearchItem{
        boolean onClickSearchItem(Song item);
    }

    private OnClickSearchItem callback;
    private static final String DATABASE_NAME = "SONG_DATABASE";

    private RecyclerView rvSearchResults;
    private SongItemAdapter adapter;
    private SongDatabase database;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        view = inflater.inflate(R.layout.fragment_search, null);
        init();
        return view;
    }

    public void init(){
        rvSearchResults = (RecyclerView) view.findViewById(R.id.rvSearchResults);
        rvSearchResults.setLayoutManager(new LinearLayoutManager(view.getContext()));

        database = Room.databaseBuilder(view.getContext(), SongDatabase.class, DATABASE_NAME).build();

        adapter = new SongItemAdapter(this);
        rvSearchResults.setAdapter(adapter);

        (new SearchSongDatabaseTask()).execute();
    }



    @Override
    public void recyclerViewListItemClicked(View v, int position) {
        Log.d("TAGGY","item clicked at position: " + position);
//        Intent mIntent = new Intent(this, SongActivity.class);
//        mIntent.putExtra("Song", adapter.getSong(position));
//
//        startActivity(mIntent);
    }

    private class SearchSongDatabaseTask extends AsyncTask<Void,Void,List<Song>> {
        String search;

        public SearchSongDatabaseTask(String search){
            this.search = search;
        }

        public SearchSongDatabaseTask(){
            this.search = "";
        }


        @Override
        protected List<Song> doInBackground(Void... voids) {
            search = "%" + search + "%";
            return database.songDao().getSongsFromSearch(search);
        }

        @Override
        protected void onPostExecute(List<Song> songList) {
            adapter.updateItems(songList);
        }
    }
}
