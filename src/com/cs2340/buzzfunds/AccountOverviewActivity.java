package com.cs2340.buzzfunds;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AccountOverviewActivity extends Activity {
	boolean isAuth;
	Intent intent = getIntent();
    User user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Setup.ignoreMainNetworkException();
		if (IntentSingleton.keyExists("USERNAME")) {
            user = new User(IntentSingleton.getString("USERNAME"));
		}
		
		isAuth = IntentSingleton.getBoolean("AUTH_STATE");
		
		if (!isAuth) {
			// User is not (yet) allowed to be here!
			Intent toLogin = new Intent(this, LoginActivity.class);
			toLogin.putExtra("AUTH_ERROR", true);
			startActivity(toLogin);
		}
		
		if (user.HasAccounts()) {
			setContentView(R.layout.activity_account_overview);
			ArrayAdapter<Account> adapter = new ArrayAdapter<Account>(this,
			        android.R.layout.simple_list_item_1, (Account[]) user.getAccounts().toArray());
			ListView accountList = (ListView) findViewById(R.id.accountList);
			accountList.setAdapter(adapter);
			accountList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					switchToAccountDetail(position);
					
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
	 * Transitions to CreateAccountActivity on button press.
	 * 
	 * @param view The current View
	 */
	public void switchToCreateAccount(View view) {
		Intent intent = new Intent(this, CreateAccountActivity.class);
        intent.putExtra("user", user);
		startActivity(intent);
	}
	
	/**
	 * Transitions to AccountDetailActivity on ListItem click.
	 * 
	 * @param id The position of the ListItem
	 */
	public void switchToAccountDetail(int id) {
		Intent intent = new Intent(this, AccountDetailActivity.class);
		IntentSingleton.putAccount("CURRENT_ACCOUNT", (Account) user.getAccounts().toArray()[id]);
		startActivity(intent);
	}
	
}
