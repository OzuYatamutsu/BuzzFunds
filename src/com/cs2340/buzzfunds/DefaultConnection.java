package com.cs2340.buzzfunds;

/**
 * A class which holds default information profiles for often-used connection endpoints.
 * 
 * @author Sean Collins
 */
public class DefaultConnection {
		/**
		 * A default connection profile associated with the Buzzfunds endpoint.<br><br>
		 * 
		 * Register: /register<br>
		 * Login: /login<br>
		 * Add Account: /addaccount<br>
		 * Get Account(s): /retrieveaccounts
		 */
		public static final ConnectionProfile BUZZFUNDS = new ConnectionProfile(
				"http://buzzfunds.herokuapp.com", 
				"/register", 
				"/login", 
				"/addaccount", 
				"/retrieveaccounts");
}