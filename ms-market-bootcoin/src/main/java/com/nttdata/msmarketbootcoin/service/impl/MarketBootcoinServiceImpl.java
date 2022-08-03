package com.nttdata.msmarketbootcoin.service.impl;

import com.nttdata.msmarketbootcoin.model.MarketBootcoin;
import com.nttdata.msmarketbootcoin.repository.MarketBootcoinRepository;
import com.nttdata.msmarketbootcoin.service.MarketBootcoinService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class MarketBootcoinServiceImpl implements MarketBootcoinService {

    private static Logger logger = LogManager.getLogger(MarketBootcoinServiceImpl.class);

    @Autowired
    MarketBootcoinRepository repository;

    @Override
    public Flux<MarketBootcoin> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<MarketBootcoin> save(MarketBootcoin m) {
        return repository.save(m);
    }

    @Override
    public Mono<MarketBootcoin> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Mono<MarketBootcoin> update(MarketBootcoin m, String id) {
        return repository.findById(id)
                .flatMap( x -> {
                    x.setAmountPEN(m.getAmountPEN());
                    x.setActuallyTax(m.getActuallyTax());
                    x.setWalletBootcoinBuyer(m.getWalletBootcoinBuyer());
                    x.setTransactionNumber(m.getTransactionNumber());
                    x.setStatus(m.getStatus());
                    x.setWalletBootcoinVendor(m.getWalletBootcoinVendor());
                    return repository.save(x);
                });
    }

    @Override
    public Mono<MarketBootcoin> delete(String id) {
        return repository.findById(id)
                .flatMap( x -> repository.delete(x)
                        .then(Mono.just(x)));
    }
}
