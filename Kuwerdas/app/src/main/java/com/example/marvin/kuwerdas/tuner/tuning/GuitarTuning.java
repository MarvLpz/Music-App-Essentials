package com.example.marvin.kuwerdas.tuner.tuning;

public class GuitarTuning {

    public static double getCentInterval(float f1, float f2){
        return 1200 * Math.log(f2/f1) / Math.log(2);
    }
}
