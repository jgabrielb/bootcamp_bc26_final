package com.nttdata.mswallet.model;


import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class PaymentWallet {
    private String phoneNumberOrig;
    private String phoneNumberDest;
    private BigDecimal amount;
}
