package com.nttdata.mswallet.controller;

import com.nttdata.mswallet.model.Wallet;
import com.nttdata.mswallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/wallets")
public class WalletController {

    @Autowired
    private WalletService service;

    @GetMapping("/findAll")
    public Flux<Wallet> getWallets(){
        return service.findAll();
    }

    @GetMapping("/find/{id}")
    public Mono<ResponseEntity<Wallet>> getWallet(@PathVariable String id){
        return service.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Wallet> createWallet(@RequestBody Wallet w){
        return service.save(w);
    }

    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<Wallet>> updateWallet(@RequestBody Wallet w, @PathVariable String id){
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
