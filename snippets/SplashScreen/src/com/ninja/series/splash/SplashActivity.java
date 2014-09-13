package com.ninja.series.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

public class SplashActivity extends Activity {
    private static final long SPLASH_SCREEN_TIMEOUT = 5000;
    private Thread splashScreenThread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.splash_activity);
        this.startSplashScreen();
    }

    private void startSplashScreen() {
        this.splashScreenThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        splashScreenThread.sleep(SPLASH_SCREEN_TIMEOUT);
                    }
                } catch (InterruptedException e) {
                    showMainActivity();
                }finally {
                    showMainActivity();
                }
            }
        });

        this.splashScreenThread.start();
    }

    private void showMainActivity() {
        this.finish();
        this.startActivity(new Intent(SplashActivity.this, MainActivity.class));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            synchronized (splashScreenThread) {
                splashScreenThread.interrupt();
            }
        }
        return true;
    }
}
