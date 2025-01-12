package com.sb.main.fullstack_development.journey;

import com.sb.main.fullstack_development.entities.Customer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerIntegrationTest {

    @Autowired
    private WebTestClient client;

    private static final String BASE_URL = "api/v1/customers";


    @Test
    void canRegisterACustomer() {


        String email= "bachOp53.buff@gmail.com";

        Customer customer = new Customer(
                "gulabo",
                "buff",
                email,
                "1316163161",
                99
        );

        // inserting or adding a customer
        client.post().uri(BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(customer),Customer.class)
                .exchange()
                .expectStatus().isOk();
//         get all customers
        List<Customer> getAllCustomers = client.get()
                .uri(BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {
                })
                .returnResult().getResponseBody();
        Assertions.assertThat(getAllCustomers).usingRecursiveFieldByFieldElementComparatorIgnoringFields("customerId")
                .contains(customer);

    // get customer by id
      int id =getAllCustomers
                .stream()
                        .filter((c)->
                             c.getEmail().equals(email))
                                .map(Customer::getCustomerId)
                                        .findFirst()
                                                .orElseThrow();


      customer.setCustomerId(id);
        client.get()
                .uri(BASE_URL+"/{id}",id)
                .exchange()
                .expectStatus().isOk();


    }

    @Test
    void canDeleteACustomer() {
        String email= "bundlesgulabo.buff@gmail.com";

        Customer customer = new Customer(
                "mahita241muu",
                "buff",
                email,
                "1316163161",
                99
        );

        // inserting or adding a customer
        client.post().uri(BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(customer),Customer.class)
                .exchange()
                .expectStatus().isOk();
//         get all customers
        List<Customer> getAllCustomers = client.get()
                .uri(BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {
                })
                .returnResult().getResponseBody();

        // get customer by id
        int id =getAllCustomers
                .stream()
                .filter((c)->
                        c.getEmail().equals(email))
                .map(Customer::getCustomerId)
                .findFirst()
                .orElseThrow();



        //deleting the customer
        client.delete()
                .uri(BASE_URL+"/{id}",id)
                .exchange()
                .expectStatus().isOk();
        client.get()
                .uri(BASE_URL+"/{id}",id)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void canUpdateCustomer() {

        String email= "banoo.buff@gmail.com";

        Customer customer = new Customer(
                "mahita241muu",
                "updatedLastName",
                email,
                "updatedPhoneNumber",
                99
        );

        //updating the customer

        client.put()
                .uri(BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(customer),Customer.class)
                .exchange()
                .expectStatus().isOk();

    }
}
