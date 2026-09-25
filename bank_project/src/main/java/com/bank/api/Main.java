package com.bank.api;

import com.bank.business.CustomerService;
import com.bank.business.CustomerServiceImpl;
import com.bank.repo.CustomerDAO;
import com.bank.repo.CustomerDAOImpl;
import com.bank.repo.TransactionDAO;
import com.bank.repo.TransactionDAOImpl;

public class Main {

    public static void main(String[] args) {

       
        CustomerDAO customerDAO =new CustomerDAOImpl();

        TransactionDAO transactionDAO =new TransactionDAOImpl();

        CustomerService customerService =new CustomerServiceImpl(customerDAO,transactionDAO);

        Repl repl =new Repl(customerService);

        repl.run();
    }
}