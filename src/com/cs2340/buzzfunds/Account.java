package com.cs2340.buzzfunds;

import org.joda.time.LocalDate;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.NumberFormat;
import java.util.*;

/**
 * An Account object stores account information as retrieved from
 * a database or data source.
 * 
 * @author Sean Collins
 */
public class Account {

    // Account balance - negative balances are illegal!
	private double balance;

    // Account type - checking or savings for now
    private AccountType type;

    // A key used to identify this account against a database.
	private String key;

    // The interest rate for the account
    private double intRate;

    // A queue containing transactions unsynced with the server
    private Queue<Transaction> unsynced;

	/**
	 * A Map containing the Transaction history of this Account.
	 * It should be organized as follows:
	 * 
	 * dateString -> (transactionType, amount)
	 * (i.e.
	 * 
	 * Map(
	 * (2012-12-31, List("deposit", 120.00), ("withdrawal", 12.00), ...)),
	 * (2013-01-01, List(("deposit", 61.59), ...)))
	 * )
	 */
	private Map<LocalDate, Collection<Transaction>> history;

	/**
	 * Constructs a new Account object. 
	 * 
	 * @param key A key used to identify this Account to a data source
     * @param rate The interest rate for the account
	 * @param balance The balance of this account
	 * @param type The type of account
	 * @param history A Map which contains the Transaction history of this account.
	 */
	public Account(String key, double balance, String type, double rate,
                   Map<LocalDate, Collection<Transaction>> history) {
		this.key = key;
        intRate = rate;
		this.balance = balance;
		this.history = history;
        unsynced = new LinkedList<Transaction>();
        this.type = type == "savings" ? AccountType.SAVINGS : AccountType.CHECKING;
        this.CalcBalance();
	}
	
	/**
	 * Constructs a new Account object without a Transaction history.
	 * 
	 * @param key A key used to identify this Account to a data source
     * @param rate The interest rate for the account
	 * @param balance The balance of this account
	 * @param type The type of account
	 */
	public Account(String key, double balance, String type, double rate) {
		this(key, balance, type, rate, new HashMap<LocalDate, Collection<Transaction>>());
	}

    // Construct a new Account object using a JSON object from the server
    public Account(JSONObject obj) throws JSONException {
        key = obj.getString("name");
        intRate = Double.parseDouble(obj.getString("interest"));
        type = obj.getString("type") == "savings" ? AccountType.SAVINGS : AccountType.CHECKING;

        history = new HashMap<LocalDate, Collection<Transaction>>();
        Collection<Transaction> txns = Transaction.ParseTxnHistory(obj.getJSONArray("history"));
        for (Transaction txn : txns) {
            if (!history.containsKey(txn.getDate())) {
                history.put(txn.getDate(), new ArrayList<Transaction>());
                }
            history.get(txn.getDate()).add(txn);
        }

        this.CalcBalance();
    }

    // Get the key for this account
    public String getKey() { return key; }

    // Get the balance for this account
	public double getBalance() { return balance; }

    // Get the ID for this account
    public String getId() { return key.split("-")[1]; }

    // Get this account's type
    public String getType() {
        String outType = "";
        switch (type) {
            case SAVINGS:
                outType = "savings";
                break;
            case CHECKING:
                outType = "checking";
                break;
        }
        return outType;
    }


    // Confirms if the user can withdraw the desired amount of money from this account
    public boolean CanWithdraw(double amount) { return (balance - amount) > 0.00; }

    /**
     * Returns whether this Account has changes which need to be pushed to
     * the data source and resynced.
     *
     * @return true if changes need to be pushed; false if up to date with data source
     */
    public boolean NeedSyncing() { return !unsynced.isEmpty(); }

    public boolean MakeNewTransaction(String name, double amount, String type, String category, LocalDate effDate) {
        boolean result = false;
        Transaction txn = new Transaction(name, amount, type, category, effDate);
        if (NetworkActivities.AddTransactionToAccount(key,txn)) {
            AddTxnToHistory(txn);
            CalcBalance();
            result = true;
            } else { unsynced.add(txn); }
        return result;
    }

    // Returns true if there were no unsynced transactions or we synced them all, false otherwise
    public boolean SyncTxns() {
        if (!unsynced.isEmpty()) {
            int size = unsynced.size();
            for (int i = 0; i < size; i++) {
                Transaction txn = unsynced.remove();
                if (NetworkActivities.AddTransactionToAccount(key,txn)) {
                    AddTxnToHistory(txn);
                } else { unsynced.add(txn); }
            }
        }
       CalcBalance();
       return unsynced.isEmpty();
    }

    // Add a transaction to this account's history
     private void AddTxnToHistory(Transaction txn) {
         if (!history.containsKey(txn.getDate())) {
             history.put(txn.getDate(), new ArrayList<Transaction>());
            }
         history.get(txn.getDate()).add(txn);
     }

    // Get a list containing the full transaction history for the account
    public Collection<Transaction> getHistory() {
        Collection<Transaction> txnHistory = new LinkedList<Transaction>();
        for (Collection<Transaction> txnOnDate : history.values()) {
            for (Transaction txn : txnOnDate) {
                txnHistory.add(txn);
            }
        }
        return txnHistory;
    }

    public Collection<String> getSimpleHistory() {
        Collection<String> simpleHistory = new LinkedList<String>();
        for (LocalDate date : history.keySet()) {
            String dateHistory = date.toString("yyyy-MM-dd:");
            for (Transaction txn : history.get(date)) {
                String typeString = txn.getType().equals("d") ? "+" : "-";
                dateHistory += String.format("\n%s: %s%s", txn.getName(), typeString,
                        NumberFormat.getCurrencyInstance().format(txn.getAmount()));
            }
            simpleHistory.add(dateHistory);
        }
        return simpleHistory;
    }

    // Get a list of transactions that took effect on the specified date
    public Collection<Transaction> getHistoryOnDate(LocalDate date) {
        if (history.containsKey(date)){
            return history.get(date);
            } else { return new LinkedList<Transaction>(); }
    }

    // Get a list of transactions that took effect in the specified date range
    public Collection<Transaction> getHistoryBetweenDates(LocalDate startDate, LocalDate endDate) {
        List<Transaction> txns = new LinkedList<Transaction>();
        for (LocalDate date = startDate; date.isBefore(endDate) || date.isEqual(endDate); date = date.plusDays(1)) {
            if (history.containsKey(date)) {
                for (Transaction txn : history.get(date)) {
                    txns.add(txn);
                }
            }
        }
        return txns;
    }

    private void CalcBalance() {
        double bal = 0;
        for (Collection<Transaction> txnOnDate : history.values()) {
            for (Transaction txn : txnOnDate) {
                if (txn.IsEnabled()) {
                    if (txn.getType().equals("d")) {
                        bal += txn.getAmount();
                    } else {
                        bal -= txn.getAmount();
                        }
                    }
                }
            }
        balance = bal;
    }

    public String toURL() {
        String acctType = type == AccountType.SAVINGS ? "savings" : "checking";
        return String.format("?user=%s&name=%s&amount=%f&type=%s&interest=%f&date=%tF", key.split("-")[0],
                key.split("-")[1], balance, acctType, intRate, LocalDate.now().toDate());
    }
    @Override
    public String toString() {
        return "[" + NumberFormat.getCurrencyInstance().format(getBalance())
                + "] " + getId() + ", " + getType();
    }

}