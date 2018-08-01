package com.example.marvin.kuwerdas.tuner;

import java.util.ArrayList;

public class MusicChords {
    private ArrayList<Octave> octaves = new ArrayList<>();


    public MusicChords() {
        init();
    }

    public ArrayList<Octave> getOctaves() {
        return octaves;
    }

    public void init(){
        octaves.add(new Octave(0,16.35f,30.87f));
        octaves.add(new Octave(1,32.70f,61.74f));
        octaves.add(new Octave(2,65.41f,123.5f));
        octaves.add(new Octave(3,130.8f,246.9f));
        octaves.add(new Octave(4,261.6f,493.9f));
        octaves.add(new Octave(5,523.3f,987.8f));
        octaves.add(new Octave(6,1047f,1976f));
        octaves.add(new Octave(7,2093f,3951f));
        octaves.add(new Octave(8,4186f,7092f));

        octaves.get(0).setNotes(new TuneString[]{
                new TuneString("C",16.35f),
                new TuneString("C#",17.32f),
                new TuneString("D",18.35f),
                new TuneString("Eb",19.45f),
                new TuneString("E",20.6f),
                new TuneString("F",21.83f),
                new TuneString("F#",23.12f),
                new TuneString("G",24.50f),
                new TuneString("G#",25.96f),
                new TuneString("A",27.50f),
                new TuneString("Bb",29.14f),
                new TuneString("B",30.87f)
        });

        octaves.get(1).setNotes(new TuneString[]{
                new TuneString("C",32.70f),
                new TuneString("C#",34.65f),
                new TuneString("D",36.71f),
                new TuneString("Eb",38.89f),
                new TuneString("E",41.20f),
                new TuneString("F",43.65f),
                new TuneString("F#",46.25f),
                new TuneString("G",49.00f),
                new TuneString("G#",51.91f),
                new TuneString("A",55.00f),
                new TuneString("Bb",58.27f),
                new TuneString("B",61.74f)
        });

        octaves.get(2).setNotes(new TuneString[]{
                new TuneString("C",65.41f),
                new TuneString("C#",69.30f),
                new TuneString("D",73.42f),
                new TuneString("Eb",77.78f),
                new TuneString("E",82.41f),
                new TuneString("F",87.31f),
                new TuneString("F#",92.50f),
                new TuneString("G",98.00f),
                new TuneString("G#",103.8f),
                new TuneString("A",110.0f),
                new TuneString("Bb",116.5f),
                new TuneString("B",123.5f)
        });

        octaves.get(3).setNotes(new TuneString[]{
                new TuneString("C",130.8f),
                new TuneString("C#",138.6f),
                new TuneString("D",146.8f),
                new TuneString("Eb",155.6f),
                new TuneString("E",164.8f),
                new TuneString("F",174.6f),
                new TuneString("F#",185.0f),
                new TuneString("G",196.0f),
                new TuneString("G#",207.7f),
                new TuneString("A",220.0f),
                new TuneString("Bb",233.1f),
                new TuneString("B",246.9f),

        });

        octaves.get(4).setNotes(new TuneString[]{
                new TuneString("C",261.6f),
                new TuneString("C#",277.2f),
                new TuneString("D",293.7f),
                new TuneString("Eb", 311.1f),
                new TuneString("E",329.6f),
                new TuneString("F",349.2f),
                new TuneString("F#",370.0f),
                new TuneString("G",392.0f),
                new TuneString("G#",415.3f),
                new TuneString("A",440.0f),
                new TuneString("Bb",466.2f),
                new TuneString("B",493.9f)
        });

        octaves.get(5).setNotes(new TuneString[]{
                new TuneString("C",523.3f),
                new TuneString("C#",554.4f),
                new TuneString("D",587.3f),
                new TuneString("Eb",622.3f),
                new TuneString("E",659.3f),
                new TuneString("F",698.5f),
                new TuneString("F#",740.0f),
                new TuneString("G",784.0f),
                new TuneString("G#",830.6f),
                new TuneString("A",880.0f),
                new TuneString("Bb",932.3f),
                new TuneString("B",987.8f)
        });

        octaves.get(6).setNotes(new TuneString[]{
                new TuneString("C",1047f),
                new TuneString("C#",1109f),
                new TuneString("D",1175f),
                new TuneString("Eb",1245f),
                new TuneString("E",1319f),
                new TuneString("F",1397f),
                new TuneString("F#",1480f),
                new TuneString("G",1568f),
                new TuneString("G#",1661f),
                new TuneString("A",1760f),
                new TuneString("Bb",1865f),
                new TuneString("B",1976f)
        });

        octaves.get(7).setNotes(new TuneString[]{
                new TuneString("C",2093f),
                new TuneString("C#",2217f),
                new TuneString("D",2349f),
                new TuneString("Eb",2489f),
                new TuneString("E",2637f),
                new TuneString("F",2794f),
                new TuneString("F#",2960f),
                new TuneString("G",3136f),
                new TuneString("G#",3322f),
                new TuneString("A",3520f),
                new TuneString("Bb",3729f),
                new TuneString("B",3951f)
        });

        octaves.get(8).setNotes(new TuneString[]{
                new TuneString("C",4186f),
                new TuneString("C#",4435f),
                new TuneString("D",4699f),
                new TuneString("Eb",4978f),
                new TuneString("E",5274f),
                new TuneString("F",5588f),
                new TuneString("F#",5920f),
                new TuneString("G",6272f),
                new TuneString("G#",6645f),
                new TuneString("A",7040f),
                new TuneString("Bb",7459f),
                new TuneString("B",7902f),
        });
    }
}
