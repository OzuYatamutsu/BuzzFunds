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

    private static MessageDigest sha;

    // Unique ID for the transaction necessary for deleting transactions
    private String id;

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
        // All Java VMs are required to support SHA-256 sums, so we should never get an exception
        try {
            sha = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) { e.printStackTrace(); }

        this.name = name;
		this.amount = amount;
        enabled = true;
        category = cat;
        insDate = iDate;
        effDate = eDate;
        this.type = type == "d" ? TransactionType.DEPOSIT : TransactionType.WITHDRAWAL;

        String outType = this.type == TransactionType.DEPOSIT ? "d" : "w";
        String toHash = String.format("name=%s&delta=%f&type=%s&initDate=%tF&exeDate=%tF", name, amount, outType,
                insDate.toDate(), effDate.toDate());
        // I don't know what computing device doesn't support UTF-8, but I don't wanna meet it.
        try {
            byte[] hash = sha.digest(toHash.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 8; i++) {
                sb.append(String.format("%02X", hash[i]));
                }
            id = sb.toString();
            } catch (UnsupportedEncodingException e) { e.printStackTrace(); }
	}

    public Transaction(String name, double amount, String type, String cat, LocalDate eDate) {
        this(name, amount, type, cat, LocalDate.now(), eDate);
    }


    // Construct a new Transaction using a JSON object from the server
    public Transaction(JSONObject obj) {
        try {
            enabled = true;
            name = obj.getString("name");
            amount = Double.parseDouble(obj.getString("balance"));
            category = obj.getString("category");
            id = obj.getString("id");

            if (obj.getString("type") == "d") {
                type = TransactionType.DEPOSIT;
                } else {
                type = TransactionType.WITHDRAWAL;
                }

            DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-mm-dd");
            insDate = fmt.parseLocalDate(obj.getString("insDate"));
            effDate = fmt.parseLocalDate(obj.getString("effDate"));
        } catch (Exception e) { e.printStackTrace(); }
    }

    public String getName() { return name; }

    public double getAmount() { return amount; }

    public String getType() { return type == TransactionType.DEPOSIT ? "d" : "w"; }

    public String getCategory() { return category; }

    public LocalDate getDate() { return effDate; }

    public String getId() { return id; }

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
        String outType = type == TransactionType.DEPOSIT ? "d" : "w";
        return String.format("id=%s&name=%s&delta=%f&type=%s&initDate=%tF&exeDate=%tF", id, name, amount, outType,
                insDate.toDate(), effDate.toDate());
    }
}