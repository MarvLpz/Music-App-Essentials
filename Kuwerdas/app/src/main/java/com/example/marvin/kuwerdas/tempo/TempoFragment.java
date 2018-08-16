package com.example.marvin.kuwerdas.tempo;


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

import com.example.marvin.kuwerdas.R;
import com.sdsmdg.harjot.crollerTest.Croller;
import com.sdsmdg.harjot.crollerTest.OnCrollerChangeListener;

import java.util.Arrays;

import top.defaults.view.PickerView;

import static android.content.Context.VIBRATOR_SERVICE;

public class TempoFragment extends Fragment {


    private static final String TAG = "TAGGY";

    private AudioManager audioManager;
    private ToneGenerator toneGenerator;
    private EditText mTextTempo;
    private Metronome mMetronome;
    private Button mButtonTap;
    private Croller mCroller;


    private Vibrator mVibrator;
    private View view;
    private PickerView mPicker;

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

    int crollerPreviousValue = 0;

    private void init(){
        mMetronome = new Metronome();
/*        if (mMetronome.counter == mMetronome.maxcount){
//            mMetronome.setTone(MediaPlayer.create(view.getContext(), R.raw.beephigh));
            Log.d("TRY LOG MEDIA", "HIGH");
        }
        else{
//            mMetronome.setTone(MediaPlayer.create(view.getContext(), R.raw.beeplow));
            Log.d("TRY LOG MEDIA", "LOW");
        }*/

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

        mCroller = (Croller) view.findViewById(R.id.croller);
//        mCroller.setIndicatorWidth(10);
        mCroller.setBackCircleColor(Color.parseColor("#8CA7A6A7"));
        mCroller.setMainCircleColor(Color.parseColor("#8CA7A6A7"));
        mCroller.setMin(20);
        mCroller.setMax(250);
        mCroller.setStartOffset(20);
        mCroller.setIsContinuous(true);
//        mCroller.setLabelColor(Color.BLACK);
        mCroller.setProgressPrimaryColor(Color.parseColor("#df8b37"));
        mCroller.setIndicatorColor(Color.parseColor("#FAB36E"));
        mCroller.setProgressSecondaryColor(Color.parseColor("#818181"));
        mCroller.setProgressRadius(365);
        mCroller.setBackCircleRadius(400);
        mCroller.setProgress(0);
        mCroller.setOnCrollerChangeListener(new OnCrollerChangeListener() {
            boolean isTouched = false;
            @Override
            public void onProgressChanged(Croller croller, int progress) {
                if(isTouched) {
                    if (mMetronome.changeTempo(progress)) {
                        mButtonTap.setText(String.valueOf(mMetronome.getTempo()));
                        crollerPreviousValue = progress;
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
    }

    public void onClickButton(){
        mMetronome.tap();
        mCroller.setProgress(mMetronome.getTempo());
        mButtonTap.setText(String.valueOf(mMetronome.getTempo()));
        mVibrator.vibrate(50);
    }

    public void onLongClickButton(){
        if(mMetronome.isPlaying()) {
            mMetronome.stop();
            mButtonTap.setText(String.valueOf(mMetronome.getTempo()));
            mVibrator.vibrate(500);
            mCroller.setProgress(mMetronome.getTempo());
            Toast.makeText(getActivity(), "Stopped playing", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPause(){
        super.onPause();

//        mMetronome.stop();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mMetronome.stop();
    }
}