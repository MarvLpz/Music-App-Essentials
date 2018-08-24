package com.example.marvin.kuwerdas;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marvin.kuwerdas.db.SongDatabaseUtils;
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

    public static OnNewSearchResult SearchResultListener;
    public static OnChangeFragment FragmentSwitcher;
    private SongDatabase database;

    private SearchView searchViewAndroidActionBar;
    private MenuItem searchViewItem;

    private OnChangeFragment.Frags currentFragment = null;

    TempoFragment tempoFragment;
    SongFragment songFragment;
    TunerFragment tunerFragment;

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tempoFragment = new TempoFragment();
        songFragment = new SongFragment();
        tunerFragment = new TunerFragment();

        init();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);

//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setIcon(R.drawable.tuner);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
//        getSupportActionBar().setLogo(R.mipmap.ic);
        getSupportActionBar().setLogo(R.mipmap.iconlegit);

        View headerLayout = nvDrawer.inflateHeaderView(R.layout.nav_header);

/*        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_draw);
// Inflate the header view at runtime
        View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header);
// We can now look up items within the header if needed
        ImageView ivHeaderPhoto = headerLayout.findViewById(R.id.imageView);*/
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                FragmentSwitcher.change(Frags.SEARCH);
                searchViewItem.setVisible(true);
                fragmentClass = SearchFragment.class;
                break;
            case R.id.nav_second_fragment:
                FragmentSwitcher.change(Frags.TEMPO);
                searchViewItem.setVisible(false);
                fragmentClass = TempoFragment.class;
                break;
            case R.id.nav_third_fragment:
                searchViewItem.setVisible(false);
                FragmentSwitcher.change(Frags.TUNER);
                fragmentClass = TunerFragment.class;
                break;
            default:
                searchViewItem.setVisible(true);
                fragmentClass = SearchFragment.class;
        }

//
//        try {
//            fragment = (Fragment) fragmentClass.newInstance();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // Insert the fragment by replacing any existing fragment
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void init(){

        database = SongDatabase.getSongDatabase(this);
        SongDatabaseUtils.initialize(database);


/*        Objects.requireNonNull(ViewTools.findActionBarTitle(getWindow().getDecorView())).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentSwitcher.change(Frags.SEARCH);
            }
        });*/

        loadFragment(songFragment);
        loadFragment(new SearchFragment());
        currentFragment = Frags.SEARCH;
        FragmentSwitcher = this;
        FragmentSwitcher.change(Frags.SEARCH);
        //getting bottom navigation view and attaching the listener
/*        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);*/

    }


    @Override
    public void change(Frags frag) {
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

        if(getSupportActionBar()!=null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(frag != Frags.SONG) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
                getSupportActionBar().show();
                getWindow().getDecorView().setSystemUiVisibility(0);
            }
            else {
                getSupportActionBar().hide();
                getWindow().setStatusBarColor(Color.WHITE);
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
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
                    .addToBackStack(null)
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
        switch (item.getItemId()) {
            case R.id.navigation_song:
                if(currentFragment == Frags.SONG)
                    return false;

//                fragment = new SongFragment();
                currentFragment = Frags.SONG;
                break;
 
            case R.id.navigation_tempo:
                if(currentFragment == Frags.TEMPO)
                    return false;

//                fragment = new TempoFragment();
                currentFragment = Frags.TEMPO;
                break;
 
            case R.id.navigation_tuner:
                if(currentFragment == Frags.TUNER)
                    return false;

//                fragment = new TunerFragment();
                currentFragment = Frags.TUNER;
                break;
        }

        FragmentSwitcher.change(currentFragment);
        return true;
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
        if(currentFragment.equals(Frags.TEMPO)){
            FragmentSwitcher.change(Frags.SONG);

            return;
        }
//        super.onBackPressed();
    }

    public interface OnNewSearchResult {
        public boolean onNewSearchResult(List<Song> songs);
    }
}