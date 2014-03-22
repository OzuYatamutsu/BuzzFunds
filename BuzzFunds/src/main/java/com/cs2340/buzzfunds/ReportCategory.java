package com.cs2340.buzzfunds;

/**
 * Created by Jeremy on 3/20/14.
 */
public class ReportCategory {
    private double amount;
    private String name;

    public ReportCategory(String name, double amount) {
        this.amount = amount;
        this.name = name;
    }

    public double getAmount() { return amount; }

    public void IncreaseAmount(double amt) { amount += amt; }

    public String getName() { return name; }
}
