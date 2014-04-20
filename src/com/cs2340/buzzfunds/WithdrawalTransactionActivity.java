package com.cs2340.buzzfunds;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

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
        // Button click
        SoundEffect.playSound(getApplicationContext(), R.raw.click);

        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
        LocalDate effDate = fmt.parseLocalDate(String.format("%s-%s-%s",
                mYear.getText().toString(), mMonth.getText().toString(), mDay.getText().toString()));

        double amount = Double.parseDouble(mAmount.getText().toString());
        if (account.MakeNewTransaction("withdrawal", amount, "w", "uncategorized", effDate)) {
            // All good!
            Intent intent = new Intent(this, AccountDetailActivity.class);
            startActivity(intent);
        } else {
            mAmount.setError(getString(R.string.error_transaction));
        }
    }
}
