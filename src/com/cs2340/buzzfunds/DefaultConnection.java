package com.cs2340.buzzfunds;

public enum DefaultConnection {
	/**
	 * A default connection profile associated with the Buzzfunds endpoint.
	 * 
	 * 
	 */
	BUZZFUNDS("http://buzzfunds.herokuapp.com", "/register", "/adduser", "/retrieveaccounts");
	
	public ConnectionProfile connProfile;
	
	private DefaultConnection(String endpoint, String registerUserEndpoint, 
			String authUserEndpoint, String syncAccountEndpoint) {
		connProfile = new ConnectionProfile(endpoint, registerUserEndpoint,
				authUserEndpoint, syncAccountEndpoint);
	}
}
