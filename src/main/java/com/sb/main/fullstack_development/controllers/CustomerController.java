package com.sb.main.fullstack_development.controllers;

import com.sb.main.fullstack_development.db.CustomerDao;
import com.sb.main.fullstack_development.db.CustomerJdbcDao;
import com.sb.main.fullstack_development.entities.Customer;
import com.sb.main.fullstack_development.exceptions.CustomerNotFoundException;
import com.sb.main.fullstack_development.exceptions.DuplicateCustomerException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerDao db;
    private final CustomerJdbcDao jdbcDao;


    public CustomerController(CustomerDao db, CustomerJdbcDao jdbcDao) {
        this.db = db;
        this.jdbcDao = jdbcDao;
    }

    @GetMapping("/")
    String home (){
        return "index.html";
    }


    @GetMapping
    List<Customer> getAllCustomers() {

        return db.getAllCustomers();
    }

    @GetMapping("{id}")
    Customer getCustomerById(@PathVariable("id")  int id) {


           return db
                   .getCustomerById(id)
                   .orElseThrow(()->new CustomerNotFoundException("not found customer with id " ));

    }

    @PostMapping
    void addCustomer(@RequestBody Customer customer) {

        if (!db.saveCustomer(customer)) {
            throw new DuplicateCustomerException("customer already exists");
        }
        db.saveCustomer(customer);

    }
    @DeleteMapping("{id}")
    void deleteCustomerById(@PathVariable("id")  int id) {
         db.deleteCustomer(id);
    }

    @PutMapping("")
    void updateCustomer( @RequestBody Customer customer) {



        jdbcDao.updateCustomer(customer);
    }




}
