package com.sb.main.fullstack_development.db;

import com.sb.main.fullstack_development.AbstractTestContainers;
import com.sb.main.fullstack_development.entities.Customer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

class CustomerJdbcDaoTest extends AbstractTestContainers {

    private  CustomerJdbcDao underTest;
    private final CustomerRowMapper customerRowMapper = new CustomerRowMapper() ;

    @BeforeEach
    void setUp() {

        underTest=new CustomerJdbcDao(new JdbcTemplate(getDataSource()),customerRowMapper);
    }

    @Test
    void getAllCustomers() {

        Assertions.assertThat(underTest.getAllCustomers()).isNotNull();
    }

    @Test
    void getCustomerById() {
        //given

        var email = "amigos.ali@gmail.com";
        Customer customer = new Customer(
                "ali","muhammad",
               email ,"01212111",55
        );
        underTest.saveCustomer(customer);
        //will find the id for us
       int id= underTest.getAllCustomers()
                .stream()
                .filter(customer1 -> customer1.getEmail().equals(email))
                .map(Customer::getCustomerId)
                .findFirst()
               .orElseThrow();
       //when
        Optional<Customer> actual = underTest.getCustomerById(id);
       //then
       Assertions.assertThat (actual.isPresent()).isTrue();
        Assertions.assertThat(actual.get())
                .extracting("firstName", "lastName", "email", "phone", "age")
                .containsExactly("ali", "muhammad", email, "01212111", 55);

    }

    @Test
    void willReturnFalseWhenGetCustomerById() {

        int id =-1;
        Optional<Customer> actual = underTest.getCustomerById(id);

        Assertions.assertThat(actual.isPresent()).isFalse();


    }

    @Test
    void saveCustomer() {

        //given
        Customer customer = new Customer(
                "maham","muhammad",
                "maham.muhammad@gmail.com" ,"01212111",55);
        //when
        Assertions.assertThat(underTest.saveCustomer(customer)).isTrue();
    }


    @Test
    void updateCustomer() {
        Customer customer = new Customer(
                "maham","muhammad",
                "maham.muha554mmad@gmail.com" ,"01212111",55);
        customer.setCustomerId(333);
         underTest.saveCustomer(customer);
        System.out.println();
        customer
                .setAge(999);
        var actual = underTest.updateCustomer(customer);
        Assertions.assertThat(actual).isTrue();

        System.out.println();
    }

    @Test
    void deleteCustomer() {

//given
        Customer customer = new Customer(
                "maham","muhammad",
                "maham.muhammad@gmail.com" ,"01212111",55);
         underTest.saveCustomer(customer);

        int id = underTest.getAllCustomers()
                .stream()
                .filter(c1 -> c1.getEmail().equals(customer.getEmail()))
                .map(c1->c1.getCustomerId())
                .findFirst()
                .orElseThrow();
//when
        var actual = underTest.deleteCustomer(id);
//then
        Assertions.assertThat(actual).isTrue();

    }
}