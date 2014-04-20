package com.cs2340.buzzfunds;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class ChooseTransactionTypeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Setup.ignoreMainNetworkException();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_transaction_type);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose_transaction_type, menu);
		return true;
	}
	
	/**
	 * Transitions to DepositTransactionActivity.
	 * 
	 * @param view The current View.
	 */
	public void switchToDepositTransactionActivity(View view) {
        // Button click
        SoundEffect.playSound(getApplicationContext(), R.raw.click);

		Intent intent = new Intent(this, DepositTransactionActivity.class);
		startActivity(intent);
	}
	
	/**
	 * Transitions to WithdrawalTransactionActivity.
	 * 
	 * @param view The current View.
	 */
	public void switchToWithdrawalTransactionActivity(View view) {
        // Button click
        SoundEffect.playSound(getApplicationContext(), R.raw.click);

		Intent intent = new Intent(this, WithdrawalTransactionActivity.class);
		startActivity(intent);
	}
	
	/**
	 * Transitions to TransferTransactionActivity.
	 * 
	 * @param view The current View.
	 */
	public void switchToTransferTransactionActivity(View view) {
        // Button click
        SoundEffect.playSound(getApplicationContext(), R.raw.click);

		Intent intent = new Intent(this, TransferTransactionActivity.class);
		startActivity(intent);
	}

}
