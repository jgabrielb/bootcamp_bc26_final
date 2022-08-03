package com.nttdata.mswallet.service.impl;

import com.nttdata.mswallet.model.Wallet;
import com.nttdata.mswallet.repository.WalletRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class WalletServiceImplTest {
    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WalletServiceImpl walletServiceImpl;



    @Test
    void createWalletTest() {
        Wallet walletMono = Wallet.builder()
                .id(ObjectId.get().toString())
                .documentNumber("75854321")
                .phoneNumber("987654321")
                .email("correo@prueba.com")
                .debitCardNumber("9014876537658736")
                .walletBalance(BigDecimal.valueOf(176))
                .build();

        Mockito.when(walletRepository.save(Mockito.any())).thenReturn(Mono.just(walletMono));

        assertDoesNotThrow(() -> walletServiceImpl.save(walletMono)
                .subscribe(response -> {
                    assertEquals(walletMono.getDocumentNumber(), response.getDocumentNumber());
                }));
    }
    @Test
    void updateWalletTest() {
        Wallet walletMono = Wallet.builder()
                .id(ObjectId.get().toString())
                .documentNumber("75854321")
                .phoneNumber("987654321")
                .email("correo@prueba.com")
                .debitCardNumber("9014876537658736")
                .walletBalance(BigDecimal.valueOf(176))
                .build();
        String id = "676766831245daswae9";

        Mockito.when(walletRepository.findById(id)).thenReturn(Mono.just(walletMono));
        Mockito.when(walletRepository.save(walletMono)).thenReturn(Mono.just(walletMono));

        assertDoesNotThrow(() -> walletServiceImpl.update(walletMono,id)
                .subscribe(response -> {
                    assertEquals(walletMono.getDocumentNumber(), response.getDocumentNumber());
                }));
    }

    @Test
    void findAll() {
        Wallet walletMono = Wallet.builder()
                .id(ObjectId.get().toString())
                .documentNumber("75854321")
                .phoneNumber("987654321")
                .email("correo@prueba.com")
                .debitCardNumber("9014876537658736")
                .walletBalance(BigDecimal.valueOf(176))
                .build();

        Mockito.when(walletRepository.findAll()).thenReturn(Flux.just(walletMono));

        assertDoesNotThrow(() -> walletServiceImpl.findAll()
                .subscribe(response -> {
                    assertEquals(walletMono.getDocumentNumber(), response.getDocumentNumber());
                }));
    }

    @Test
    void FindById() {

        Wallet walletMono = Wallet.builder()
                .id("2346723847262")
                .documentNumber("75854321")
                .phoneNumber("987654321")
                .email("correo@prueba.com")
                .debitCardNumber("9014876537658736")
                .walletBalance(BigDecimal.valueOf(176))
                .build();

        Mockito.when(walletRepository.findById("2346723847262")).thenReturn(Mono.just(walletMono));

        assertDoesNotThrow(() -> walletServiceImpl.findById(walletMono.getId())
                .subscribe(response -> {
                    assertEquals(walletMono.getDocumentNumber(), response.getDocumentNumber());
                }));
    }

    @Test
    void Delete() {

        Wallet walletMono = Wallet.builder()
                .id(ObjectId.get().toString())
                .documentNumber("75854321")
                .phoneNumber("987654321")
                .email("correo@prueba.com")
                .debitCardNumber("9014876537658736")
                .walletBalance(BigDecimal.valueOf(176))
                .build();
        String id = "6767668789fds9";

        Mockito.when(walletRepository.findById("6767668789fds9")).thenReturn(Mono.just(walletMono));
        Mockito.when(walletRepository.delete(walletMono)).thenReturn(Mono.empty());

        assertDoesNotThrow(() -> walletServiceImpl.delete(id)
                .subscribe(response -> {
                    assertEquals(new Wallet(), response);
                }));

    }
}