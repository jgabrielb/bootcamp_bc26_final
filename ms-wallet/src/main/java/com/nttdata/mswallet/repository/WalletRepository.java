package com.nttdata.mswallet.repository;

import com.nttdata.mswallet.model.Wallet;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends ReactiveCrudRepository<Wallet, String> {
}
