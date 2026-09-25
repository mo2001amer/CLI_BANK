package com.bank.repo;

import com.bank.domain.Customer;

public interface CustomerDAO {

    int addCustomer(Customer customer);

    Customer findByAccountId(int accountId);

    void updateBalance(
            int accountId,
            double newBalance
    );

    void transferFunds(
            int fromAccountId,
            int toAccountId,
            double amount
    );
}