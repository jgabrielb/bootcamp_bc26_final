package com.nttdata.msmarketbootcoin.kafka.producer;

import com.nttdata.msmarketbootcoin.model.MarketBootcoin;
import com.nttdata.msmarketbootcoin.repository.MarketBootcoinRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@EnableBinding(Source.class)
@Service
public class MarketBootcoinProducer {

    @Autowired
    MarketBootcoinRepository repository;

    @Autowired
    private Source source;

    @SendTo
    public void send(MarketBootcoin message, String id){
        if (message.getWalletBootcoinVendor().isEmpty()){
            throw new RuntimeException("El monedero virtual del vendedor no puede ser null o vacio");
        }else{
            repository.findById(id)
                    .flatMap( x -> {
                        x.setWalletBootcoinVendor(message.getWalletBootcoinVendor());
                        x.setTransactionNumber(ObjectId.get().toString());
                        x.setStatus("BUYED");
                        return repository.save(x);
                    })
                    .subscribe( y -> {
                        source.output().send(MessageBuilder.withPayload(y).build());
                    });
        }
    }
}
