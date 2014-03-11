package com.cs2340.buzzfunds;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class WithdrawalTransactionActivity extends Activity {
	Account account;
	EditText mAmount, mYear, mMonth, mDay;
	Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Setup.ignoreMainNetworkException();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_withdrawal_transaction);
		mAmount = (EditText) findViewById(R.id.amount);
		mYear = (EditText) findViewById(R.id.year);
		mMonth = (EditText) findViewById(R.id.month);
		mDay = (EditText) findViewById(R.id.day);
		
		if (IntentSingleton.keyExists("CURRENT_ACCOUNT")) {
			account = IntentSingleton.getAccount("CURRENT_ACCOUNT");
		} else {
			intent = new Intent(this, AccountOverviewActivity.class);
			startActivity(intent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.withdrawal_transaction, menu);
		return true;
	}
	
	public void attemptTransaction(View view) {
		String date = mYear.getText() + "-" + mMonth.getText() + "-" + mDay.getText();
		
		if (queueTransaction(date)) {
			if (pushTransaction(date)) {
				// All good!
				Intent intent = new Intent(this, AccountDetailActivity.class);
				startActivity(intent);
			} else {
				mAmount.setError(getString(R.string.error_transaction));
			}
		} else {
			mAmount.setError(getString(R.string.error_transaction));
			
		}
		
		
	}
	
	private boolean queueTransaction(String date) {
		boolean result = true;
		
		double amount = Double.parseDouble(mAmount.getText().toString());
		Transaction transaction = new Transaction(account, amount, date, "withdrawal");
		// Title is just date for now
		account.queue(transaction);
		
		return result;
	}
	
	private boolean pushTransaction(String date) {
		boolean result = false;
		
		if (account.pushNeeded()) {
			result = account.push(date);
		}
		
		return result;
	}

}
