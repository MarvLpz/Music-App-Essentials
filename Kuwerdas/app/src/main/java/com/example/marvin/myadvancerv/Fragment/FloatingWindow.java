package com.example.marvin.myadvancerv.Fragment;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.marvin.myadvancerv.song.model.Line;

public class FloatingWindow extends Service {
    private WindowManager wm;
    private LinearLayout ll;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();

        wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        ll = new LinearLayout(this);
        LinearLayout.LayoutParams llparameters = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        ll.setBackgroundColor(Color.argb(66,255,0,0));
        ll.setLayoutParams(llparameters);
        WindowManager.LayoutParams parameters = new WindowManager.LayoutParams
                (400, 150, WindowManager.LayoutParams.TYPE_PHONE,WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
        parameters.x = 0;
        parameters.y = 0;
        parameters.gravity = Gravity.CENTER | Gravity.CENTER;
        wm.addView(ll, parameters);

    }
}
