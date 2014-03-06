package com.cs2340.buzzfunds;

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
	 * The network endpoint of the authentication source.
	 */
	private String endpoint;
	/**
	 * The default login endpoint.
	 */
	private static final String LOGIN_ENDPOINT = "/login";
	/**
	 * The default register endpoint.
	 */
	private static final String REGISTER_ENDPOINT = "/register";
	/**
	 * The default account add endpoint.
	 */
	private static final String ADDACCOUNT_ENDPOINT = "/addaccount";
	/**
	 * The default sync endpoint.
	 */
	private static final String SYNC_ENDPOINT = "/retrieveaccounts";
	/**
	 * Constructs a new Authenticator with a given network endpoint.
	 * 
	 * @param endpoint A provided network endpoint
	 */
	public Authenticator(String endpoint) {
		this.endpoint = endpoint;
	}
	
	/**
	 * Attempts to authenticate against the remote endpoint by GETting 
	 * provided credentials against a web server.
	 * 
	 * Authenticates against (endpoint + "/login") by default.
	 * 
	 * @param username The provided username
	 * @param password The provided password
	 * @param successState The value to check the response of the server against to 
	 * 	determine if authentication was successful or not
	 * @return true if authentication was successful; else false
	 */
	public boolean httpLoginGetAuth(String username, String password, 
			String successState) {
		return httpLoginGetAuth(username, password, successState, LOGIN_ENDPOINT);
	}
	
	/**
	 * Attempts to authenticate against the remote endpoint by GETting 
	 * provided credentials against a web server. The loginEndpoint provided
	 * is appended to the network endpoint in the authentication request.
	 * 
	 * @param username The provided username
	 * @param password The provided password
	 * @param successState The value to check the response of the server against to 
	 * 	determine if authentication was successful or not
	 * @param loginEndpoint Appended to URI to authenticate against (e.g. "/login")
	 * @return true if authentication was successful; else false
	 */
	public boolean httpLoginGetAuth(String username, String password, 
			String successState, String loginEndpoint) {
		String response;
		
		//note '/login' is slightly different than the preivous '/loginserver'
		response = BasicHttpClient.exeGet(endpoint + loginEndpoint + "?username=" + 
				username + "&password=" + password);
		

		//Couldn't get the POST quite functional so we will just rely on GET for now
		//postParameters.add(new BasicNameValuePair("username", username));
		//postParameters.add(new BasicNameValuePair("password", password));
		//response = BasicHttpClient.exePost(endpoint, postParameters)
		//		.toString().replaceAll("\\s+", "");
		
		return response.substring(0,1).equals(successState);
	}
	
	/**
	 * Attempts to register a new user against the remote endpoint by GETting
	 * provided credentials against a web server. The registerEndpoint provided
	 * is appended to the network endpoint in the register request.
	 * 
	 * Registers against (endpoint + "/register") by default.
	 * 
	 * @param username The provided username
	 * @param password The provided password
	 * @param successState The value to check the response of the server against to 
	 * 	determine if registration was successful or not
	 * @return true if registration was successful; else false
	 */
	public boolean httpRegisterGetAuth(String username, String password, 
			String successState) {
		return httpRegisterGetAuth(username, password, successState, REGISTER_ENDPOINT);
	}
	
	/**
	 * Attempts to register a new user against the remote endpoint by GETting
	 * provided credentials against a web server. The registerEndpoint provided
	 * is appended to the network endpoint in the register request.
	 * 
	 * @param username The provided username
	 * @param password The provided password
	 * @param successState The value to check the response of the server against to 
	 * 	determine if registration was successful or not
	 * @param registerEndpoint Appended to URI to authenticate against (e.g. "/register")
	 * @return true if registration was successful; else false
	 */
	public boolean httpRegisterGetAuth(String username, String password, 
			String successState, String registerEndpoint) {
		String response;
		
		response = BasicHttpClient.exeGet(endpoint + registerEndpoint + "?username="
			+ username + "&password=" + password);
		
		return response.substring(0,1).equals(successState);
	}
	
	
	
	/**
	 * Attempts to add a new account against the remote endpoint by GETting
	 * provided credentials against a web server. The addAccountEndpoint provided
	 * is appended to the network endpoint in the register request.
	 * 
	 * @param username The provided username
	 * @param account A String account ID
	 * @param balance The amount to initally add to this account
	 * @param type The account type
	 * @return The JSON-encoded server response
	 */
	public String httpGetCreateAccount(String username, String account, 
			double balance, String type) {
		return httpGetCreateAccount(username, account, balance, type, ADDACCOUNT_ENDPOINT);
	}
	
	/**
	 * Attempts to add a new account against the remote endpoint by GETting
	 * provided credentials against a web server. The addAccountEndpoint provided
	 * is appended to the network endpoint in the register request.
	 * 
	 * @param username The provided username
	 * @param account A String account ID
	 * @param balance The amount to initally add to this account
	 * @param type The account type
	 * @param registerEndpoint Appended to URI to authenticate against (e.g. "/addaccount")
	 * @return The JSON-encoded server response
	 */
	public String httpGetCreateAccount(String username, String account, 
			double balance, String type, String addAccountEndpoint) {
		String response;
		
		response = BasicHttpClient.exeGet(endpoint + addAccountEndpoint + "?account="
			+ account + "&user=" + username + "&balance=" + balance + "&type=" + type);
		
		return response;
	}
	
	/**
	 * Attempts to sync a JSON array of accounts associated with this user from server.
	 * 
	 * Syncs against "/retrieveaccounts" by default.
	 * 
	 * @param username The username associated with the acccounts
	 * @return The JSON-encoded server response as a String
	 */
	public String httpGetSyncAccounts(String username) {
		return httpGetSyncAccounts(endpoint + SYNC_ENDPOINT + "&user=" + username);
	}
	
	/**
	 * Attempts to sync a JSON array of accounts associated with this user from server.
	 * 
	 * @param username The username associated with the acccounts
	 * @param syncEndpoint The appended to URI to sync against (e.g. "/retrieveaccounts")
	 * @return The JSON-encoded server response as a String
	 */
	public String httpGetSyncAccounts(String username, String syncEndpoint) {
		String response;
		
		response = BasicHttpClient.exeGet(endpoint + syncEndpoint + "&user=" + username);
		
		return response;
	}
}
