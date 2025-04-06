package com.crypto.trading.system.cryptotradingsystem.model.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "crypto_item")
public class CryptoItem {
    @Id
    @JsonIgnore
    @Column(name = "id")
    private String id;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "qty")
    private Double qty;

    @Column(name = "crypto_value")
    private Double value;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")  // The foreign key should point to 'user_id' in the CryptoWallet table
    @JsonBackReference
    private CryptoWallet cryptoWallet;

}
