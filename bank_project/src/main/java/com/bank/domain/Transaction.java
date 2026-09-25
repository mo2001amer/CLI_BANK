package com.bank.domain;

import java.sql.Timestamp;

public class Transaction {

    private int transactionId;
    private String transactionType;
    private double amount;

    private Integer fromAccountId;
    private Integer toAccountId;

    private Timestamp createdAt;



    public Transaction(
            String transactionType,
            double amount,
            Integer fromAccountId,
            Integer toAccountId) {

        this.transactionType = transactionType;
        this.amount = amount;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
    }


    public Transaction(
            int transactionId,
            String transactionType,
            double amount,
            Integer fromAccountId,
            Integer toAccountId,
            Timestamp createdAt) {

        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.createdAt = createdAt;
    }


    public int getTransactionId() {
        return transactionId;
    }


    public String getTransactionType() {
        return transactionType;
    }


    public double getAmount() {
        return amount;
    }


    public Integer getFromAccountId() {
        return fromAccountId;
    }


    public Integer getToAccountId() {
        return toAccountId;
    }


    public Timestamp getCreatedAt() {
        return createdAt;
    }
}