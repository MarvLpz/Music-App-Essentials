package com.example.marvin.myadvancerv;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;

public class LineLayout extends LinearLayout{

    private GridLayout mChordGridLayout;
    private EditText mLyricLineEditText;

    public LineLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public LineLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        inflate(getContext(), R.layout.line_layout, this);
        setOrientation(VERTICAL);
        mChordGridLayout = (GridLayout)findViewById(R.id.glChords);
        mLyricLineEditText = (EditText)findViewById(R.id.etLine);
        mChordGridLayout.setColumnCount(10);

        for(int i=0;i<10;i++)
            mChordGridLayout.addView(new ChordCell(getContext()));

    }

    public void setLyricLine(String lyrics){
        mLyricLineEditText.setText(lyrics);
    }
}
