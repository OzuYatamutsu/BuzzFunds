package com.cs2340.buzzfunds;

import org.json.JSONArray;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Jeremy on 3/20/14.
 */
public class NetworkActivities {

    private static final String base = "http://buzzfunds.herokuapp.com/";
    private static final String newAcctEP = "addaccount";
    private static final String allAcctsEP = "retrieveaccounts";
    private static final String newTxnEP = "transaction";
    private static final String deleteAcctEP = "deleteaccount";
    private static final String editAcctNameEP = "editaccount";
    private static final String editInterestEP = "editinterest";
    private static final String success = "1";

    public static boolean AddTransactionToAccount(String longName, Transaction txn) {
        String user = longName.split("-")[0];
        String endpoint = String.format("%s%s?user=%s&account=%s&%s", base, newTxnEP, user, longName, txn.toURL());
        String res = BasicHttpClient.exeGet(endpoint);
        return res.substring(0,1).equals(success);
    }

    public static boolean AddAccountToUser(Account acct) {
        String endpoint = String.format("%s%s%s", base, newAcctEP, acct.toURL());
        String res = BasicHttpClient.exeGet(endpoint);
        return res.substring(0,1).equals(success);
    }

    public static Collection<Account> GetAccountsFromServer(String username) {
        ArrayList<Account> accounts = null;
        String endpoint = String.format("%s%s?user=%s", base, allAcctsEP, username);
        String res = BasicHttpClient.exeGet(endpoint);
        if (res == null || res.equals("")) { return accounts; }
        try {
            JSONArray ary = new JSONArray(res);
            accounts = new ArrayList<Account>(ary.length());
            for (int i = 0; i < ary.length(); i++) {
                accounts.add(new Account(ary.getJSONObject(i)));
            }
        } catch (Exception e) {
            return accounts;
        }
        return accounts;
    }

    public static boolean deleteAccount(String username, Account acct) {
        String endpoint = String.format("%s%s?user=%s&account=%s", base, deleteAcctEP, username, acct.getKey().split("-")[1]);
        String res = BasicHttpClient.exeGet(endpoint);
        return res.substring(0,1).equals(success);
    }

    public static boolean editAccountName(String username, Account acct, String newName) {
        String endpoint = String.format("%s%s?user=%s&account=%s&new=%s", base, editAcctNameEP, username, acct.getKey().split("-")[1], newName);
        String res = BasicHttpClient.exeGet(endpoint);
        return res.substring(0,1).equals(success);
    }

    public static boolean editInterest(String username, Account acct, double newRate) {
        String endpoint = String.format("%s%s?user=%s&account=%s&interest=%s", base, editInterestEP, username, acct.getKey().split("-")[1], newRate);
        String res = BasicHttpClient.exeGet(endpoint);
        return res.substring(0,1).equals(success);
    }
}