package com.crypto.trading.system.cryptotradingsystem.model.response;

import lombok.Data;

@Data
public class HuobiData {
    private String symbol;
    private Double bid;
    private Double bidSize;
    private Double ask;
    private Double askSize;
}
