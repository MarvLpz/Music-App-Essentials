package com.example.marvin.kuwerdas.tuner;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
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
import com.example.marvin.kuwerdas.tuner.frequencynotes.FreqNoteIndex;
import com.example.marvin.kuwerdas.tuner.frequencynotes.Note;
import com.example.marvin.kuwerdas.tuner.frequencynotes.Octave;
import com.example.marvin.kuwerdas.tuner.tuning.GuitarTuning;
import com.example.marvin.kuwerdas.tuner.tuning.Tune;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;

import static be.tarsos.dsp.pitch.PitchProcessor.PitchEstimationAlgorithm.FFT_YIN;

public class TunerFragment extends Fragment {
    private static final int RECORD_AUDIO_PERMISSION = 1;
    private TextView textLabel;
    private FreqNoteIndex freqNoteIndex = new FreqNoteIndex();
    private View view;
    private TunerView tunerView;

    private AudioDispatcher dispatcher;
    private PitchListener pitchListener;

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
        tunerView = (TunerView) view.findViewById(R.id.tunerView);
        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            tunerView.setVisibility(View.GONE);
            // Permission is not granted
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_AUDIO_PERMISSION);

        } else
            initializePitchDetector();
    }

    private void initializePitchDetector(){
        pitchListener = new PitchListener();
        tunerView.setVisibility(View.VISIBLE);
        tunerView.setDotPosition(2);
        pitchListener.execute();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RECORD_AUDIO_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initializePitchDetector();
                    Toast.makeText(Objects.requireNonNull(getActivity()).getBaseContext(), "Permission granted to record audio", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Objects.requireNonNull(getActivity()).getBaseContext(), "Permission denied to record audio", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(pitchListener!=null)
            pitchListener.cancel(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(pitchListener!=null)
            pitchListener.cancel(true);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (pitchListener!=null && pitchListener.isCancelled()) {
            pitchListener = new PitchListener();
            pitchListener.execute();
        }
    }

    public void displayNote(String pitch){
        textLabel.setText(pitch);
    }

    private class PitchListener extends AsyncTask<Void, Float, Void> {

        private AudioDispatcher audioDispatcher;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            PitchDetectionHandler pitchDetectionHandler = new PitchDetectionHandler() {
                @Override
                public void handlePitch(PitchDetectionResult pitchDetectionResult,
                                        AudioEvent audioEvent) {

                    if (isCancelled()) {
                        stop();
                        return;
                    }

                    float pitch = pitchDetectionResult.getPitch();

                    publishProgress(pitch);

                }
            };

            PitchProcessor pitchProcessor = new PitchProcessor(FFT_YIN, 44100,
                    1024 * 4, pitchDetectionHandler);

            audioDispatcher = AudioDispatcherFactory.fromDefaultMicrophone(44100,
                    1024*4, 0);

            audioDispatcher.addAudioProcessor(pitchProcessor);

            audioDispatcher.run();

            return null;
        }

        public Tune getClosestTune(float pitch){
            float diff = Float.MAX_VALUE;
            Note closestNote = null;
            String text = "";
            for(Octave octave : freqNoteIndex.getOctaves()) {
                if(pitch>=octave.getMin() && pitch<=octave.getMax()) {
                    for (Note note : octave.getNotes()) {
                        if (Math.abs((pitch - note.getFrequency())) < Math.abs(diff)) {
                            closestNote = note;
                            diff = note.getFrequency() - pitch;
                        }
                    }
                    break;
                }
            }


            return closestNote!=null ? new Tune(closestNote,pitch) : null;

        }

        protected void onProgressUpdate(Float... progress) {
            if(progress[0] == -1.0f) {
                tunerView.setDotPosition(-1);
                return;
            }
            Tune tune = getClosestTune(progress[0]);
            if(tune != null) {
                String note = tune.getClosestNote().getPitchNotation().toString();
                displayNote(note);
                tunerView.setDotPosition(2);
                setTunerViewDotPosition(tune.getCentInterval().intValue());
            } else {
                displayNote("");
                tunerView.setDotPosition(-1);
            }
        }

        private void stop() {
            if (audioDispatcher != null && !audioDispatcher.isStopped()) {
                audioDispatcher.stop();
            }

//            pitchList.clear();
        }
    }


    private void setTunerViewDotPosition(int value){
        if(value>=-10 && value<=10){
            tunerView.setDotPosition(2);
            Log.d("TUNERVIEW","SET DOT AT 2 - VALUE: " + value);
            return;
        }


        int[] marks = new int[]{-50,-25,25,50};
        int diff = Integer.MAX_VALUE;
        int closest = 0;
        for (int mark : marks) {
            if (diff > Math.abs(mark - value)) {
                closest = mark;
                diff = Math.abs(mark - value);
            }
        }

        if(closest == -50)
            tunerView.setDotPosition(4);
        else if(closest == -25)
            tunerView.setDotPosition(3);
        else if(closest == 25)
            tunerView.setDotPosition(1);
        else if(closest == 50)
            tunerView.setDotPosition(0);
        else
            tunerView.setDotPosition(2);
    }
}