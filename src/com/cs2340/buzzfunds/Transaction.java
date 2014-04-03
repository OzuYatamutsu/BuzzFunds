package com.cs2340.buzzfunds;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A Transaction object stores and handles a transaction to an Account.
 * 
 * @author Sean Collins
 */
public class Transaction {

    // User-specified identifier for the transaction
    private String name;

    // Transaction amount - can only be positive
	private double amount;

    // Transaction type (deposit or withdrawal)
	private TransactionType type;

    // Transaction category (spending category for withdrawal, income source for deposit)
    private String category;

    // Transaction creation date
    private LocalDate insDate;

    // Effective transaction date
    private LocalDate effDate;

    // Whether or not the transaction is rolled back
    private boolean enabled;

	/**
	 * Constructs a new Transaction with given source and destination accounts,
	 * as well as the amount to be transferred.
	 *
     * @param name The user's identifier for the transaction
	 * @param amount The amount to be transferred
	 * @param type The type of this Transaction (deposit, withdrawal)
     * @param category What the money went to or where it came from
     * @param insDate When the transaction was created
     * @param effDate When the transaction should begin to effect the account
	 */
	public Transaction(String name, double amount, String type, String cat, LocalDate iDate, LocalDate eDate) {
        this.name = name;
		this.amount = amount;
        enabled = true;
        category = cat;
        insDate = iDate;
        effDate = eDate;
        this.type = type.equals("deposit") ? TransactionType.DEPOSIT : TransactionType.WITHDRAWAL;
	}

    public Transaction(String name, double amount, String type, String cat, LocalDate eDate) {
        this(name, amount, type, cat, LocalDate.now(), eDate);
    }


    // Construct a new Transaction using a JSON object from the server
    public Transaction(JSONObject obj) {
        try {
            enabled = true;
            name = obj.getString("name");
            category = obj.getString("category");
            amount = obj.getDouble("amount");

            if (obj.getString("type").equals("deposit")) {
                type = TransactionType.DEPOSIT;
                } else {
                type = TransactionType.WITHDRAWAL;
                }

            DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
            insDate = fmt.parseLocalDate(obj.getString("creationDate"));
            effDate = fmt.parseLocalDate(obj.getString("effectiveDate"));
        } catch (Exception e) { e.printStackTrace(); }
    }

    public String getName() { return name; }

    public double getAmount() { return amount; }

    public String getType() { return type == TransactionType.DEPOSIT ? "d" : "w"; }

    public String getCategory() { return category; }

    public LocalDate getDate() { return effDate; }

    public boolean IsEnabled() { return enabled; }

    public void ToggleEnabled() {
        enabled = !enabled;
    }

    public static Collection<Transaction> ParseTxnHistory(JSONArray ary) throws JSONException{
        Collection<Transaction> history = new ArrayList<Transaction>(ary.length());
        for (int i = 0; i < ary.length(); i++) {
            history.add(new Transaction(ary.getJSONObject(i)));
            }
        return history;
    }

    public String toURL() {
        String outType = type == TransactionType.DEPOSIT ? "deposit" : "withdrawal";
        return String.format("name=%s&amount=%f&type=%s&category=%s&insDate=%tF&effDate=%tF", name, amount, outType,
                category, insDate.toDate(), effDate.toDate());
    }
}