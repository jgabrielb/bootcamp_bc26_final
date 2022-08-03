package com.nttdata.msmarketbootcoin.repository;

import com.nttdata.msmarketbootcoin.model.MarketBootcoin;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketBootcoinRepository extends ReactiveCrudRepository<MarketBootcoin, String> {
}
