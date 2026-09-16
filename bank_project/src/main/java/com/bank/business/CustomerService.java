package com.bank.business;

import com.bank.domain.Customer;
import java.util.List;

public interface CustomerService {
    void addCustmoer(Customer customer);
    Customer findCustomer(int id);
    List<Customer> findAllCustomers();
    void updateCustomer(Customer customer);
    void deleteCustomer(int id);
}
