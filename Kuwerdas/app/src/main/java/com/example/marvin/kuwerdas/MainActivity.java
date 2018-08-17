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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.marvin.kuwerdas.db.DatabaseUtils;
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
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, OnChangeFragment{

    TempoFragment tempoFragment;
    SongFragment songFragment;
    TunerFragment tunerFragment;

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

        tempoFragment = new TempoFragment();
        songFragment = new SongFragment();
        tunerFragment = new TunerFragment();

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
                currentFragment = Frags.SONG;
                loadFragment(songFragment);
                clearSearch();
                break;
            case TEMPO:
                currentFragment = Frags.TEMPO;
                loadFragment(tempoFragment);
                clearSearch();
                break;
            case TUNER:
                currentFragment = Frags.TUNER;
                loadFragment(tunerFragment);
                clearSearch();
                break;
            case SEARCH:
                currentFragment = Frags.SEARCH;
                loadFragment(new SearchFragment());
                (new SearchSongDatabaseTask()).execute();
                break;
        }

    }

    private void clearSearch(){
        searchViewAndroidActionBar.setQuery("",false);
        searchViewAndroidActionBar.clearFocus();
    }


    private boolean loadFragment(Fragment fragment) {
        if(searchViewItem!=null)
            searchViewItem.collapseActionView();
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

        database = SongDatabase.getSongDatabase(this);
        DatabaseUtils.initialize(database);


        Objects.requireNonNull(ViewTools.findActionBarTitle(getWindow().getDecorView())).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChangeFragment.change(Frags.SEARCH);
            }
        });

        loadFragment(songFragment);
        loadFragment(new SearchFragment());
        currentFragment = Frags.SEARCH;
        onChangeFragment = this;
        onChangeFragment.change(Frags.SEARCH);
        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

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

    @Override
    public void onBackPressed() {

        if(currentFragment.equals(Frags.SONG)){
            onChangeFragment.change(Frags.SEARCH);
            return;
        }

        super.onBackPressed();
    }
}