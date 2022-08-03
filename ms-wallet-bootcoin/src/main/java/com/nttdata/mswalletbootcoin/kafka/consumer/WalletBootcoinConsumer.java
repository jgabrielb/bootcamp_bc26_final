package com.nttdata.mswalletbootcoin.kafka.consumer;

import com.nttdata.mswalletbootcoin.model.MarketBootcoin;
import com.nttdata.mswalletbootcoin.repository.WalletBootcoinRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@EnableBinding(Sink.class)
@Service
public class WalletBootcoinConsumer {

    private static Logger logger = LogManager.getLogger(WalletBootcoinConsumer.class);

    @Autowired
    WalletBootcoinRepository repository;

    @StreamListener(Sink.INPUT)
    public void readMessage(MarketBootcoin pw) {

        BigDecimal amountBuyed = pw.getAmountPEN().divide(pw.getActuallyTax()).setScale(2, RoundingMode.HALF_EVEN);

        // Actualizando monedero del comprador
        repository.findById(pw.getWalletBootcoinBuyer())
                .flatMap( x -> {
                    x.setAmountCoins( x.getAmountCoins().add( amountBuyed ) );
                    return repository.save(x);
                }).subscribe();

        // Actualizando monedero del vendedor
        repository.findById(pw.getWalletBootcoinVendor())
                .flatMap( x -> {
                    x.setAmountCoins( x.getAmountCoins().subtract( amountBuyed ) );
                    return repository.save(x);
                }).subscribe();
    }
}
