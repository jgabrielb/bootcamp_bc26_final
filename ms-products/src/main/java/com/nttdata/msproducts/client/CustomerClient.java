package com.nttdata.msproducts.client;

import com.nttdata.msproducts.model.Customer;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CustomerClient {
    private WebClient client = WebClient.create("http://ms-customers:9002/customers");

    public Mono<Customer> getCustomer(String id){
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/find/{id}")
                        .build(id)
                )
                .retrieve()
                .bodyToMono(Customer.class);
    };
}
