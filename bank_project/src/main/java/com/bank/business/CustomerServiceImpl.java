package com.bank.business;
import com.bank.domain.*;
import com.bank.repo.CustomerDAO;
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDAO customerDAO;

    //cunstrctor 
    CustomerServiceImpl(CustomerDAO customerDAO)
    {
        this.customerDAO = customerDAO; 

    }


    @Override 
    public void register(Customer customer)
    {
        if (customerDAO.accountExists(accountId)) {
        throw new IllegalArgumentException("Account ID already exists.");
    }

        


    }





    
}