package com.example.marvin.kuwerdas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class ManualActivity extends AppCompatActivity {
    ImageView image;
    srcImage source = srcImage.one;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);
        init();
    }

    enum srcImage{
        one,two,three,four,five,six,seven,eight,nine,ten,eleven,twelve
    }

    public void init(){
        image = (ImageView) findViewById(R.id.m1);



        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(source){
                    case one:
                        image.setImageResource(R.drawable.m2);
                        source = srcImage.two;
                        break;
                    case two:
                        image.setImageResource(R.drawable.m3);
                        source = srcImage.three;
                        break;
                    case three:
                        image.setImageResource(R.drawable.m4);
                        source = srcImage.four;
                        break;
                    case four:
                        image.setImageResource(R.drawable.m5);
                        source = srcImage.five;
                        break;
                        case five:
                        image.setImageResource(R.drawable.m6);
                        source = srcImage.six;
                        break;
                    case six:
                        image.setImageResource(R.drawable.m7);
                        source = srcImage.seven;
                        break;
                    case seven:
                        image.setImageResource(R.drawable.m8);
                        source = srcImage.eight;
                        break;
                    case eight:
                        image.setImageResource(R.drawable.m9);
                        source = srcImage.nine;
                        break;
                    case nine:
                        image.setImageResource(R.drawable.m10);
                        source = srcImage.ten;
                        break;
                    case ten:
                        image.setImageResource(R.drawable.m11);
                        source = srcImage.eleven;
                        break;
                    case eleven:
                        image.setImageResource(R.drawable.m12);
                        source = srcImage.twelve;
                        break;
                    case twelve:
                        source = srcImage.one;
                        Intent myIntent = new Intent(getBaseContext(),   MainActivity.class);
                        startActivity(myIntent);
                        break;
                }
            }
        });
    }
}
