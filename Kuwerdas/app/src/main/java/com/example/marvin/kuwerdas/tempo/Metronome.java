package com.example.marvin.kuwerdas.tempo;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.marvin.kuwerdas.db.SongDatabase;
import com.example.marvin.kuwerdas.db.SongDatabaseUtils;
import com.example.marvin.kuwerdas.song.SongFragment;
import com.example.marvin.kuwerdas.song.model.Song;

import java.util.ArrayList;
import java.util.List;

public class Metronome {
    public static final String TAG = "TAGGY";
    private static final int LONGEST_INTERVAL = 3000;
    private static final int COUNT = 4;
    public static final int MIN_TEMPO = 20;
    public static final int MAX_TEMPO = 400;
    static String TimeSig = "4/4";
    private int tempo;
    private long delay;

    private Long previousTime = null;
    private BeatListener beatListener;

    private List<Beat> userTapList = new ArrayList<>();
    private MediaPlayer mpL;
    private MediaPlayer mpH;

    private boolean isPlaying;

    private Handler handler = new Handler();
    private int counter;


    public Metronome(BeatListener beatListener){
        this.beatListener = beatListener;
    }

    private int getTimeSigMaxCount(){
        if(TimeSig.equals("4/4"))
            return 4;
        else if (TimeSig.equals("6/8"))
            return 6;
        else if (TimeSig.equals("2/4"))
            return 2;
        else
            return -1;
    }

    private Runnable beatRunnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(beatRunnable, delay);


            int maxCount = getTimeSigMaxCount();

            if (counter >= maxCount){
                counter = 0;
                Log.d("COUNT", "TICK");
                mpH.start();
                beatListener.onBeatEnd();
            }
            else
                mpL.start();

            beatListener.onBeatStart();

            counter++;
            Log.d("COUNT", String.valueOf(counter));
        }

    };

    public void setTimeSignature(String GetTimeSig){
        TimeSig = GetTimeSig;
        if(isPlaying)
            startTimer();
    }

    public void setToneLow(MediaPlayer mp){
        this.mpL = mp;
    }
    public void setToneHigh(MediaPlayer mp){
        this.mpH = mp;
    }
    public int getTempo() {
        return tempo;
    }
    public long getDelay() {
        return delay;
    }

    public boolean changeTempo(int t){
        if(t>=MIN_TEMPO && t<=MAX_TEMPO) {
            tempo = t;
            delay = 60000 / tempo;
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

        if(tempo>MAX_TEMPO) {
            tempo = MAX_TEMPO;
            delay = 60000/tempo;
        }
    }

    private void startTimer(){
        handler.removeCallbacks(beatRunnable);
        handler.postDelayed(beatRunnable,delay);
        isPlaying = true;
    }

    public void stop(){
        handler.removeCallbacks(beatRunnable);
        userTapList.clear();
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