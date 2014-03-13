package com.cs2340.buzzfunds;

import java.text.NumberFormat;

/**
 * A TransactionHistoryItem stores information about a particular 
 * past Transaction in history.
 * 
 * @author Sean Collins
 */
public class TransactionHistoryItem {
	private String type;
	private double amount;
	
	/**
	 * Constructs a new TransactionHistoryItem with a given type and amount.
	 * 
	 * @param type The type of Transaction (e.g. "withdrawal," "deposit"
	 * @param amount The amount of this Transaction
	 */
	public TransactionHistoryItem(String type, double amount) {
		this.type = type;
		this.amount = amount;
	}
	
	/**
	 * Returns this TransactionHistoryItem's type.
	 * 
	 * @return This TransactionHistoryItem's type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Returns this TransactionHistoryItem's amount.
	 * 
	 * @return This TransactionHistoryItem's amount.
	 */
	public double getAmount() {
		return amount;
	}
	
	/**
	 * Returns a String representation of this TransactionHistoryItem.
	 * 
	 * @return A String representation of this TransactionHistoryItem
	 */
	public String toString() {
		return type.toUpperCase() + ": " + (type.equals("deposit") ? "+" : "-") 
				+ NumberFormat.getCurrencyInstance().format(amount);
	}
}
