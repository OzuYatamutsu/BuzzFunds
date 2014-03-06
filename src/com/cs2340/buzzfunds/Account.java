package com.cs2340.buzzfunds;

/**
 * An Account object stores account information as retrieved from
 * a database or data source.
 * 
 * @author Sean Collins
 */
public class Account {
	/**
	 * The balance in this account; negative balances are illegal!
	 */
	private double balance;
	/**
	 * A key used to identify this account against a database.
	 */
	private String key;
	
	private Authenticator dataSource;
	
	/**
	 * Constructs a new Account object with a given key. 
	 * (NOTE: A balance should not be passed to a constructor for 
	 * integrity reasons - this should be pulled from a database instead.)
	 * 
	 * @param key A key used to identify this Account to a data source
	 * @param dataSource The data source authenticated against
	 */
	public Account(String key, Authenticator dataSource) {
		this.key = key;
		this.dataSource = dataSource;
		sync();
	}
	
	/**
	 * Initializes this account by syncing balances between app and database.
	 * @return A String describing success state
	 */
	public String sync() {
		String initString = "Success!";
		boolean balanceState = pullBalance();
		if (!balanceState) {
			initString = "There was a problem syncing balances to the server.";
		}
		
		return initString;
	}
	
	
	/**
	 * Attempts to synchronize this Account's balance with a data source.
	 * @return true if successful; else false
	 */
	public boolean pullBalance() {
		boolean success = true; // Placeholder
		// Sync logic here
		if (success) { // Placeholder
			double balance = 100.00; // Placeholder
			this.balance = balance;
		}
		
		return success;
	}
	
	/**
	 * Validates whether or not this account can withdraw a given amount.
	 * 
	 * @param amount The amount to validate
	 * @return true if this account has enough funds; else false
	 */
	public boolean validate(double amount) {
		return ((balance - amount) > 0.00) ? true : false;
	}
	
	/**
	 * Returns this Account's balance.
	 * @return This Account's balance
	 */
	public double getBalance() {
		return balance;
	}
	
	/**
	 * 
	 */
}
