package com.cs2340.buzzfunds;

import org.json.simple.parser.ParseException;

/**
 * The Authenticator class performs authentication operations 
 * against a remote data source.
 * 
 * There should be one Authenticator object per authenticated
 * data source.
 * 
 * @author Sean Collins
 */
public class Authenticator {
	/**
	 * Contains connection endpoint information.
	 */
	private ConnectionProfile conn;
	/**
	 * Contains the username used for login.
	 */
	private String username;
	/**
	 * Contains the password used for login. (No getter method for security, please.)
	 */
	private String password;
	/**
	 * Constructs a new Authenticator object with an input 
	 * ConnectionProfile, username, and password.
	 * 
	 * @param profile A ConnectionProfile containing at least the server endpoint
	 * @param username The username to authenticate with.
	 * @param password The password to authenticate with.
	 */
	public Authenticator(ConnectionProfile profile, String username, String password) {
		this.conn = profile;
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Constructs a new Authenticator object with an input 
	 * ConnectionProfile and username. Note that some some methods 
	 * may fail without supplying additional information.
	 * 
	 * @param profile A ConnectionProfile containing at least the server endpoint
	 * @param username The username to authenticate with.
	 */
	public Authenticator(ConnectionProfile profile, String username) {
		this(profile, username, null);
	}
	
	/**
	 * Constructs a new Authenticator object with an input 
	 * ConnectionProfile. Note that some some methods 
	 * may fail without supplying additional information.
	 * 
	 * @param profile A ConnectionProfile containing at least the server endpoint
	 */
	public Authenticator(ConnectionProfile profile) {
		this(profile, null, null);
	}
	
	/**
	 * Attempts to authenticate by GETing credentials against the login endpoint.
	 * Requires authUserEndpoint in ConnectionProfile to be non-null.
	 * 
	 * @param successState The value to check the response of the server against to 
	 * 	determine if authentication was successful or not
	 * @return true if authentication was successful; false if invalid credentials
	 * or no authUserEndpoint declared in ConnectionProfile
	 */
	public boolean httpGetLogin(String successState) {
		String response = "";
		
		if (conn.authUserEndpoint != null && username != null && password != null) {
			response = BasicHttpClient.exeGet(conn.endpoint 
					+ conn.authUserEndpoint + "?username=" 
					+ username + "&password=" + password);
		}
		
		return response.substring(0,1).equals(successState);
	}
	
	/**
	 * Attempts to register a new user by GETing information against the register endpoint.
	 * Requires registerUserEndpoint in ConnectionProfile to be non-null.
	 * 
	 * @param successState The value to check the response of the server against to 
	 * 	determine if registration was successful or not
	 * @return true if registration was successful; false if invalid information
	 * or no registerUserEndpoint declared in ConnectionProfile
	 */
	public boolean httpGetRegister(String successState) {
		String response = "";
		
		if (conn.registerUserEndpoint != null && username != null && password != null) {
			response = BasicHttpClient.exeGet(conn.endpoint 
					+ conn.registerUserEndpoint + "?username="
					+ username + "&password=" + password);
		}
		
		return response.substring(0,1).equals(successState);
	}
	
	/**
	 * Attempts to add a new account by GETing information against the addAccount endpoint.
	 * Requires addAccountEndpoint in ConnectionProfile to be non-null.
	 * 
	 * @param id The accountID to identify this account as
	 * @param type The type of account (e.g. "savings," "checking," etc.)
	 * @return A new Account object generated from the JSON-encoded server response,
	 * or null if account add was unsuccessful or not enough information was supplied
	 */
	public Account httpGetAddAccount(String id, String type) {
		Account newAccount = null;
		
		if (conn.addAccountEndpoint != null) {
			String jsonResponse = BasicHttpClient.exeGet(conn.endpoint
					+ conn.addAccountEndpoint + "?account="
					+ id + "&user=" + username + "&balance=" 
					+ 0.00 + "&type=" + type);
			
		}
		
		return newAccount;
	}
	
	/**
	 * Attempts to sync a JSON array of accounts associated with this user from server.
	 * Requires syncAccountEndpoint in ConnectionProfile to be non-null.
	 * 
	 * @return An Account[] containing the decoded JSON response from the server, or 
	 * null if no Accounts exist or not enough information was supplied
	 */
	public Account[] httpGetSyncAccount() {
		Account[] accounts = null;
		
		if (conn.syncAccountEndpoint != null) {
			String jsonResponse = BasicHttpClient.exeGet(conn.endpoint 
					+ conn.syncAccountEndpoint + "?user=" + username);
			if (jsonResponse != "" && jsonResponse != null) {
				JSONMap map;
				if (jsonResponse != "" && jsonResponse != null) {
					try {
						map = new JSONMap(jsonResponse);
					} catch (Exception e) {
						return null; // Invalid or nonexistant data
					}

					accounts = new Account[map.size()];
					String[] ids = map.returnAllValues("name");
					String[] amounts = map.returnAllValues("amount");
					for (int i = 0; i < map.size(); i++) {
						accounts[i] = new Account(ids[i], this, Double.parseDouble(amounts[i]));
					}
				}
			}
		}
		
		return accounts;
	}
	
	/**
	 * Sets the username used by this Authenticator.
	 * 
	 * @param username The username to set in this Authenticator
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Sets the password used by this Authenticator.
	 * 
	 * @param password The password to set in this Authenticator
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Returns the username used by this Authenticator.
	 * 
	 * @return The username used by this Authenticator.
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Unimplemented for security reasons.
	 */
	protected void getPassword() {
		// Don't implement this (unless for testing!)
	}
	
	/**
	 * Returns the connection endpoint used in this Authenticator.
	 * @return The connection endpoint used in this Authenticator.
	 */
	public String getEndpoint() {
		return conn.endpoint;
	}
}
