package com.example.marvin.kuwerdas.tempo;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.marvin.kuwerdas.R;
import com.sdsmdg.harjot.crollerTest.Croller;
import com.sdsmdg.harjot.crollerTest.OnCrollerChangeListener;

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
        mMetronome = new Metronome();
        mMetronome.setTone(MediaPlayer.create(view.getContext(), R.raw.beat));

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
//        mCroller.setBackCircleColor(Color.parseColor("#EDEDED"));
//        mCroller.setMainCircleColor(Color.WHITE);
        mCroller.setMin(0);
        mCroller.setMax(400);
//        mCroller.setStartOffset(45);
        mCroller.setIsContinuous(true);
//        mCroller.setLabelColor(Color.BLACK);
//        mCroller.setProgressPrimaryColor(Color.parseColor("#0B3C49"));
//        mCroller.setIndicatorColor(Color.parseColor("#0B3C49"));
//        mCroller.setProgressSecondaryColor(Color.parseColor("#EEEEEE"));
//        mCroller.setProgressRadius(380);
//        mCroller.setBackCircleRadius(300);

        mCroller.setOnCrollerChangeListener(new OnCrollerChangeListener() {
            @Override
            public void onProgressChanged(Croller croller, int progress) {
                mMetronome.changeTempo(progress);
                mButtonTap.setText(String.valueOf(mMetronome.getTempo()));
            }

            @Override
            public void onStartTrackingTouch(Croller croller) {
                Toast.makeText(getActivity(), "Start", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(Croller croller) {
                Toast.makeText(getActivity(), "Stop", Toast.LENGTH_SHORT).show();
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
        mMetronome.stop();
        super.onPause();
    }
}