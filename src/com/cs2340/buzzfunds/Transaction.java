package com.cs2340.buzzfunds;

/**
 * A Transaction object stores and handles a transaction to an Account.
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
	private Account source;
	
	/**
	 * Constructs a new Transaction with given source and destination accounts,
	 * as well as the amount to be transferred.
	 * 
	 * @param source The source Account
	 * @param dest The destination Account
	 * @param amount The amount to be transferred
	 */
	public Transaction(Account source, double amount) {
		this.source = source;
		this.amount = amount;
	}
	
	/**
	 * Constructs a new Transaction with given amount.
	 * (Note that some types of transactions may fail.)
	 * 
	 * @param amount The amount to be transferred
	 */
	public Transaction(double amount) {
		this(null, amount);
	}
	
	/**
	 * Sets the source Account.
	 * @param source The source Account
	 */
	public void setSource(Account source) {
		this.source = source;
	}
	
	/**
	 * Returns the source Account.
	 * 
	 * @return The source Account
	 */
	public Account getSource() {
		return source;
	}
	
	
	/**
	 * Sets the amount to be transferred.
	 * 
	 * @param amount The amount to be transferred
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	/**
	 * Gets the amount to be transferred.
	 * 
	 * @return The amount to be transferred
	 */
	public double getAmount() {
		return amount;
	}
}
