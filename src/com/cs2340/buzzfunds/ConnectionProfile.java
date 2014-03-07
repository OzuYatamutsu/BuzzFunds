package com.cs2340.buzzfunds;

/**
 * A ConnectionProfile holds information about a connection endpoint.
 * 
 * @author Sean Collins
 */
public class ConnectionProfile {
	/**
	 * The base server location, without any URI endpoints.
	 */
	public String endpoint;
	/**
	 * The URI endpoint to register Users against
	 */
	public String registerUserEndpoint;
	/**
	 * The URI endpoint to authenticate against
	 */
	public String authUserEndpoint;
	/**
	 * The URI endpoint to sync accounts against
	 */
	public String syncAccountEndpoint;
	
	/**
	 * Constructs a new ConnectionProfile with given information.
	 * 
	 * @param endpoint The base server location, without any URI endpoints
	 * @param registerUserEndpoint The URI endpoint to register Users against
	 * @param authUserEndpoint The URI endpoint to authenticate against
	 * @param syncAccountEndpoint The URI endpoint to sync accounts against
	 */
	public ConnectionProfile(String endpoint, String registerUserEndpoint, 
			String authUserEndpoint, String syncAccountEndpoint) {
		this.endpoint = endpoint;
		this.registerUserEndpoint = registerUserEndpoint;
		this.authUserEndpoint = authUserEndpoint;
		this.syncAccountEndpoint = syncAccountEndpoint;
	}
	
	/**
	 * Constructs a new ConnectionProfile with given information.
	 * 
	 * @param endpoint The base server location, without any URI endpoints
	 * @param authUserEndpoint The URI endpoint to authenticate against
	 * @param syncAccountEndpoint The URI endpoint to sync accounts against
	 */
	public ConnectionProfile(String endpoint, String authUserEndpoint, String syncAccountEndpoint) {
		this(endpoint, null, authUserEndpoint, syncAccountEndpoint);
	}
	
	/**
	 * Constructs a new ConnectionProfile with given information.
	 * 
	 * @param endpoint The base server location, without any URI endpoints
	 * @param authUserEndpoint The URI endpoint to authenticate against
	 */
	public ConnectionProfile(String endpoint, String authUserEndpoint) {
		this(endpoint, null, authUserEndpoint, null);
	}
	
	/**
	 * Constructs a new ConnectionProfile with given information.
	 * 
	 * @param endpoint The base server location, without any URI endpoints
	 */
	public ConnectionProfile(String endpoint) {
		this(endpoint, null, null, null);
	}
}
