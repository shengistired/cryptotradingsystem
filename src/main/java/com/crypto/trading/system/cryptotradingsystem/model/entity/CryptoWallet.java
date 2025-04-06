package com.crypto.trading.system.cryptotradingsystem.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "crypto_wallet")
public class CryptoWallet {
    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "usdt")
    private Double usdt;

    @JsonIgnore
    @Column(name = "latest_transaction")
    private String latestTransaction;

    @JsonIgnore
    @Column(name = "latest_purchase_timestamp")
    private Timestamp latestPurchaseTimestamp;

    // Ensure this matches the field name in CryptoItem (cryptoWallet)
    @OneToMany(mappedBy = "cryptoWallet", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<CryptoItem> cryptoItems;

}
