package com.cs2340.buzzfunds;

import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.Queue;

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
	private String key, type;
	private Authenticator dataSource;
	private Queue<Transaction> transactionQueue = new LinkedList<Transaction>();
	
	/**
	 * Constructs a new Account object with a given key. 
	 * (NOTE: A balance should not be passed to a constructor for 
	 * integrity reasons - this should be pulled from a database instead.)
	 * 
	 * @param key A key used to identify this Account to a data source
	 * @param dataSource The data source authenticated against
	 * @param type The type of account
	 */
	public Account(String key, Authenticator dataSource, String type) {
		this.key = key;
		this.dataSource = dataSource;
		this.type = type;
		sync();
	}
	
	public Account(String key, Authenticator dataSource, double balance, String type) {
		this.key = key;
		this.dataSource = dataSource;
		this.balance = balance;
		this.type = type;
	}
	
	/**
	 * Initializes this account by syncing balances between app and database.
	 * @return A String describing success state
	 */
	public String sync() {
		String initString = "Success!";
		boolean balanceState = false; //pullBalance();
		if (!balanceState) {
			initString = "There was a problem syncing balances to the server.";
		}
		
		return initString;
	}
	
	
	/**
	 * Attempts to synchronize this Account's balance with a data source.
	 * @return true if successful; else false
	 */
	public boolean pullBalance() throws Exception {
		boolean success = false;
		//this.balance = dataSource.getEndpoint();
		
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
	 * Pushes a Transaction from transactionQueue to the data source.
	 *
	 * @return true if the Transaction was successful; false otherwise
	 */
	/*public boolean dequeue() {
		
	}*/
	
	/**
	 * Returns whether this Account has changes which need to be pushed to
	 * the data source and resynced.
	 *
	 * @return true if changes need to be pushed; false if up to date with data source
	 */
	public boolean pushNeeded() {
		return !transactionQueue.isEmpty();
	}
	
	/**
	 * Adds a transaction to transactionQueue.
	 */
	 public void queue(Transaction transaction) {
		 transactionQueue.add(transaction);
	 }
	 
	 /**
	  * Returns this Account's key.
	  * 
	  * @return This Account's key
	  */
	 public String getKey() {
		 return key;
	 }
	 
	 /**
	  * Returns this Account's ID (key minus username-prefix).
	  * 
	  * @return This Account's key, without username prefix
	  */
	 public String getId() {
		return key.replace(dataSource.getUsername() + "-", "");
	 }
	 
	 /**
	  * Returns this Account's type.
	  * 
	  * @return This Account's type
	  */
	 public String getType() {
		 return type;
	 }
	 
	 /**
	  * Returns a String representation of this Account.
	  */
	 public String toString() {
		 return "[" + NumberFormat.getCurrencyInstance().format(getBalance()) 
				 + "] " + getId() + ", " + getType();
	 }
}
