package com.nttdata.mswallet.service;

import com.nttdata.mswallet.model.PaymentWallet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Service;
@Service
@EnableBinding(Sink.class)
public class WalletKafkaService {

    private static Logger logger = LogManager.getLogger(WalletKafkaService.class);

    @Autowired
    WalletService service;

    @StreamListener(Sink.INPUT)
    public void readMessage(PaymentWallet pw) {
        logger.info("mensaje del topico: "+ pw);
        if(!pw.getPhoneNumberOrig().equals(pw.getPhoneNumberDest())){

            // Actualizando saldo de la cuenta origen
            logger.info("actualizando monto de la cuenta origen: "+ pw.getPhoneNumberOrig());
            service.updateByPhoneNumberAmountOrig(pw.getPhoneNumberOrig(),pw.getAmount()).subscribe();

            // Actualizando saldo de la cuenta destino
            logger.info("actualizando monto de la cuenta destino: "+ pw.getPhoneNumberDest());
            service.updateByPhoneNumberAmountDest(pw.getPhoneNumberDest(),pw.getAmount()).subscribe();

            logger.info("Fin de ejecución del kafka-service");

        }else{
            throw new RuntimeException("No se puede realizar la operación de pago para el mismo número");
        }
    }
}
