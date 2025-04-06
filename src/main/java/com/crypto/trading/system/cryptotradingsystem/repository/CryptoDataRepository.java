package com.crypto.trading.system.cryptotradingsystem.repository;

import com.crypto.trading.system.cryptotradingsystem.model.entity.CryptoData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CryptoDataRepository extends JpaRepository<CryptoData, String> {

    //Get the best price for buying
    Optional<CryptoData> findTopBySymbolOrderByAskPriceAsc(String symbol);

    //Get the best price for selling
    Optional<CryptoData> findTopBySymbolOrderByBidPriceDesc(String symbol);
}
