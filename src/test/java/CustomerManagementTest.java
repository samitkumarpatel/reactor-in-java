import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerManagementTest {
    CustomerService customerService = new CustomerService();

    @Test
    @DisplayName("Optional class related test")
    void optionalTest() {

    }

    @Test
    @DisplayName("fetAll Customer Test")
    void fetchAll() {
        Flux<Customer> customerFlux = customerService.fetchAll();
        StepVerifier.create(customerFlux).expectNextCount(4).verifyComplete();
    }

    @Test
    @DisplayName("getOne Customer By Id Test")
    void getOneCustomerByIdTest() {
        Mono<Customer> customerMono = customerService.getById(2l);
        StepVerifier.create(customerMono).consumeNextWith( customer -> {
            assertAll("customer" ,
                    () -> assertEquals(2l, customer.id, "Id should match"),
                    () -> assertEquals("Customer Two", customer.name, "name should match")
            );
        }).verifyComplete();
    }
}

//Pojo
@Data @Builder @AllArgsConstructor @NoArgsConstructor
class Customer {
    Long id;
    String name;
    int age;
    List<Address> address;
}

@Data @Builder @AllArgsConstructor @NoArgsConstructor
class Address {
    long id;
    String houseNumber;
    String landMark;
    String city;
    String zipCode;
    String state;
    String country;
}

class CustomerNotFound extends RuntimeException {
    CustomerNotFound(String message) {
        super(message);
    }
}

//Service
class CustomerService {
    private final Flux<Customer> allCustomer = Flux.just(
            Customer.builder().id(1l).name("Customer One").age(30).build(),
            Customer.builder().id(2l).name("Customer Two").age(29).build(),
            Customer.builder().id(3l).name("Customer Three").age(46).build(),
            Customer.builder().id(4l).name("Customer Four").age(22).build()
    );

    public Flux<Customer> fetchAll() {
        return allCustomer;
    }

    public Mono<Customer> getById(long id) {
        return allCustomer
                .map(customer -> customer)
                .filter(customer -> customer.id == id)
                .singleOrEmpty()
                .switchIfEmpty(Mono.error(new CustomerNotFound("not found")));
    }
}