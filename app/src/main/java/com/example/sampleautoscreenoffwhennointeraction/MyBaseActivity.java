package com.example.sampleautoscreenoffwhennointeraction;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.view.WindowManager;


public class MyBaseActivity extends Activity {

    public static final long DISCONNECT_TIMEOUT = 5000; // 300000 -> 5 min = 5 * 60 * 1000 ms

    /*private static Handler disconnectHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            // todo
            return true;
        }
    });*/

    private void turnOnScreen(){
        // turn on screen
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.screenBrightness = -1;
        getWindow().setAttributes(params);
    }

    private void turnOffScreen(){
        // turn off screen
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.screenBrightness = 0;
        getWindow().setAttributes(params);
    }

    private final Handler disconnectHandler = new Handler(Looper.getMainLooper());

    private Runnable disconnectCallback = new Runnable() {
        @Override
        public void run() {
            // Perform any required operation on disconnect
            turnOffScreen();
        }
    };

    public void resetDisconnectTimer(){
        disconnectHandler.removeCallbacks(disconnectCallback);
        disconnectHandler.postDelayed(disconnectCallback, DISCONNECT_TIMEOUT);
    }

    public void stopDisconnectTimer(){
        disconnectHandler.removeCallbacks(disconnectCallback);
    }

    @Override
    public void onUserInteraction(){
        resetDisconnectTimer();
        turnOnScreen();
    }

    @Override
    public void onResume() {
        super.onResume();
        resetDisconnectTimer();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopDisconnectTimer();
    }
}