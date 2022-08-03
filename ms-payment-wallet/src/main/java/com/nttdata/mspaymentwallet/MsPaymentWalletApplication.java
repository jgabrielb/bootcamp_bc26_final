package com.nttdata.mspaymentwallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MsPaymentWalletApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsPaymentWalletApplication.class, args);
    }

}
