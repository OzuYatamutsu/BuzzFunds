package com.cs2340.buzzfunds;

import org.joda.time.LocalDate;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Jeremy on 3/20/14.
 */
public class User implements Serializable {

    // The user's credentials
    private String name;

    //The user's accounts
    private Collection<Account> accounts;

    public User(String userName) {
        name = userName;
        PopulateAccounts();
    }

    public void PopulateAccounts() {
        Collection<Account> maybeAccounts = NetworkActivities.GetAccountsFromServer(name);
        if (maybeAccounts != null) {
            accounts = maybeAccounts;
            } else { accounts = new ArrayList<Account>(); }
    }

    public Collection<Account> getAccounts() { return accounts; }

    public Account[] getAccountsAsArray() {
    Account[] acctAry = new Account[0];
    acctAry = accounts.toArray(acctAry);
    return acctAry;
    }

    public boolean HasAccounts() { return !accounts.isEmpty(); }

    public boolean AddAccount(String shortName, double balance, String type, double rate) {
        Account acct = new Account(String.format("%s-%s", name, shortName), balance, type, rate);
        return NetworkActivities.AddAccountToUser(acct);
    }
    
    public void UpdateInterest() {
    	for(Account a: accounts) {
    		a.UpdateInterest();
    	}
    }

    public Map<String, Collection<ReportCategory>> GenerateReports(LocalDate startDate, LocalDate endDate) {
        Map<String, Collection<ReportCategory>> reports = new HashMap<String, Collection<ReportCategory>>();
        Map<String, ReportCategory> spending = new HashMap<String, ReportCategory>();
        Map<String, ReportCategory> income = new HashMap<String, ReportCategory>();
        Map<String, ReportCategory> flows = new HashMap<String, ReportCategory>();

        for (Account acct : accounts) {
            for (Transaction txn : acct.getHistoryBetweenDates(startDate, endDate)){
                if (txn.IsEnabled()) {
                    if (txn.getType() == "d") {
                        if (!income.containsKey(txn.getCategory())) {
                            income.put(txn.getCategory(), new ReportCategory(txn.getCategory(), Double.parseDouble("0")));
                            }
                        if (!flows.containsKey("plusle")) {
                            flows.put("plusle", new ReportCategory("Income", Double.parseDouble("0")));
                            }

                        income.get(txn.getCategory()).IncreaseAmount(txn.getAmount());
                        flows.get("plusle").IncreaseAmount(txn.getAmount());
                        } else {
                        if (!spending.containsKey(txn.getCategory())) {
                            spending.put(txn.getCategory(), new ReportCategory(txn.getCategory(), Double.parseDouble("0")));
                            }
                        if (!flows.containsKey("minun")) {
                            flows.put("minun", new ReportCategory("Expenses", Double.parseDouble("0")));
                            }

                        spending.get(txn.getCategory()).IncreaseAmount(txn.getAmount());
                        flows.get("minun").IncreaseAmount(txn.getAmount());
                    }
                }
            }
        }

        reports.put("spending", spending.values());
        reports.put("income", income.values());
        reports.put("flows", flows.values());
        return reports;
    }
}
