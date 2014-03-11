package com.cs2340.buzzfunds;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class DepositTransactionActivity extends Activity {

	boolean isAuth;
	Intent intent = getIntent();
	String username;
	private UserDepositTask mAuthTask = null;
	Authenticator endpoint = new Authenticator(DefaultConnection.BUZZFUNDS);
	Account account;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Setup.ignoreMainNetworkException();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deposit_transaction);
		
		if(IntentSingleton.keyExists("USERNAME")){
			username = IntentSingleton.getString("USERNAME");
			endpoint.setUsername(username);
		}
		if(IntentSingleton.keyExists("CURRENT_ACCOUNT")){
			account = IntentSingleton.getAccount("CURRENT_ACCOUNT");
		}
		else
		{
			intent = new Intent(this, AccountOverviewActivity.class);
			startActivity(intent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.deposit_transaction, menu);
		return true;
	}
	
	public class UserDepositTask extends AsyncTask<Void, Void, Boolean>{

		@Override
		protected Boolean doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
