package com.nttdata.mswalletbootcoin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MsWalletBootcoinApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsWalletBootcoinApplication.class, args);
    }

}
