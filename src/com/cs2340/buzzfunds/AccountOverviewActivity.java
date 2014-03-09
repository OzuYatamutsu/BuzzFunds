package com.cs2340.buzzfunds;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class AccountOverviewActivity extends Activity {
	boolean isAuth;
	Bundle extras = null; //getIntent().getExtras();
	Intent intent = getIntent();
	String username;
	Authenticator endpoint = new Authenticator(DefaultConnection.BUZZFUNDS);
	Account[] accounts;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (extras != null) {
			isAuth = getIntent().getExtras().getBoolean("AUTH_STATE");
			username = getIntent().getExtras().getString("USERNAME");
		}
		
		
		
		/*if (populateAccounts()) {
			setContentView(R.layout.activity_account_overview);
		} else {
			setContentView(R.layout.activity_account_overview_empty);
		}
		
		if (!isAuth) {
			// User is not (yet) allowed to be here!
			Intent toLogin = new Intent(this, LoginActivity.class);
			toLogin.putExtra("AUTH_ERROR", true);
			startActivity(toLogin);
		}*/
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
	    super.onNewIntent(intent);
	    setIntent(intent);
	}

	/*public boolean testAdd() {
		Account account = addTestAccount("admin", "test11");
		if (account != null) {
			accounts = new Account[1];
			accounts[0] = account;
			return true;
		} else {
			return false;
		}
	}*/
	
	/*
	public Account addTestAccount(String username, String account) {
		Account response = endpoint.httpGetAddAccount(account, "checking");
		return response;
	}*/
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.account_overview, menu);
		return true;
	}

	/*private boolean populateAccounts() {
		boolean result = false;
		accounts = endpoint.httpGetSyncAccount();
		if (accounts != null) {
			result = true;
		}
		
		return result;
	}*/
	
}
