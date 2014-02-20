package com.cs2340.buzzfunds;

import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/**
 * The Authenticator class performs authentication operations 
 * against a remote data source.
 * 
 * @author Sean Collins
 */
public class Authenticator {
	/**
	 * The network endpoint of the authentication source.
	 */
	private String endpoint;
	
	/**
	 * Constructs a new Authenticator with a given network endpoint.
	 * 
	 * @param endpoint A provided network endpoint
	 */
	public Authenticator(String endpoint) {
		this.endpoint = endpoint;
	}
	
	/**
	 * Attempts to authenticate against the remote endpoint by POSTing 
	 * provided credentials against a web server.
	 * 
	 * @param username The provided username
	 * @param password The provided password
	 * @param successState The value to check the response of the server against to 
	 * 	determine if authentication was successful or not
	 * @return true if authentication was successful; else false
	 */
	public boolean httpLoginGetAuth(String username, String password, String successState) {
		String response;
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		
		//note '/login' is slightly different than the preivous '/loginserver'
		response = BasicHttpClient.exeGet(endpoint + "?username=" + username + "&password=" + password);
		

		//Couldn't get the POST quite functional so we will just rely on GET for now
		//postParameters.add(new BasicNameValuePair("username", username));
		//postParameters.add(new BasicNameValuePair("password", password));
		//response = BasicHttpClient.exePost(endpoint, postParameters)
		//		.toString().replaceAll("\\s+", "");
		
		
		return response.substring(0,1).equals(successState);
	}
	
	public boolean httpRegisterGetAuth(String username, String password, String successState) {
		String response;
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		
		//note '/login' is slightly different than the preivous '/loginserver'
		response = BasicHttpClient.exeGet("http://buzzfunds.herokuapp.com/register?username="
			+ username + "&password=" + password);
		
		//Couldn't get the POST quite functional so we will just rely on GET for now
		//postParameters.add(new BasicNameValuePair("username", username));
		//postParameters.add(new BasicNameValuePair("password", password));
		//response = BasicHttpClient.exePost(endpoint, postParameters)
		//		.toString().replaceAll("\\s+", "");
		
		return response.equals(successState);
	}

}
