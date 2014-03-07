package com.cs2340.buzzfunds;

public enum DefaultConnection {
	/**
	 * A default connection profile associated with the Buzzfunds endpoint.<br><br>
	 * 
	 * Register: /register<br>
	 * Add user: /adduser<br>
	 * Add Account: /addaccount<br>
	 * Get Account(s): /retrieveaccounts
	 */
	BUZZFUNDS("http://buzzfunds.herokuapp.com", "/register", 
			"/adduser", "/addaccount", "/retrieveaccounts");
	
	public ConnectionProfile connProfile;
	
	private DefaultConnection(String endpoint, String registerUserEndpoint, 
			String authUserEndpoint, String addAccountEndpoint, 
			String syncAccountEndpoint) {
		connProfile = new ConnectionProfile(endpoint, registerUserEndpoint,
				authUserEndpoint, addAccountEndpoint, syncAccountEndpoint);
	}
}
