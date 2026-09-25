package com.bank.business;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.bank.domain.Customer;
import com.bank.domain.Transaction;

import com.bank.repo.CustomerDAO;
import com.bank.repo.TransactionDAO;


class CustomerServiceImplTest {



    @Test
    void login_correctCredentials_returnsTrue() {


        Customer customer = new Customer(1,1234,100);


        FakeCustomerDAO customerDAO = new FakeCustomerDAO(customer);


        FakeTransactionDAO transactionDAO =new FakeTransactionDAO();


        CustomerService service =new CustomerServiceImpl(customerDAO,transactionDAO);


        boolean result = service.login(1,  1234);


        assertTrue(result);
    }


    
    @Test
    void login_wrongPin_returnsFalse() {



        Customer customer =new Customer(1,1234,100);


        FakeCustomerDAO customerDAO =new FakeCustomerDAO(customer);


        FakeTransactionDAO transactionDAO =new FakeTransactionDAO();


        CustomerService service =new CustomerServiceImpl(customerDAO,transactionDAO  );


    
        boolean result =
                service.login(  1,9999);

        assertFalse(result);
    }


 
    private static class FakeCustomerDAO implements CustomerDAO {private Customer customer;


        public FakeCustomerDAO(Customer customer) {

            this.customer =customer;
        }


        @Override
        public Customer findByAccountId(int accountId) {

    
            if (customer.getAccountID()== accountId) {

                return customer;
            }


            return null;
        }




        @Override
        public int addCustomer(Customer customer) {

            return 0;
        }


        @Override
        public void updateBalance(int accountId,double newBalance) {

        }


        @Override
        public void transferFunds(int fromAccountId,int toAccountId,double amount) {

        }
    }



    private static class FakeTransactionDAO implements TransactionDAO {


        @Override
        public void addTransaction(Transaction transaction) {

        }


        @Override
        public List<Transaction>findByAccountId(int accountId) {

            return new ArrayList<>();
        }
    }
}