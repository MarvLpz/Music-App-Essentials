package com.example.marvin.kuwerdas;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.marvin.kuwerdas.db.SongDatabase;
import com.example.marvin.kuwerdas.search.OnChangeFragment;
import com.example.marvin.kuwerdas.search.SearchFragment;
import com.example.marvin.kuwerdas.search.adapter.SongItemAdapter;
import com.example.marvin.kuwerdas.song.SongFragment;
import com.example.marvin.kuwerdas.song.model.Song;
import com.example.marvin.kuwerdas.song.util.SongUtil;
import com.example.marvin.kuwerdas.tempo.TempoFragment;
import com.example.marvin.kuwerdas.tuner.TunerFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, OnChangeFragment{

    public interface OnNewSearchResult {
        public boolean onNewSearchResult(List<Song> songs);
    }

    private static final String DATABASE_NAME = "SONG_DATABASE";

    public static OnNewSearchResult callback;
    public static OnChangeFragment onChangeFragment;
    private SongDatabase database;

    private SearchView searchViewAndroidActionBar;
    private MenuItem searchViewItem;

    private OnChangeFragment.Frags currentFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    @Override
    public void change(OnChangeFragment.Frags frag) {
        switch(frag){
            case SONG:
                loadFragment(new SongFragment());
                clearSearch();
                break;
            case TEMPO:
                loadFragment(new TempoFragment());
                clearSearch();
                break;
            case TUNER:
                loadFragment(new TunerFragment());
                clearSearch();
                break;
            case SEARCH:
                loadFragment(new SearchFragment());
                break;
        }

    }

    private void clearSearch(){
        searchViewAndroidActionBar.setQuery("",false);
        searchViewAndroidActionBar.clearFocus();
    }


    private boolean loadFragment(Fragment fragment) {
        //switching fragment 
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_view_menu_item, menu);
        searchViewItem = menu.findItem(R.id.action_search);

        searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchViewAndroidActionBar.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChangeFragment.change(Frags.SEARCH);
            }
        });
        
        searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchViewAndroidActionBar.clearFocus();
                loadFragment(new SearchFragment());
                currentFragment = Frags.SEARCH;
                (new SearchSongDatabaseTask(query)).execute();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                (new SearchSongDatabaseTask(newText)).execute();
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
     Fragment fragment = null;
     searchViewItem.collapseActionView();
        switch (item.getItemId()) {
            case R.id.navigation_song:
                if(currentFragment == Frags.SONG)
                    return false;
                fragment = new SongFragment();
                currentFragment = Frags.SONG;
                break;
 
            case R.id.navigation_tempo:
                if(currentFragment == Frags.TEMPO)
                    return false;

                fragment = new TempoFragment();
                currentFragment = Frags.TEMPO;
                break;
 
            case R.id.navigation_tuner:
                if(currentFragment == Frags.TUNER)
                    return false;

                fragment = new TunerFragment();
                currentFragment = Frags.TUNER;
                break;
        }
 
        return loadFragment(fragment); 
    }


    private void init(){
        //loading the default fragment
        loadFragment(new SearchFragment());
        currentFragment = Frags.SEARCH;

        onChangeFragment = this;
        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        database = Room.databaseBuilder(getApplicationContext(), SongDatabase.class, DATABASE_NAME).build();
        (new SearchSongDatabaseTask()).execute();
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
            if(callback!=null)
                callback.onNewSearchResult(songList);
        }
    }

    private class InsertSongDatabaseTask extends AsyncTask<Void,Void,Integer> {
        Song song;

        public InsertSongDatabaseTask(Song song){
            this.song = song;
        }


        @Override
        protected Integer doInBackground(Void... voids) {
            return database.songDao().insertSong(song);
        }

        @Override
        protected void onPostExecute(Integer id) {
        }
    }
}