package com.cs2340.buzzfunds;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AccountOverviewActivity extends Activity {
	boolean isAuth;
	Intent intent = getIntent();
	String username;
	Authenticator endpoint = new Authenticator(DefaultConnection.BUZZFUNDS);
	Account[] accounts;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (IntentSingleton.keyExists("USERNAME")) {
			username = IntentSingleton.getString("USERNAME");
			endpoint.setUsername(username);
		}
		
		isAuth = IntentSingleton.getBoolean("AUTH_STATE");
		
		if (!isAuth) {
			// User is not (yet) allowed to be here!
			Intent toLogin = new Intent(this, LoginActivity.class);
			toLogin.putExtra("AUTH_ERROR", true);
			startActivity(toLogin);
		}
		
		if (populateAccounts()) {
			setContentView(R.layout.activity_account_overview);
			ArrayAdapter<Account> adapter = new ArrayAdapter<Account>(this, 
			        android.R.layout.simple_list_item_1, accounts);
			ListView accountList = (ListView) findViewById(R.id.accountList);
			accountList.setAdapter(adapter);
			accountList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Log.d("click event", "the button: " + id + " was pressed at " + position);
					
				}
			});
		} else {
			setContentView(R.layout.activity_account_overview_empty);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.account_overview, menu);
		return true;
	}

	/**
	 * Populates accounts by contacting server.
	 * 
	 * @return true if the local accounts store was populated; false otherwise
	 */
	private boolean populateAccounts() {
		boolean result = false;
		accounts = endpoint.httpGetSyncAccount();
		if (accounts != null) {
			result = true;
		}
		
		return result;
	}
	
	/**
	 * Transitions to CreateAccountActivity on button press.
	 * 
	 * @param view The current View
	 */
	public void switchToCreateAccount(View view) {
		Intent intent = new Intent(this, CreateAccountActivity.class);
		startActivity(intent);
	}
	
}
