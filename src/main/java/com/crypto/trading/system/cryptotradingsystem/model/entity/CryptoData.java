package com.crypto.trading.system.cryptotradingsystem.model.entity;

import com.crypto.trading.system.cryptotradingsystem.model.response.BinanceData;
import com.crypto.trading.system.cryptotradingsystem.model.response.HuobiData;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "crypto_data")
@NoArgsConstructor
@AllArgsConstructor
public class CryptoData {
    @Id
    @JsonIgnore
    @Column(name = "id")
    private String id;
    @Column(name = "symbol")
    private String symbol;
    @Column(name = "bid_price")
    private Double bidPrice;
    @Column(name = "bid_qty")
    private Double bidQty;
    @Column(name = "ask_price")
    private Double askPrice;
    @Column(name = "ask_qty")
    private Double askQty;
    @Column(name = "source")
    private String source; //HUOBI VS BINANCE
    @JsonIgnore
    @Column(name = "update_timestamp")
    private Timestamp updateTimestamp;

    public CryptoData(HuobiData huobiData){
        this.id = UUID.randomUUID().toString();
        this.symbol = huobiData.getSymbol().toUpperCase();
        this.bidPrice = huobiData.getBid();
        this.bidQty = huobiData.getBidSize();
        this.askPrice = huobiData.getAsk();
        this.askQty = huobiData.getAskSize();
        this.source = "HUOBI";
        this.updateTimestamp = new Timestamp(System.currentTimeMillis());
    }

    public CryptoData(BinanceData binanceData){
        this.id = UUID.randomUUID().toString();
        this.symbol = binanceData.getSymbol().toUpperCase();
        this.bidPrice = Double.parseDouble(binanceData.getBidPrice());
        this.bidQty = Double.parseDouble(binanceData.getBidQty());
        this.askPrice = Double.parseDouble(binanceData.getAskPrice());
        this.askQty = Double.parseDouble(binanceData.getAskQty());
        this.source = "BINANCE";
        this.updateTimestamp = new Timestamp(System.currentTimeMillis());
    }
}
