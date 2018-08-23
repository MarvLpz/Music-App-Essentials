package com.example.marvin.kuwerdas.tempo;


import android.animation.Animator;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.appolica.flubber.Flubber;
import com.example.marvin.kuwerdas.R;
import com.example.marvin.kuwerdas.db.SongDatabaseUtils;
import com.example.marvin.kuwerdas.song.SongFragment;
import com.sdsmdg.harjot.crollerTest.Croller;
import com.sdsmdg.harjot.crollerTest.OnCrollerChangeListener;

import java.util.Arrays;

import top.defaults.view.PickerView;

import static android.content.Context.VIBRATOR_SERVICE;

public class TempoFragment extends Fragment implements BeatListener{

    private static final String TAG = "TAGGY";

    private AudioManager audioManager;
    private ToneGenerator toneGenerator;
    private EditText mTextTempo;
    private Metronome mMetronome;
    private Button mButtonTap;
    private Croller mCroller;


    private Vibrator mVibrator;
    private View view;
    private Animator animator;
    private PickerView mPicker;

    private int lastTempo = 0;
    private int crollerPreviousValue = 0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        view = inflater.inflate(R.layout.fragment_tempo, null);
        init();
        return view;
    }

    private void init(){
        mMetronome = new Metronome(this);

        mMetronome.setToneLow(MediaPlayer.create(view.getContext(), R.raw.beeplow));
        mMetronome.setToneHigh(MediaPlayer.create(view.getContext(), R.raw.beephigh));
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        mButtonTap = (Button) view.findViewById(R.id.btnTap);
        mButtonTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickButton();
            }
        });

        mButtonTap.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onLongClickButton();
                return true;
            }
        });

        mVibrator = (Vibrator)getActivity().getSystemService(VIBRATOR_SERVICE);
        animator = Flubber.with().duration(100)
                .animation(Flubber.AnimationPreset.ZOOM_OUT)
                .repeatCount(1)
                .createFor(view.findViewById(R.id.btnFade));

        mCroller = (Croller) view.findViewById(R.id.croller);
        mCroller.setBackCircleColor(Color.parseColor("#8CA7A6A7"));
        mCroller.setMainCircleColor(Color.parseColor("#8CA7A6A7"));
        mCroller.setMin(20);
        mCroller.setMax(Metronome.MAX_TEMPO);
        mCroller.setIsContinuous(false);
        mCroller.setProgressPrimaryColor(Color.parseColor("#df8b37"));
        mCroller.setIndicatorColor(Color.parseColor("#FAB36E"));
        mCroller.setProgressSecondaryColor(Color.parseColor("#818181"));
//        mCroller.setProgressRadius(365);
//        mCroller.setBackCircleRadius(400);
        mCroller.setProgress(0);
        mCroller.setIndicatorWidth(0);
        mCroller.setOnCrollerChangeListener(new OnCrollerChangeListener() {
            boolean isTouched = false;
            @Override
            public void onProgressChanged(Croller croller, int progress) {
                if(isTouched) {
                    if (mMetronome.changeTempo(progress)) {
                        mButtonTap.setText(String.valueOf(mMetronome.getTempo()));
                        crollerPreviousValue = progress;
                        lastTempo = mMetronome.getTempo();

                    } else
                        mCroller.setProgress(crollerPreviousValue);
                }
            }

            @Override
            public void onStartTrackingTouch(Croller croller) {
                isTouched = true;
            }

            @Override
            public void onStopTrackingTouch(Croller croller) {
                isTouched = false;
            }
        });

        mPicker = (PickerView) view.findViewById(R.id.pv_timeSig);
        mPicker.setItemHeight(100);
        mPicker.setTextSize(60);
        mPicker.setTextColor(Color.GRAY);
        mPicker.setCurved(true);
        mPicker.setItems(Arrays.asList(TimeSignature.four_four,TimeSignature.six_eight,TimeSignature.two_four), new PickerView.OnItemSelectedListener<TimeSignature>() {
            @Override
            public void onItemSelected(TimeSignature item) {
                mMetronome.setTimeSignature(item.getText());
            }
        });

        if(SongFragment.song!=null) {
            mMetronome.changeTempo(SongFragment.song.getTempo());
            lastTempo = mMetronome.getTempo();
            mButtonTap.setText(String.valueOf(lastTempo));
            mCroller.setProgress(lastTempo);
        }
    }

    public void onClickButton(){
        mMetronome.tap();
        onBeatStart();
        lastTempo = mMetronome.getTempo();
        mCroller.setProgress(mMetronome.getTempo());
        mButtonTap.setText(String.valueOf(mMetronome.getTempo()));
        mVibrator.vibrate(50);
    }

    public void onLongClickButton(){
        if(mMetronome.isPlaying()) {
            lastTempo = mMetronome.getTempo();
            mMetronome.stop();
            mButtonTap.setText(String.valueOf(mMetronome.getTempo()));
            mVibrator.vibrate(500);
            mCroller.setProgress(mMetronome.getTempo());
            Toast.makeText(getActivity(), "Stopped playing", Toast.LENGTH_SHORT).show();
        }
        else {
            mVibrator.vibrate(500);
            mMetronome.changeTempo(lastTempo);
            mButtonTap.setText(String.valueOf(lastTempo));
            mCroller.setProgress(lastTempo);
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        changeSongTempoValue();
//        mMetronome.stop();
    }

    private boolean changeSongTempoValue(){
        if(SongFragment.song!=null)
        {
            SongFragment.song.setTempo(lastTempo);
            (new SongDatabaseUtils.UpdateSongDatabaseTask(SongFragment.song)).execute();
            return true;
        }
        return false;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        changeSongTempoValue();
        mMetronome.stop();
    }

    @Override
    public void onBeatStart() {
        animator.start();
    }

    @Override
    public void onBeatEnd() {
//        mVibrator.vibrate(200);
    }
}