package com.cs2340.buzzfunds;

import java.text.NumberFormat;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class AccountDetailActivity extends Activity {

	Account account;
	TextView mTitleVal, mTypeVal, mBalanceVal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		Setup.ignoreMainNetworkException();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_detail);
		mTitleVal = (TextView) findViewById(R.id.account_detail_name);
		mTypeVal = (TextView) findViewById(R.id.detail_type_val);
		mBalanceVal = (TextView) findViewById(R.id.detail_balance_val);
		if (IntentSingleton.keyExists("CURRENT_ACCOUNT")) {
			account = IntentSingleton.getAccount("CURRENT_ACCOUNT");
            account.UpdateInterest();
			mTitleVal.setText(account.getId());
			mTypeVal.setText(account.getType());
			mBalanceVal.setText(NumberFormat.getCurrencyInstance().format(account.getBalance()));
		} else {
			// Go back to AccountOverviewActivity, no information to display
			Intent intent = new Intent(this, AccountOverviewActivity.class);
			startActivity(intent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.account_detail, menu);
		return true;
	}
	
	/**
	 * Transitions to ChooseTransactioNTypeActivity.
	 * 
	 * @param view The current View.
	 */
	public void switchToChooseTransactionTypeActivity(View view) {
        // Button click
        SoundEffect.playSound(getApplicationContext(), R.raw.click);

		//Intent intent = new Intent(this, ChooseTransactionTypeActivity.class);
        Intent intent = new Intent(this, TransactionActivity.class);
		IntentSingleton.putAccount("CURRENT_ACCOUNT", account);
		startActivity(intent);
	}
	
	/**
	 * Transitions to HistoryActivity.
	 * 
	 * @param view The current View.
	 */
    public void switchToHistoryActivity(View view) {
        // Button click
        SoundEffect.playSound(getApplicationContext(), R.raw.click);

        Intent intent = new Intent(this, HistoryActivity.class);
        IntentSingleton.putAccount("CURRENT_ACCOUNT", account);
        startActivity(intent);
    }

    public void switchToEditAccountActivity(View view) {
        // Button click
        SoundEffect.playSound(getApplicationContext(), R.raw.click);

        Intent intent = new Intent(this, EditAccountActivity.class);
        IntentSingleton.putAccount("CURRENT_ACCOUNT", account);
        startActivity(intent);
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

    /**
     * Deletes the current account.
     *
     * @param view The current View.
     */
    public void deleteAccount(View view) {
        // Prepare to go back to account overview
        Intent intent = new Intent(this, AccountOverviewActivity.class);
        boolean success = NetworkActivities.deleteAccount(IntentSingleton.getString("USERNAME"), account);
        startActivity(intent);
    }
}
