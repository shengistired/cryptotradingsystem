package com.crypto.trading.system.cryptotradingsystem.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "crypto_transaction_history")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CryptoHistory {
    @Id
    @JsonIgnore
    @Column(name = "id")
    private String id;
    @JsonIgnore
    @Column(name = "user_id")
    private String userId;
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
    @JsonIgnore
    @Column(name = "source_from")
    private String source; //HUOBI VS BINANCE
    @JsonIgnore
    @Column(name = "update_timestamp")
    private Timestamp updateTimestamp;
}
