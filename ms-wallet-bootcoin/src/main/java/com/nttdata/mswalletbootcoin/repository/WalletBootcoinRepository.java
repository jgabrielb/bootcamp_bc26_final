package com.nttdata.mswalletbootcoin.repository;

import com.nttdata.mswalletbootcoin.model.WalletBootcoin;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletBootcoinRepository extends ReactiveCrudRepository<WalletBootcoin, String> {
}
