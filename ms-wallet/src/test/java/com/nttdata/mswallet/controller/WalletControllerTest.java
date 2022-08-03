package com.nttdata.mswallet.controller;

import com.nttdata.mswallet.model.Wallet;
import com.nttdata.mswallet.service.WalletService;
import com.nttdata.mswallet.service.impl.WalletServiceImpl;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = WalletController.class)
@Import(WalletServiceImpl.class)
class WalletControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private WalletService walletService;

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
        Mockito.when(walletService.save(Mockito.any())).thenReturn(Mono.just(walletMono));
        webTestClient.post().uri("/wallets/create")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(walletMono))
                .exchange()
                .expectStatus().isCreated();

        Mockito.verify(walletService,times(1)).save(walletMono);
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
        Mockito.when(walletService.update(walletMono, id))
                .thenReturn(Mono.just(walletMono));

        webTestClient.put().uri(uriBuilder -> uriBuilder
                        .path("/wallets/update/{id}")
                        .build(id))
                .body(Mono.just(walletMono), Wallet.class)
                .exchange()
                .expectStatus().isOk();
        Mockito.verify(walletService,times(1)).update(walletMono,id);
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

        Mockito.when(walletService.findAll()).thenReturn(Flux.just(walletMono));

        webTestClient.get()
                .uri("/wallets/findAll")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Wallet.class);

        Mockito.verify(walletService,times(1)).findAll();
    }

    @Test
    void FindById() {

        Wallet wallet = new Wallet();
        wallet.setId("676766831245daswae9");
        wallet.setDocumentNumber("75854321");
        wallet.setPhoneNumber("987654321");
        wallet.setEmail("correo@prueba.com");
        wallet.setDebitCardNumber("9014876537658736");
        wallet.setWalletBalance(BigDecimal.valueOf(176));

        Mockito.when(walletService.findById("676766831245daswae9")).thenReturn(Mono.just(wallet));

        webTestClient.get()
                .uri("/wallets/find/{id}","676766831245daswae9")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.id").isEqualTo("676766831245daswae9")
                .jsonPath("$.documentNumber").isEqualTo("75854321")
                .jsonPath("$.phoneNumber").isEqualTo("987654321")
                .jsonPath("$.email").isEqualTo("correo@prueba.com")
                .jsonPath("$.debitCardNumber").isEqualTo("9014876537658736")
                .jsonPath("$.walletBalance").isEqualTo(BigDecimal.valueOf(176));


        Mockito.verify(walletService,times(1)).findById("676766831245daswae9");
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
        String id = "676766831245daswae9";

        Mockito.when(walletService.delete(id))
                .thenReturn(Mono.just(walletMono));

        webTestClient.delete().uri("/wallets/delete/{id}", id)
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(walletService,times(1)).delete(id);

    }
}