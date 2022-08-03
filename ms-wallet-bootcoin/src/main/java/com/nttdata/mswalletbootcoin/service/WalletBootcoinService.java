package com.nttdata.mswalletbootcoin.service;

import com.nttdata.mswalletbootcoin.model.WalletBootcoin;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WalletBootcoinService {

    Flux<WalletBootcoin> findAll();

    Mono<WalletBootcoin> save(WalletBootcoin w);

    Mono<WalletBootcoin> findById(String id);

    Mono<WalletBootcoin> findByPhone(String p);

    Mono<WalletBootcoin> update(WalletBootcoin w, String id);

    Mono<WalletBootcoin> delete(String id);
}
