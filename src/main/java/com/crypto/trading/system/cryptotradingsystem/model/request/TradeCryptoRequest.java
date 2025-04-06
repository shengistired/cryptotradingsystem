package com.crypto.trading.system.cryptotradingsystem.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TradeCryptoRequest {
    @NotBlank
    private String type; //SELL, BUY
    @NotBlank
    private String symbol;
//    @NotNull
//    private Double price;
    @NotNull
    private Double qty;
}
