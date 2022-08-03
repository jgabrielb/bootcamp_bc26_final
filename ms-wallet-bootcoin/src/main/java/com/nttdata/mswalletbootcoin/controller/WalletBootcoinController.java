package com.nttdata.mswalletbootcoin.controller;

import com.nttdata.mswalletbootcoin.model.WalletBootcoin;
import com.nttdata.mswalletbootcoin.service.WalletBootcoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/wallets-bc")
public class WalletBootcoinController {

    @Autowired
    private WalletBootcoinService service;

    @GetMapping("/findAll")
    public Flux<WalletBootcoin> getWallets(){
        return service.findAll();
    }

    @GetMapping("/find/{id}")
    public Mono<ResponseEntity<WalletBootcoin>> getWallet(@PathVariable String id){
        return service.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/findByDate/{phone}")
    public Mono<ResponseEntity<WalletBootcoin>> getWalletByPhone(@PathVariable String phone){
        return service.findByPhone(phone)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<WalletBootcoin> createWallet(@RequestBody WalletBootcoin w){
        return service.save(w);
    }

    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<WalletBootcoin>> updateWallet(@RequestBody WalletBootcoin w, @PathVariable String id){
        return service.update(w,id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<Void>> deleteWallet(@PathVariable String id){
        return service.delete(id)
                .map( r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
