package com.nttdata.msmarketbootcoin.controller;

import com.nttdata.msmarketbootcoin.kafka.producer.MarketBootcoinProducer;
import com.nttdata.msmarketbootcoin.model.MarketBootcoin;
import com.nttdata.msmarketbootcoin.service.MarketBootcoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/market-bc")
public class MarketBootcoinController {
    @Autowired
    private MarketBootcoinService service;

    @Autowired
    private MarketBootcoinProducer serviceProducer;

    @GetMapping("/findAll")
    public Flux<MarketBootcoin> getMarkets(){
        return service.findAll();
    }

    @GetMapping("/find/{id}")
    public Mono<ResponseEntity<MarketBootcoin>> getMarket(@PathVariable String id){
        return service.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MarketBootcoin> createMarket(@RequestBody MarketBootcoin m){
        return service.save(m);
    }

    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<MarketBootcoin>> updateMarket(@RequestBody MarketBootcoin m, @PathVariable String id){
        return service.update(m,id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<Void>> deleteMarket(@PathVariable String id){
        return service.delete(id)
                .map( r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/buyBootcoin/{id}")
    public void send(@RequestBody MarketBootcoin message, @PathVariable String id){
        serviceProducer.send(message,id);
    }
}
