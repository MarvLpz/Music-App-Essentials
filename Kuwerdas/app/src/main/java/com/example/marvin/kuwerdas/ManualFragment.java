package com.example.marvin.kuwerdas;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class ManualFragment extends Fragment {

    private View view;
    ImageView one,two,three,four,five,six,seven,eight,nine,ten,eleven,twelve;

    public ManualFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_manual, null);
        init();
        return view;
//        return inflater.inflate(R.layout.fragment_manual, container, false);

    }

    public void init(){
        one = (ImageView) view.findViewById(R.id.m1);
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Image","Clicked");
            }
        });
    }
}
