package com.bank.business;

import java.util.List;

import com.bank.domain.Customer;
import com.bank.domain.Transaction;

public interface CustomerService {

    int register(
            Customer customer
    );


    boolean login(
            int accountId,
            int pin
    );


    double getBalance(
            int accountId
    );


    void deposit(
            int accountId,
            double amount
    );


    void withdraw(
            int accountId,
            double amount
    );


    void transfer(
            int fromAccountId,
            int toAccountId,
            double amount
    );


    List<Transaction> getTransactionHistory(
            int accountId
    );
}