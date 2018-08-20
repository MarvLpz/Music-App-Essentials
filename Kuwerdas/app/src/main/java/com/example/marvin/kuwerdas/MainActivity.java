package com.example.marvin.kuwerdas;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.marvin.kuwerdas.db.DatabaseUtils;
import com.example.marvin.kuwerdas.db.SongDatabase;
import com.example.marvin.kuwerdas.search.SearchFragment;
import com.example.marvin.kuwerdas.song.SongFragment;
import com.example.marvin.kuwerdas.song.model.Song;
import com.example.marvin.kuwerdas.song.util.SongUtil;
import com.example.marvin.kuwerdas.tempo.TempoFragment;
import com.example.marvin.kuwerdas.tuner.TunerFragment;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, OnChangeFragment{

    private static final String DATABASE_NAME = "SONG_DATABASE";

    public static OnNewSearchResult SearchResultListener;
    public static OnChangeFragment FragmentSwitcher;
    private SongDatabase database;

    private SearchView searchViewAndroidActionBar;
    private MenuItem searchViewItem;

    private OnChangeFragment.Frags currentFragment = null;

    TempoFragment tempoFragment;
    SongFragment songFragment;
    TunerFragment tunerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tempoFragment = new TempoFragment();
        songFragment = new SongFragment();
        tunerFragment = new TunerFragment();

        init();

    }

    private void init(){

        database = SongDatabase.getSongDatabase(this);
        DatabaseUtils.initialize(database);


        Objects.requireNonNull(ViewTools.findActionBarTitle(getWindow().getDecorView())).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentSwitcher.change(Frags.SEARCH);
            }
        });

        loadFragment(songFragment);
        loadFragment(new SearchFragment());
        currentFragment = Frags.SEARCH;
        FragmentSwitcher = this;
        FragmentSwitcher.change(Frags.SEARCH);
        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

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
                FragmentSwitcher.change(Frags.SEARCH);
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
            if(SearchResultListener!=null)
                SearchResultListener.onNewSearchResult(songList);
        }
    }

    @Override
    public void onBackPressed() {

        if(currentFragment.equals(Frags.SONG)){
            FragmentSwitcher.change(Frags.SEARCH);
            return;
        }

        super.onBackPressed();
    }

    public interface OnNewSearchResult {
        public boolean onNewSearchResult(List<Song> songs);
    }
}