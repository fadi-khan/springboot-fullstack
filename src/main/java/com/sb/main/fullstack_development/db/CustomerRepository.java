package com.sb.main.fullstack_development.db;

import com.sb.main.fullstack_development.entities.Customer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
   boolean existsCustomerByEmail(String email);

    boolean existsCustomerByCustomerId(Integer customerId);

    Customer findCustomerByEmail(String email);

    @Transactional
    int deleteCustomerByCustomerId(Integer customerId);
}
