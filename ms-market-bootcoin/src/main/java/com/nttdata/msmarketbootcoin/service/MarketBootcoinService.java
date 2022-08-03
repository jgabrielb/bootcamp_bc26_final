package com.nttdata.msmarketbootcoin.service;

import com.nttdata.msmarketbootcoin.model.MarketBootcoin;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MarketBootcoinService {
    Flux<MarketBootcoin> findAll();

    Mono<MarketBootcoin> save(MarketBootcoin m);

    Mono<MarketBootcoin> findById(String id);

    Mono<MarketBootcoin> update(MarketBootcoin m, String id);

    Mono<MarketBootcoin> delete(String id);
}
