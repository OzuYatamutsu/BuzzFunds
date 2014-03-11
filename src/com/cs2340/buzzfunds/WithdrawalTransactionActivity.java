package com.cs2340.buzzfunds;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class WithdrawalTransactionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Setup.ignoreMainNetworkException();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_withdrawal_transaction);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.withdrawal_transaction, menu);
		return true;
	}

}
