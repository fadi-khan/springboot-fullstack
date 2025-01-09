package com.sb.main.fullstack_development.db;

import com.sb.main.fullstack_development.entities.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {


    List<Customer> getAllCustomers();
    Optional<Customer> getCustomerById(int id);
    boolean saveCustomer(Customer customer);
    boolean updateCustomer(Customer customer);
    boolean deleteCustomer(int id);

}
