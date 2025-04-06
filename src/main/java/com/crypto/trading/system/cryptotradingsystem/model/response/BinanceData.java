package com.crypto.trading.system.cryptotradingsystem.model.response;

import lombok.Data;

@Data
public class BinanceData {
    private String symbol;
    private String bidPrice;
    private String bidQty;
    private String askPrice;
    private String askQty;
}
