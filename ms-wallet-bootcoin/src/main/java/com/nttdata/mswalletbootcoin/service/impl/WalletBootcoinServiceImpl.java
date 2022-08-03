package com.nttdata.mswalletbootcoin.service.impl;

import com.nttdata.mswalletbootcoin.model.WalletBootcoin;
import com.nttdata.mswalletbootcoin.repository.WalletBootcoinRepository;
import com.nttdata.mswalletbootcoin.service.WalletBootcoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
@Transactional
@ConditionalOnProperty(name = "cache.enabled", havingValue = "false")
public class WalletBootcoinServiceImpl implements WalletBootcoinService {

    private static Logger logger = LogManager.getLogger(WalletBootcoinServiceImpl.class);

    @Autowired
    WalletBootcoinRepository repository;

    @Override
    public Flux<WalletBootcoin> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<WalletBootcoin> save(WalletBootcoin w) {
        return repository.save(w);
    }

    @Override
    public Mono<WalletBootcoin> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Mono<WalletBootcoin> findByPhone(String p) {
        return repository.findAll()
                .filter( x -> x.getPhoneNumber().equals(p))
                .single();
    }

    @Override
    public Mono<WalletBootcoin> update(WalletBootcoin w, String id) {
        return repository.findById(id)
                .flatMap( x -> {

                    x.setAccountId(w.getAccountId());
                    x.setAmountCoins(w.getAmountCoins());
                    x.setDocumentNumber(w.getDocumentNumber());
                    x.setPhoneNumber(w.getPhoneNumber());
                    x.setEmail(w.getEmail());
                    x.setIsVendor(w.getIsVendor());
                    x.setOtherWalletId(w.getOtherWalletId());

                    return repository.save(x);
                });
    }

    @Override
    public Mono<WalletBootcoin> delete(String id) {
        return repository.findById(id)
                .flatMap( x -> repository.delete(x)
                        .then(Mono.just(x)));
    }
}
