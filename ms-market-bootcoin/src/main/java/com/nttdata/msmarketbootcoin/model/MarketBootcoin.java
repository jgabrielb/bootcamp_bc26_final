package com.nttdata.msmarketbootcoin.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "schema_wallet.market_bootcoin")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarketBootcoin {
    @Id
    private String id;

    private BigDecimal amountPEN;

    private BigDecimal actuallyTax;

    private String status;

    private String walletBootcoinBuyer;

    private String walletBootcoinVendor;

    private String transactionNumber;
}
