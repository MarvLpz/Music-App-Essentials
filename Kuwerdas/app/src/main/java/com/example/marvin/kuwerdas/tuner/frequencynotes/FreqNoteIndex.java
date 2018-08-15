package com.example.marvin.kuwerdas.tuner.frequencynotes;

import java.util.ArrayList;

import be.tarsos.dsp.util.PitchConverter;

public class FreqNoteIndex {
    private ArrayList<Octave> octaves = new ArrayList<>();


    public FreqNoteIndex() {
        init();
    }

    public ArrayList<Octave> getOctaves() {
        return octaves;
    }

    public void init() {

        octaves.add(new Octave(0, 16.35f, 31.78f));
        octaves.add(new Octave(1, 32.70f, 63.57f));
        octaves.add(new Octave(2, 65.41f, 127.15f));
        octaves.add(new Octave(3, 130.8f, 254.25f));
        octaves.add(new Octave(4, 261.6f, 508.6f));
        octaves.add(new Octave(5, 523.3f, 1017.4f));
        octaves.add(new Octave(6, 1047f, 2034.5f));
        octaves.add(new Octave(7, 2093f, 4068.5f));
        octaves.add(new Octave(8, 4186f, 7092f));

        setTuneStrings(0, 16.35f, 17.32f, 18.35f, 19.45f, 20.60f, 21.83f, 23.12f, 24.50f, 25.96f, 27.50f, 29.14f, 30.87f);
        setTuneStrings(1, 32.70f, 34.65f, 36.71f, 38.89f, 41.20f, 43.65f, 46.25f, 49.00f, 51.91f, 55.00f, 58.27f, 61.74f);
        setTuneStrings(2, 65.41f, 69.30f, 73.42f, 77.78f, 82.41f, 87.31f, 92.50f, 98.00f, 103.8f, 110.0f, 116.5f, 123.5f);
        setTuneStrings(3, 130.8f, 138.6f, 146.8f, 155.6f, 164.8f, 174.6f, 185.0f, 196.0f, 207.7f, 220.0f, 233.1f, 246.9f);
        setTuneStrings(4, 261.6f, 277.2f, 293.7f, 311.1f, 329.6f, 349.2f, 370.0f, 392.0f, 415.3f, 440.0f, 466.2f, 493.9f);
        setTuneStrings(5, 523.3f, 554.4f, 587.3f, 622.3f, 659.3f, 698.5f, 740.0f, 784.0f, 830.6f, 880.0f, 932.3f, 987.8f);
        setTuneStrings(6, 1047f, 1109f, 1175f, 1245f, 1319f, 1397f, 1480f, 1568f, 1661f, 1760f, 1865f, 1976f);
        setTuneStrings(7, 2093f, 2217f, 2349f, 2489f, 2637f, 2794f, 2960f, 3136f, 3322f, 3520f, 3729f, 3951f);
        setTuneStrings(8, 4186f, 4435f, 4699f, 4978f, 5274f, 5588f, 5920f, 6272f, 6645f, 7040f, 7459f, 7902f);
    }

    private void setTuneStrings(int octavePosition, float freq_C, float freq_C_sharp, float freq_D, float freq_E_flat, float freq_E, float freq_F, float freq_F_sharp, float freq_G, float freq_G_sharp, float freq_A, float freq_B_flat, float freq_B) {

        octaves.get(octavePosition).setNotes(new Note[]{
                new Note(Notation.C, freq_C),
                new Note(Notation.C_sharp, freq_C_sharp),
                new Note(Notation.D, freq_D),
                new Note(Notation.E_flat, freq_E_flat),
                new Note(Notation.E, freq_E),
                new Note(Notation.F, freq_F),
                new Note(Notation.F_sharp, freq_F_sharp),
                new Note(Notation.G, freq_G),
                new Note(Notation.G_sharp, freq_G_sharp),
                new Note(Notation.A, freq_A),
                new Note(Notation.B_flat, freq_B_flat),
                new Note(Notation.B, freq_B)
        });
    }
}
