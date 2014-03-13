package com.cs2340.buzzfunds;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
	 * A Map containing the Transaction history of this Account.
	 * It should be organized as follows:<br><br>
	 * 
	 * dateString -> (transactionType, amount)<br>
	 * (i.e.
	 * 
	 * Map(<br>
	 * (2012-12-31, List("deposit", 120.00), ("withdrawal", 12.00), ...)),<br>
	 * (2013-01-01, List(("deposit", 61.59), ...)))<br/>
	 * )
	 */
	private Map<String, List<TransactionHistoryItem>> history;
	/**
	 * Constructs a new Account object. 
	 * 
	 * @param key A key used to identify this Account to a data source
	 * @param dataSource The data source authenticated against
	 * @param balance The balance of this account
	 * @param type The type of account
	 * @param history A Map which contains the Transaction history of this account.
	 */
	public Account(String key, Authenticator dataSource, double balance, 
			String type, Map<String, List<TransactionHistoryItem>> history) {
		this.key = key;
		this.dataSource = dataSource;
		this.balance = balance;
		this.type = type;
		this.history = history;
	}
	
	/**
	 * Constructs a new Account object without a Transaction history.
	 * 
	 * @param key A key used to identify this Account to a data source
	 * @param dataSource The data source authenticated against
	 * @param balance The balance of this account
	 * @param type The type of account
	 */
	public Account(String key, Authenticator dataSource, double balance, 
			String type) {
		this(key, dataSource, balance, type, null);
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
	 * Attempts to push a Transaction from transactionQueue to the endpoint.
	 * 
	 * @param date The given date of this Transaction (in "yyyy-MM-dd" format)
	 * @return true if push was successful; false otherwise
	 */
	public boolean push(String date) {
		boolean pushSuccessful = false;
		double amount = 0.00;
		Transaction transaction = null;
		
		if (pushNeeded()) {
			transaction = transactionQueue.remove();
			amount = transaction.getAmount();
			if (transaction.getType().equals("withdrawal")) {
				amount = (-1) * amount;
			}
			pushSuccessful = dataSource.httpGetTransaction(transaction, date);
		}
		
		if (pushSuccessful) {
			balance += amount;
			addHistory(date, transaction.getType(), transaction.getAmount());
		}
		
		return pushSuccessful;
	}
	
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
	 
	 /**
	  * Adds a completed Transaction to this Account's history Map.
	  * 
	  * @param date The date of the Transaction (in "yyyy-MM-dd" format)
	  * @param type The type of the Transaction
	  * @param amount The amount of the Transaction
	  */
	 private void addHistory(String date, String type, double amount) {
		 if (!history.containsKey(date)) {
			 history.put(date, new ArrayList<TransactionHistoryItem>());
		 }
		 
		 history.get(date).add(new TransactionHistoryItem(type, amount));
	 }
	 
	 /**
	  * Returns a List of all Transactions logged on a given date.
	  * 
	  * @param date The date of the Transaction (in "yyyy-MM-dd" format)
	  * @return A List of all Transactions on the given date, or null if none found
	  */
	 public List<TransactionHistoryItem> getTransactionsByDate(String date) {
		 return history.get(date);
	 }
	 
	 /**
	  * Returns the Transaction history for this Account.
	  * 
	  * @return The Transaction history for this Account
	  */
	 public Map<String, List<TransactionHistoryItem>> getMap() {
		 return history;
	 }
}
