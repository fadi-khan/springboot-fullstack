package com.sb.main.fullstack_development.db;

import com.sb.main.fullstack_development.entities.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class CustomerDaoTest {


    private CustomerDao underTest;

    @Mock
    private CustomerRepository customerRepository;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
       autoCloseable=  MockitoAnnotations.openMocks(this);
       underTest = new CustomerDao(customerRepository);


    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getAllCustomers() {
        underTest.getAllCustomers();

        Mockito.verify(customerRepository).findAll();
    }

    @Test
    void getCustomerById() {
        int id= 1;
        underTest.getCustomerById(id);
        Mockito.verify(customerRepository).findById(id);

    }

    @Test
    void saveCustomer() {

        Customer customer= new Customer(
                "fahad",
                "khan",
                "fahad.khan@gmail.com",
                "03089275702",
                25
        );
       underTest.saveCustomer(customer);
       Mockito.verify(customerRepository).save(customer);
        Mockito.when(customerRepository.save(customer)).thenReturn(customer);


    }

    @Test
    void updateCustomer() {

        Customer customer= new Customer(
                "fahad",
                "khan",
                "fahad.khan@gmail.com",
                "03089275702",
                25
        );

        underTest.updateCustomer(customer);
        Mockito.verify(customerRepository).save(customer);
        Mockito.when(customerRepository.save(customer)).thenReturn(customer);
        Mockito.when(customerRepository.existsCustomerByEmail(customer.getEmail())).thenReturn(true);

    }

    @Test
    void deleteCustomer() {

        int id = 1;

        underTest.deleteCustomer(id);
        Mockito.verify(customerRepository).deleteCustomerByCustomerId(id);

    }
}