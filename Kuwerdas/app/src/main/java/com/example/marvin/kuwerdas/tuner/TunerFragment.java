package com.example.marvin.kuwerdas.tuner;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marvin.kuwerdas.R;

import java.util.ArrayList;
import java.util.Objects;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;


public class TunerFragment extends Fragment {
    private static final int RECORD_AUDIO_PERMISSION = 1;
//    private ArrayList<TuneString> tuning = new ArrayList<>();
    private TextView textLabel;
    private MusicChords musicChords = new MusicChords();
    private View view;
    private AudioDispatcher dispatcher;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        view = inflater.inflate(R.layout.fragment_tuner, null);

        init();
        return view;
    }


    private void init(){
        textLabel = (TextView) view.findViewById(R.id.tvTuner);


        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    RECORD_AUDIO_PERMISSION);

        } else
            initializePitchDetector();
    }

    private void initializePitchDetector(){
        dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050, 1024, 0);

        PitchDetectionHandler pdh = new PitchDetectionHandler() {
            @Override
            public void handlePitch(PitchDetectionResult result, AudioEvent e) {
                final float pitchInHz = result.getPitch();

                if(getActivity() != null)
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String closest = getClosestFrequency(pitchInHz);
                        if(pitchInHz!=-1.0f) {
                            Log.d("TAGGY", "the pitch is: " + pitchInHz + " - " + closest);
                        }
                        textLabel.setText(closest);
                    }
                });
            }
        };
        AudioProcessor p = new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, 22050, 1024, pdh);
        dispatcher.addAudioProcessor(p);
        new Thread(dispatcher, "Audio Dispatcher").start();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RECORD_AUDIO_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initializePitchDetector();
                }
                else {
                    Toast.makeText(Objects.requireNonNull(getActivity()).getBaseContext(), "Permission denied to record audio", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public String getClosestFrequency(float pitch){
        if(pitch==-1.0f)
            return "";


        float diff = Float.MAX_VALUE;
        String freq = "";
        for(Octave octave : musicChords.getOctaves()) {
            if(pitch>=octave.getMin() && pitch<=octave.getMax()) {
                for (TuneString ts : octave.getNotes()) {
                    if (Math.abs((pitch - ts.getFrequency())) < diff) {
                        diff = Math.abs(pitch - ts.getFrequency());
                        freq = ts.getPitchNotation() + octave.getNumber();
                    }
                }
                break;
            }
        }

        return freq;
    }

    @Override
    public void onPause() {
        dispatcher.stop();
        super.onPause();
    }
}