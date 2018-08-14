package com.example.marvin.kuwerdas.song;

import java.util.Arrays;
import java.util.List;

import top.defaults.view.PickerView;

public class Picker {

    List<PickerView.PickerItem> pickerAccidentals = Arrays.asList(
            new PickerView.PickerItem() {
                @Override
                public String getText() {
                    return "x";
                }
            },
            new PickerView.PickerItem() {
                @Override
                public String getText() {
                    return "#";
                }
            }
            ,
            new PickerView.PickerItem() {
                @Override
                public String getText() {
                    return "b";
                }
            }
    );

    List<PickerView.PickerItem> pickerScale = Arrays.asList(
            new PickerView.PickerItem() {
                @Override
                public String getText() {
                    return "x";
                }
            },
            new PickerView.PickerItem() {
                @Override
                public String getText() {
                    return "m";
                }
            }
            ,
            new PickerView.PickerItem() {
                @Override
                public String getText() {
                    return "sus";
                }
            }
    );

    List<PickerView.PickerItem> pickerNumber = Arrays.asList(
            new PickerView.PickerItem() {
                @Override
                public String getText() {
                    return "x";
                }
            },
            new PickerView.PickerItem() {
                @Override
                public String getText() {
                    return "6";
                }
            }
            ,
            new PickerView.PickerItem() {
                @Override
                public String getText() {
                    return "7";
                }
            }
            ,
            new PickerView.PickerItem() {
                @Override
                public String getText() {
                    return "9";
                }
            }
    );
}