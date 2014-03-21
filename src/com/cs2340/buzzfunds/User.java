package com.cs2340.buzzfunds;

import org.joda.time.LocalDate;

import java.util.*;

/**
 * Created by Jeremy on 3/20/14.
 */
public class User {

    // The user's credentials
    private String name, password;

    //The user's accounts
    private Collection<Account> accounts;

    public User(String userName, String passwd) {
        name = userName;
        password = passwd;
    }

    public Map<String, Collection<ReportCategory>> GenerateReports(LocalDate startDate, LocalDate endDate) {
        Map<String, Collection<ReportCategory>> reports = new HashMap<String, Collection<ReportCategory>>();
        Map<String, ReportCategory> spending = new HashMap<String, ReportCategory>();
        Map<String, ReportCategory> income = new HashMap<String, ReportCategory>();
        Map<String, ReportCategory> flows = new HashMap<String, ReportCategory>();

        for (Account acct : accounts) {
            for (Transaction txn : acct.GetTxns(startDate, endDate)){
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
