package com.cs2340.buzzfunds;

/**
 * The Authenticator class performs authentication operations 
 * against a remote data source.
 * 
 * There should be only one Authenticator object.
 * 
 * @author Jeremy Brown
 */
public class Authenticator {

    private static final String loginEP = "http://buzzfunds.herokuapp.com/login";
    private static final String registerEP = "http://buzzfunds.herokuapp.com/register";
    private static final String success = "1";

	/**
	 * Attempts to authenticate by GETing credentials against the login endpoint.
	 *
	 * @return true if authentication was successful; false if invalid credentials
	 */
	public static boolean HasGoodCredentials(String username, String password) {
        String endpoint = String.format("%s?username=%s&password=%s", loginEP, username, password);
        String response = BasicHttpClient.exeGet(endpoint);
        return response.substring(0,1).equals(success);
	}
	
	/**
	 * Attempts to register a new user by GETing information against the register endpoint.
	 *
	 * @return true if registration was successful; false if invalid information
	 */
	public static boolean RegisterNewUser(String username, String password) {
            String endpoint = String.format("%s?username=%s&password=%s", registerEP, username, password);
			String response = BasicHttpClient.exeGet(endpoint);
            return response.substring(0,1).equals(success);
	}
}