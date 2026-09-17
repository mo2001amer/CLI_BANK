package com.bank.repo;

import java.util.List;

import com.bank.domain.Customer;

public interface CustomerDAO {

    // When someone registers
    void addCustomer(Customer customer);

    //to check if customer id already exists
    boolean customerExists(int accountID);


















    // //get someones customer by id
    // Customer getCustomerById(int id);


    // List<Customer> getAllCustomers();

    // //updates customer balance
    // void updateBalance(Customer customer);

    // //just incase we need to delete a customer if they already have an account
    // void deleteCustomer(int id);


}
