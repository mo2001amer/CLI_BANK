package com.bank.business;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.bank.domain.Customer;
import com.bank.domain.Transaction;

import com.bank.repo.CustomerDAO;
import com.bank.repo.TransactionDAO;


public class CustomerServiceImpl
        implements CustomerService {


    // 
    // LOGGING
    
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);


    private final CustomerDAO customerDAO;

    private final TransactionDAO transactionDAO;


    public CustomerServiceImpl(CustomerDAO customerDAO,TransactionDAO transactionDAO) {

        this.customerDAO =customerDAO;

        this.transactionDAO =
                transactionDAO;
    }


    // REGISTER
    @Override
    public int register(Customer customer) {

        int pin = customer.getPin();


        if (pin < 1000 || pin > 9999) {

            throw new IllegalArgumentException("PIN must be exactly 4 digits.");
        }


        return customerDAO.addCustomer( customer);
    }


    // LOGIN
    @Override
    public boolean login(int accountId,int pin) {

        Customer customer =customerDAO.findByAccountId(accountId);


        // Account does not exist
        if (customer == null) {

            return false;
        }


        // Incorrect PIN
        if (customer.getPin() != pin) {


            // LOGGING HERE - ERROR
        
            logger.error("Incorrect PIN entered for account {}",accountId );


            return false;
        }


        logger.info(
                "Account {} successfully logged in",accountId);


        return true;
    }


    // CHECK BALANCE
    @Override
    public double getBalance(int accountId) {

        Customer customer =customerDAO.findByAccountId(accountId);


        if (customer == null) {

            throw new IllegalArgumentException(
                    "Account not found."
            );
        }


        return customer.getBalance();
    }


    // DEPOSIT
    @Override
    public void deposit(int accountId,double amount) {

        if (amount <= 0) {

            throw new IllegalArgumentException(
                    "Deposit amount must be greater than zero."
            );
        }


        Customer customer =customerDAO.findByAccountId(accountId);


        if (customer == null) {

            throw new IllegalArgumentException(
                    "Account not found."
            );
        }


        double newBalance =customer.getBalance()+ amount;


        customerDAO.updateBalance(
                accountId,newBalance
        );


        Transaction transaction =new Transaction("DEPOSIT", amount, null,accountId);


        transactionDAO.addTransaction(transaction);
    }


    // WITHDRAW
    @Override
    public void withdraw( int accountId, double amount) {

        if (amount <= 0) {

            throw new IllegalArgumentException(
                    "Withdrawal amount must be greater than zero."
            );
        }


        Customer customer =customerDAO.findByAccountId(accountId);


        if (customer == null) {

            throw new IllegalArgumentException( "Account not found.");
        }


        if (amount > customer.getBalance()) {

            throw new IllegalArgumentException(
                    "Insufficient funds."
            );
        }


        double newBalance =customer.getBalance() - amount;


        customerDAO.updateBalance(accountId, newBalance);


        Transaction transaction = new Transaction("WITHDRAW",amount,accountId,null);


        transactionDAO.addTransaction(transaction );
    }


    // TRANSFER
    @Override
    public void transfer(int fromAccountId,int toAccountId,double amount) {

        if (amount <= 0) {

            throw new IllegalArgumentException(
                    "Transfer amount must be greater than zero."
            );
        }


        if (fromAccountId== toAccountId) {

            throw new IllegalArgumentException(
                    "You cannot transfer money "+ "to the same account."
            );
        }


        Customer sender =
                customerDAO.findByAccountId(fromAccountId);


        if (sender == null) {

            throw new IllegalArgumentException(
                    "Sender account not found."
            );
        }


        Customer receiver = customerDAO.findByAccountId( toAccountId);


        if (receiver == null) {

            throw new IllegalArgumentException("Receiving account not found.");
        }


        if (amount> sender.getBalance()) {

            throw new IllegalArgumentException("Insufficient funds.");
        }


        customerDAO.transferFunds(fromAccountId,toAccountId,amount);
    }


    // TRANSACTION HISTORY
    @Override
    public List<Transaction>getTransactionHistory(int accountId) {

        return transactionDAO.findByAccountId(accountId);
    }
}