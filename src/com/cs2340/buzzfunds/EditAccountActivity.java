package com.cs2340.buzzfunds;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class EditAccountActivity extends Activity {
    Account account;
    Intent intent;
    boolean interestRateChanged, nameChanged;
    EditText mEditAccountName, mEditAccountInterest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        mEditAccountName = (EditText) findViewById(R.id.editAccountName);
        mEditAccountInterest = (EditText) findViewById(R.id.editAccountInterest);
        if (IntentSingleton.keyExists("CURRENT_ACCOUNT")) {
            account = IntentSingleton.getAccount("CURRENT_ACCOUNT");
        } else {
            intent = new Intent(this, AccountOverviewActivity.class);
            startActivity(intent);
        }

        populateFields();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_account, menu);
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

    public void populateFields() {
        mEditAccountName.setText(account.getId());
        mEditAccountInterest.setText(String.valueOf(account.getInterest()));
    }

    private void checkForChanges() {
        if (!mEditAccountName.getText().toString().equals(account.getId())) {
            nameChanged = true;
        }

        if (Double.parseDouble(mEditAccountInterest.getText().toString()) != account.getInterest()) {
            interestRateChanged = true;
        }
    }

    public void submitChanges(View view) {
        checkForChanges();
        if (nameChanged) {
            NetworkActivities.editAccountName(IntentSingleton.getString("USERNAME"),
                    account, mEditAccountName.getText().toString());
        }

        if (interestRateChanged) {
            NetworkActivities.editInterest(IntentSingleton.getString("USERNAME"),
                    account, Double.parseDouble(mEditAccountInterest.getText().toString()));
        }

        intent = new Intent(this, AccountOverviewActivity.class);
        startActivity(intent);
    }

}
