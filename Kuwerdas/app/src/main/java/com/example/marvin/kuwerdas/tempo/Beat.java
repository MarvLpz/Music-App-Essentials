package com.example.marvin.kuwerdas.tempo;

public class Beat {
    Long delayMs;

    public Beat(Long delayMs) {
        this.delayMs = delayMs;
    }

    public Long getDelayMs() {
        return delayMs;
    }

    public void setDelayMs(Long delayMs) {
        this.delayMs = delayMs;
    }

    @Override
    public String toString(){
        return delayMs.toString();
    }

}