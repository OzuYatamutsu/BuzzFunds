package com.cs2340.buzzfunds;

import java.text.NumberFormat;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class AccountDetailActivity extends Activity {

	Account account;
	TextView mTitleVal, mTypeVal, mBalanceVal;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_detail);
		mTitleVal = (TextView) findViewById(R.id.account_detail_name);
		mTypeVal = (TextView) findViewById(R.id.detail_type_val);
		mBalanceVal = (TextView) findViewById(R.id.detail_balance_val);
		if (IntentSingleton.keyExists("CURRENT_ACCOUNT")) {
			account = IntentSingleton.getAccount("CURRENT_ACCOUNT");
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

}
