package com.nttdata.mspaymentwallet.service;

import com.nttdata.mspaymentwallet.model.PaymentWallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@EnableBinding(Source.class)
@Service
public class PaymentWalletService {

    @Autowired
    private Source source;

    @SendTo
    public boolean send(PaymentWallet message){
        return source.output().send(MessageBuilder.withPayload(message).build());
    }
}
