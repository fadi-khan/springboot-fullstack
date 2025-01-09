package com.sb.main.fullstack_development.db;

import com.sb.main.fullstack_development.AbstractTestContainers;
import com.sb.main.fullstack_development.entities.Customer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest extends AbstractTestContainers {


    @Autowired
   private  CustomerRepository underTest;




    @BeforeEach
    void setUp() {

     underTest.deleteAll();

    }

    @Test
    void existsCustomerByEmail() {

     var email = "amigos.ali@gmail.com";
     Customer customer = new Customer(
             "ali","muhammad",
             email ,"01212111",55
     );
     underTest.save(customer);

     var actual = underTest.existsCustomerByEmail(email);

     Assertions.assertThat(actual).isTrue();
    }

    @Test
    void existsCustomerByCustomer_id() {


     var email = "amigos.ali@gmail.com";
     Customer customer = new Customer(
             "ali","muhammad",
             email ,"01212111",55
     );
     underTest.save(customer);

     Integer id = underTest.findAll()
             .stream()
             .filter(e -> e.getEmail().equals(email))
             .map(c->c.getCustomerId())
             .findFirst()
             .orElseThrow();
     var actual = underTest.existsCustomerByCustomerId(id);

     Assertions.assertThat(actual).isTrue();

    }
}