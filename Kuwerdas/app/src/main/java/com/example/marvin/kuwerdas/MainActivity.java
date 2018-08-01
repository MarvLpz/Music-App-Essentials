package com.example.marvin.kuwerdas;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.marvin.kuwerdas.tempo.TempoFragment;
import com.example.marvin.kuwerdas.tuner.TunerFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //loading the default fragment
        loadFragment(new SongFragment());
 
        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
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
}