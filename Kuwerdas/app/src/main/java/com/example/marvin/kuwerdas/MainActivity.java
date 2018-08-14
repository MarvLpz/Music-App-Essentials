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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
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
        Song song = SongUtil.asSong("Kisapmata","Rivermaya",lyrics);
        new InsertSongDatabaseTask(song).execute();
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
        MenuItem searchViewItem = menu.findItem(R.id.action_search);

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
 
        switch (item.getItemId()) {
            case R.id.navigation_song:
                fragment = new SongFragment();
                break;
 
            case R.id.navigation_tempo:
                fragment = new TempoFragment();
                break;
 
            case R.id.navigation_tuner:
                fragment = new TunerFragment();
                break;
        }
 
        return loadFragment(fragment); 
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


    public void init(){
        //loading the default fragment
        loadFragment(new SongFragment());
        onChangeFragment = this;
        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        database = Room.databaseBuilder(getApplicationContext(), SongDatabase.class, DATABASE_NAME).build();
        (new SearchSongDatabaseTask()).execute();
    }
}