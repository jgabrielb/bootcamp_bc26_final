package com.nttdata.mswallet.service;

import com.nttdata.mswallet.model.Wallet;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface WalletService {

    Flux<Wallet> findAll();

    Mono<Wallet> save(Wallet w);

    Mono<Wallet> findById(String id);

    Mono<Wallet> update(Wallet w, String id);

    Mono<Wallet> delete(String id);

    Mono<Wallet> updateByPhoneNumberAmountOrig(String phone, BigDecimal amount);

    Mono<Wallet> updateByPhoneNumberAmountDest(String phone, BigDecimal amount);
}
