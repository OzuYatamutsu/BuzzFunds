package com.cs2340.buzzfunds;

/**
 * A Transaction object stores and handles a transaction between accounts.
 * 
 * @author Sean Collins
 */
public class Transaction {
	/**
	 * The amount of the transaction; can be positive or negative.
	 */
	private double amount;
	/**
	 * The sending and receieving accounts.
	 */
	private Account source, dest;
	
	/**
	 * Constructs a new Transaction with given source and destination accounts,
	 * as well as the amount to be transferred.
	 * 
	 * @param source The source Account
	 * @param dest The destination Account
	 * @param amount The amount to be transferred
	 */
	public Transaction(Account source, Account dest, double amount) {
		this.source = source;
		this.dest = dest;
		this.amount = amount;
	}
	
	/**
	 * Constructs a new Transaction with given amount.
	 * (Note that some types of transactions may fail.)
	 * 
	 * @param dest The destination Account
	 * @param amount The amount to be transferred
	 */
	public Transaction(Account dest, double amount) {
		this(null, dest, amount);
	}
	
	/**
	 * Constructs a new Transaction with given amount.
	 * (Note that some types of transactions may fail.)
	 * @param amount The amount to be transferred
	 */
	public Transaction(double amount) {
		this(null, null, amount);
	}
	
	/**
	 * Sets the source account.
	 * @param source The source account
	 */
	public void setSourceAccount(Account source) {
		this.source = source;
	}
	
	/**
	 * Sets the destination account.
	 * @param dest The destination account
	 */
	public void setDestAccount(Account dest) {
		this.dest = dest;
	}
	
	/**
	 * Sets the amount to be transferred.
	 * @param amount The amount to be transferred
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	/**
	 * Attempts to perform a transfer of funds between the source and destination
	 * accounts with the amount given. 
	 * @return true if the transfer was successful; else false
	 */
	public boolean transfer() {
		boolean success = false;
		if (source.validate(amount)) {
			success = true;
			// Database logic here
			
			// Finally, sync the source and destination balances from database
			source.sync();
			dest.sync();
		}
		
		return success;
	}
}
