package com.bank.repo;

import java.util.List;

import com.bank.domain.Transaction;

public interface TransactionDAO {

    void addTransaction(
            Transaction transaction
    );

    List<Transaction> findByAccountId(
            int accountId
    );
}