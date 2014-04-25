package com.cs2340.buzzfunds;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


public class TransactionActivity extends Activity {
    Account account;
    EditText mAmount, mYear, mMonth, mDay, mTransactionName, mTransactionCategory;
    Intent intent;
    RadioGroup mTransactionType;
    RadioButton mTransactionDeposit;
    RadioButton mTransactionWithdrawal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        Setup.ignoreMainNetworkException();
        mAmount = (EditText) findViewById(R.id.amount);
        mYear = (EditText) findViewById(R.id.year);
        mMonth = (EditText) findViewById(R.id.month);
        mDay = (EditText) findViewById(R.id.day);
        mTransactionType = (RadioGroup) findViewById(R.id.transactionType);
        mTransactionDeposit = (RadioButton) findViewById(R.id.depositButton);
        mTransactionWithdrawal = (RadioButton) findViewById(R.id.withdrawalButton);
        mTransactionName = (EditText) findViewById(R.id.transactionName);
        mTransactionCategory = (EditText) findViewById(R.id.transactionCategory);

        if (IntentSingleton.keyExists("CURRENT_ACCOUNT")) {
            account = IntentSingleton.getAccount("CURRENT_ACCOUNT");
        } else {
            intent = new Intent(this, AccountOverviewActivity.class);
            startActivity(intent);
        }
    }

    private String getTransactionType() {
        int selectedId = mTransactionType.getCheckedRadioButtonId();
        RadioButton checked = (RadioButton) findViewById(selectedId);
        if (checked.getText().equals(mTransactionDeposit.getText())) {
            return "deposit";
        } else {
            return "withdrawal";
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.transaction, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void attemptTransaction(View view) {
        // Button click
        SoundEffect.playSound(getApplicationContext(), R.raw.click);

        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
        LocalDate effDate = fmt.parseLocalDate(String.format("%s-%s-%s",
                mYear.getText().toString(), mMonth.getText().toString(), mDay.getText().toString()));

        double amount = Double.parseDouble(mAmount.getText().toString());
        if (account.MakeNewTransaction(mTransactionName.getText().toString(), amount, getTransactionType(), mTransactionCategory.getText().toString(), effDate)) {
            // All good!
            Intent intent = new Intent(this, AccountDetailActivity.class);
            startActivity(intent);
        } else {
            mAmount.setError(getString(R.string.error_transaction));
        }
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
