package com.cs2340.buzzfunds;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Setup.ignoreMainNetworkException();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void switchToLogin(View view) {
        // Button click
        SoundEffect.playSound(getApplicationContext(), R.raw.click);
		Intent login = new Intent(this, LoginActivity.class);
		startActivity(login);
	}
	
	public void switchToRegister(View view) {
        // Button click
        SoundEffect.playSound(getApplicationContext(), R.raw.click);
		Intent register = new Intent(this, RegisterActivity.class);
		startActivity(register);
	}

    public void displayHelp(View view) {
        TextView helpButton = (TextView) findViewById(R.id.helpView);
        TextView helpText = (TextView) findViewById(R.id.helpText);
        if (helpText.getVisibility() == View.INVISIBLE) {
            helpButton.setText(R.string.global_help_label_off);
            helpText.setVisibility(View.VISIBLE);
        } else {
            helpButton.setText(R.string.global_help_label);
            helpText.setVisibility(View.INVISIBLE);
        }
    }


}