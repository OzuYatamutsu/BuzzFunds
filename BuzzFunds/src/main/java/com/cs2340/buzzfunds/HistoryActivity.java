package com.cs2340.buzzfunds;

import java.util.*;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleExpandableListAdapter;

public class HistoryActivity extends Activity {
	
	Account account;
	ListView mHistoryView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Setup.ignoreMainNetworkException();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);
		if (IntentSingleton.keyExists("CURRENT_ACCOUNT")) {
			account = IntentSingleton.getAccount("CURRENT_ACCOUNT");
		}
		
		mHistoryView = (ListView) findViewById(R.id.historyView);
		populateHistoryView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.history, menu);
		return true;
	}
	
	/**
	 * Populates the ScrollView with the current Account's history.
	 */
	private void populateHistoryView() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
		        android.R.layout.simple_list_item_1, (LinkedList<String>) account.getSimpleHistory());
		mHistoryView.setAdapter(adapter);
	}

}
