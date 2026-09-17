package com.bank.domain;

public class Customer {
    private int accountId;
    private int pin;
    private double balance;
    public Customer(int accountId, int pin, double balance)
    {
        this.accountId = accountId;
        this.pin = pin;
        this.balance = balance;

    }
    public int getAccountID()
    {
        return accountId;

    }
    public int getPin()
    {
        return pin;

    }
    public double getBalance()
    {
        return balance;

    }
}

