package com.ah.androidapp.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.ah.androidapp.R;
import com.ah.androidapp.util.CommonFunc;

public class SplashActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                CommonFunc.NavigateActivity(SplashActivity.this, MainActivity.class);
            }
        }).start();
    }
}
