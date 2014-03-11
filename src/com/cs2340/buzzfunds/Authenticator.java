package com.cs2340.buzzfunds;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.util.Log;

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
	 * @param successState The value to check the response of the server against to 
	 * 	determine if account creation was successful or not
	 * @return true if account creation was successful; false if invalid information
	 * or no addAccountEndpoint declared in ConnectionProfile
	 */
	@SuppressLint("SimpleDateFormat")
	public boolean httpGetAddAccount(String id, String type, String successState) {
		boolean result = false;
		
		if (conn.addAccountEndpoint != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String response = BasicHttpClient.exeGet(conn.endpoint
					+ conn.addAccountEndpoint + "?name="
					+ id + "&user=" + username + "&balance=" 
					+ 0.00 + "&type=" + type + "&interest="
					+ 0.00 + "&date=" + dateFormat.format(date));
			if (response.substring(0,1).equals(successState)) {
				result = true;
			}
		}
		
		return result;
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
				JSONObject object = null;
				JSONArray accountA = null;
				try {
					jsonResponse = "{accounts:"+jsonResponse+"}";
					object = new JSONObject(jsonResponse);
					accountA = object.getJSONArray("accounts");
					accounts = new Account[accountA.length()];
					for(int i = 0; i < accountA.length(); i++){
						JSONObject obj;
							obj = accountA.getJSONObject(i);
							accounts[i] = new Account(obj.getString("name"), this, calcBalance(obj.getJSONArray("transactionHistory")), obj.getString("type"));
					}
				} catch (Exception e) {
					return null; // Invalid or nonexistant data
				}
			}
		}
		return accounts;
	}
	
	private double calcBalance(JSONArray array){
		double balance = 0;
		for(int i = 0; i < array.length(); i++){
			try {
				JSONObject obj = array.getJSONObject(i);
				String deltaS = obj.getString("balance");
				double delta = Double.parseDouble(deltaS);
					balance += delta;
			} catch (JSONException e) {
				return -5000;
			}
		}
		return balance;
	}
	
	public boolean httpGetTransaction(String username, String account, String title, String delta, String type, String exeDate){
		return false;
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
