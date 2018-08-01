package com.example.marvin.kuwerdas.tempo;

import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Metronome {
    public static final String TAG = "TAGGY";
    private static final int LONGEST_INTERVAL = 3000;
    private static final int COUNT = 4;

    private int tempo;
    private long delay;

    private Long previousTime = null;

    private List<Beat> userTapList = new ArrayList<>();
    private MediaPlayer mp;

    private boolean isPlaying;

    private Handler handler = new Handler();
    private Runnable beatRunnable = new Runnable() {
        @Override
        public void run() {
//            Log.d(TAG,"Running at " + delay + " delay.");
            mp.start();
            handler.postDelayed(beatRunnable, delay);
        }
    };


    public void setTone(MediaPlayer mp){
        this.mp = mp;
    }

    public int getTempo() {
        return tempo;
    }

    public boolean changeTempo(int t){
        if(t>=20 && t<=400) {
            tempo = t;
            delay = 60000 / tempo;
            Log.d(TAG, "changeTempoByRV: new [tempo: " + tempo + "] [delay: " + delay + "]");
            startTimer();
            return true;
        }
        return false;
    }

    private void changeTempoByTaps(){
        long sum = 0;
        if(userTapList.size()>COUNT)
            userTapList = userTapList.subList(userTapList.size()-COUNT,userTapList.size());

        for(Beat b : userTapList){ sum +=b.getDelayMs(); }
        long beats = userTapList.size();

        delay = ((sum)/beats);
        tempo = (int) (60000/delay);

        if(tempo>400)
            tempo = 400;

        Log.d(TAG,"changeTempoByTaps: new [tempo: "+tempo + "] [delay: " + delay + "]");
    }

    private void startTimer(){
        handler.removeCallbacks(beatRunnable);
        handler.postDelayed(beatRunnable,delay);
        isPlaying = true;
    }

    public void stop(){
        handler.removeCallbacks(beatRunnable);
        tempo = 0;
        delay = 0;
        previousTime = null;
        isPlaying = false;
    }

    public void tap(){
        if (previousTime == null) {
            previousTime = System.currentTimeMillis();
        }
        else {
            Long currentTime = System.currentTimeMillis();
            long delay = currentTime-previousTime;
            if(delay<=LONGEST_INTERVAL) {
                userTapList.add(new Beat(delay));
                previousTime = currentTime;

                changeTempoByTaps();
                startTimer();

            } else {
                userTapList.clear();
                handler.removeCallbacks(beatRunnable);
                previousTime = null;
            }
        }
    }

    public boolean isPlaying() {
        return isPlaying;
    }
}