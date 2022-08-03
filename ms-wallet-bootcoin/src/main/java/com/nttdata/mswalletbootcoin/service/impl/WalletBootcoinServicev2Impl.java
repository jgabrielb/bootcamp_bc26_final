package com.nttdata.mswalletbootcoin.service.impl;

import com.nttdata.mswalletbootcoin.config.CacheConfig;
import com.nttdata.mswalletbootcoin.model.WalletBootcoin;
import com.nttdata.mswalletbootcoin.repository.WalletBootcoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
@ConditionalOnProperty(name = "cache.enabled", havingValue = "true")
public class WalletBootcoinServicev2Impl extends WalletBootcoinServiceImpl{

    private static final String KEY = "wallet_bootcoin";

    @Autowired
    WalletBootcoinRepository repository;

    @Autowired
    private ReactiveHashOperations<String, String, WalletBootcoin> hashOperations;


    @Override
    @Cacheable(cacheNames = CacheConfig.WALLET_BC_CACHE, unless = "#result == null")
    public Mono<WalletBootcoin> findById(String id) {
        return hashOperations.get(KEY, id)
                .switchIfEmpty(this.getFromDatabaseAndCache(id));
    }

    private Mono<WalletBootcoin> getFromDatabaseAndCache(String id) {
        return super.findById(id)
                .flatMap(dto -> this.hashOperations.put(KEY, id, dto)
                        .thenReturn(dto));
    }

}
