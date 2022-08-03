package com.nttdata.mspaymentwallet.controller;

import com.nttdata.mspaymentwallet.model.PaymentWallet;
import com.nttdata.mspaymentwallet.service.PaymentWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paymentwallet")
public class PaymentWalletController {

    @Autowired
    private PaymentWalletService service;

    @PostMapping("/sendMessage")
    public void send(@RequestBody PaymentWallet message){
        service.send(message);
    }
}
