package com.cs2340.buzzfunds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONValue;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class AccountOverviewActivity extends Activity {
	boolean isAuth = getIntent().getExtras().getBoolean("AUTH_STATE");
	String username = getIntent().getExtras().getString("USERNAME");
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
		Authenticator testAccountAdd = new Authenticator("https://buzzfunds.herokuapp.com");
		String response = testAccountAdd.httpGetCreateAccount(username, account, 100.00, "checking");
		// Below should be synced with server on login
		// someLoginFunctionHere();
		JSONObject jsonParse = (JSONObject)JSONValue.parse(response);
		try {
			return new Account(jsonParse.getString("_id"), testAccountAdd);
		} catch (JSONException e) {
			return null; // No JSON mapping exists (maybe account add failed?)
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.account_overview, menu);
		return true;
	}

	private boolean populateAccounts() {
		boolean result = false;
		Authenticator endpoint = new Authenticator("https://buzzfunds.herokuapp.com");
		JSONObject jsonParse = (JSONObject)JSONValue.parse(endpoint.httpGetSyncAccounts(username));
		//for (int i = 0; i < jsonParse.length(); i++) {
			
		//}
		accounts = new Account[1];
		try {
			accounts[0] = new Account(jsonParse.getString("key"), endpoint);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			// Swallows exceptio for now
			e.printStackTrace();
		}
		return result;
	}
	
	
}
