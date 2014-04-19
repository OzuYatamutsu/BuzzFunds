package com.cs2340.buzzfunds;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.os.Handler;

public class SplashActivity extends Activity {

    /**
     * Amount of time to delay transition to MainActivity, in milliseconds
     */
    private final int SPLASH_DELAY = 500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Setup.ignoreMainNetworkException();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Create a new thread so splash screen pictures/text are visible
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                // Create Intent to switch to MainActivity at start
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DELAY);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}