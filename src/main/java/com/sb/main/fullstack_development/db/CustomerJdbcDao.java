package com.sb.main.fullstack_development.db;


import com.sb.main.fullstack_development.entities.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerJdbcDao implements CustomerService {

    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper ;


    public CustomerJdbcDao(JdbcTemplate jdbcTemplate, CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }

    @Override
    public List<Customer> getAllCustomers() {

      var  sqlScript="select * from customers";
        return jdbcTemplate.query(sqlScript, customerRowMapper);
    }

    @Override
    public Optional<Customer> getCustomerById(int id) {


       var sqlScript="select * from customers where customer_id=?";

        return jdbcTemplate.query(sqlScript,customerRowMapper,id).stream().findFirst();

    }
    @Override
    public boolean saveCustomer(Customer customer) {

       var  sqlScript="insert into customers (first_name, last_name, email, phone,age)  values(?,?,?,?,?)";
       var status = jdbcTemplate.update(
                sqlScript,
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getAge());
        return status > 0;

    }
    @Override
    public boolean updateCustomer(Customer customer) {
       var sqlScript = "UPDATE customers SET first_name=?,last_name=?,email=?,phone=?,age=? WHERE customer_id=? OR email=? ";
      var  status = jdbcTemplate.update(
                sqlScript,customer.getFirstName(),
                customer.getLastName(),customer.getEmail(),
                customer.getPhone(),customer.getAge(),customer.getCustomerId(),customer.getEmail());

        return status>0;
    }

    @Override
    public boolean deleteCustomer(int id) {

      var  sqlScript = "delete from customers where customer_id=?";
     var   status = jdbcTemplate.update(sqlScript,id);

        return status>0;
    }
}
