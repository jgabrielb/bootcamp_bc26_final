package com.nttdata.mswalletbootcoin.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "schema_wallet.wallet_bootcoin")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalletBootcoin {
    @Id
    private String id;

    private String documentNumber;

    private String phoneNumber;

    private String email;

    private BigDecimal amountCoins;

    private Boolean isVendor;

    private String otherWalletId;

    private String accountId;
}
