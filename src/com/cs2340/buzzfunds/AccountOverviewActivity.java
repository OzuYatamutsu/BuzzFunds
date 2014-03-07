package com.cs2340.buzzfunds;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class AccountOverviewActivity extends Activity {
	boolean isAuth = getIntent().getExtras().getBoolean("AUTH_STATE");
	String username = getIntent().getExtras().getString("USERNAME");
	Authenticator endpoint = new Authenticator(DefaultConnection.BUZZFUNDS.connProfile);
	Account[] accounts;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (populateAccounts()) {
			setContentView(R.layout.activity_account_overview);
		} else {
			setContentView(R.layout.activity_account_overview_empty);
		}
		
		if (!isAuth) {
			// User is not (yet) allowed to be here!
			Intent toLogin = new Intent(this, LoginActivity.class);
			toLogin.putExtra("AUTH_ERROR", true);
			startActivity(toLogin);
		}
	}

	public boolean testAdd() {
		Account account = addTestAccount("admin", "test11");
		if (account != null) {
			accounts = new Account[1];
			accounts[0] = account;
			return true;
		} else {
			return false;
		}
	}
	
	public Account addTestAccount(String username, String account) {
		Account response = null;
		response = endpoint.httpGetAddAccount(account, "checking");
		return response;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.account_overview, menu);
		return true;
	}

	private boolean populateAccounts() {
		boolean result = false;
		// Rewrite this
		return result;
	}
	
	
}
