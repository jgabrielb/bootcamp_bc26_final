package com.nttdata.mswallet.service.impl;

import com.nttdata.mswallet.config.CacheConfig;
import com.nttdata.mswallet.model.Wallet;
import com.nttdata.mswallet.repository.WalletRepository;
import com.nttdata.mswallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

@Service
@Transactional
public class WalletServiceImpl implements WalletService {

    private static Logger logger = LogManager.getLogger(WalletServiceImpl.class);

    @Autowired
    WalletRepository repository;

    @Override
    public Flux<Wallet> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Wallet> save(Wallet w) {
        return repository.save(w);
    }

    @Override
    @Cacheable(cacheNames = CacheConfig.WALLET_CACHE, unless = "#result == null")
    public Mono<Wallet> findById(String id) {
        return repository.findById(id);
    }

    @Override
    @CachePut(cacheNames = CacheConfig.WALLET_CACHE, key = "#id", unless = "#result == null")
    public Mono<Wallet> update(Wallet w, String id) {
        return repository.findById(id)
                .flatMap( x -> {
                    x.setDocumentNumber(w.getDocumentNumber());
                    x.setPhoneNumber(w.getPhoneNumber());
                    x.setEmail(w.getEmail());
                    x.setDebitCardNumber(w.getDebitCardNumber());
                    x.setWalletBalance(w.getWalletBalance());
                    return repository.save(x);
                });
    }

    @Override
    @CacheEvict(cacheNames = CacheConfig.WALLET_CACHE, key = "#id")
    public Mono<Wallet> delete(String id) {
        return repository.findById(id)
                .flatMap( x -> repository.delete(x)
                        .then(Mono.just(x)));
    }

    @Override
    public Mono<Wallet> updateByPhoneNumberAmountOrig(String phone, BigDecimal amount) {
        logger.info("Ejecutando metodo updateByPhoneNumberAmountOrig");
        logger.info("Telefono Orig: "+phone +" monto: "+amount);

        return repository.findAll()
                .filter( x -> x.getPhoneNumber().equals(phone) )
                .single()
                .flatMap( y -> {
                    y.setWalletBalance( y.getWalletBalance().subtract(amount) );
                    return repository.save(y);
                });
    }

    @Override
    public Mono<Wallet> updateByPhoneNumberAmountDest(String phone, BigDecimal amount) {
        logger.info("Ejecutando metodo updateByPhoneNumberAmountDest");
        logger.info("Telefono Dest: "+phone +" monto: "+amount);

        return repository.findAll()
                .filter( x -> x.getPhoneNumber().equals(phone) )
                .single()
                .flatMap( y -> {
                    y.setWalletBalance( y.getWalletBalance().add(amount) );
                    return repository.save(y);
                });
    }
}
