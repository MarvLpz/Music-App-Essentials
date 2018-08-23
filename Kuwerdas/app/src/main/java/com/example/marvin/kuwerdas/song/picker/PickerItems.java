package com.example.marvin.kuwerdas.song.picker;

import com.example.marvin.kuwerdas.song.picker.model.Accidental;
import com.example.marvin.kuwerdas.song.picker.model.DetailedChord;
import com.example.marvin.kuwerdas.song.picker.model.DetailedChordIndex;
import com.example.marvin.kuwerdas.song.picker.model.Number;
import com.example.marvin.kuwerdas.song.picker.model.Scale;

import java.util.Arrays;
import java.util.List;

import top.defaults.view.PickerView;

public class PickerItems {
    public List<Accidental> pickerAccidentals = DetailedChordIndex.allAccidentals;
    public List<Scale> pickerScale = DetailedChordIndex.allScales;
    public List<Number> pickerNumber = DetailedChordIndex.defaultNumbers;
//    public List<DetailedChord> pickerChord = DetailedChordIndex.defaultNumbers;
}