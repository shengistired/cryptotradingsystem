package com.crypto.trading.system.cryptotradingsystem.repository;

import com.crypto.trading.system.cryptotradingsystem.model.entity.CryptoData;
import com.crypto.trading.system.cryptotradingsystem.model.entity.CryptoWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CryptoWalletRepository extends JpaRepository<CryptoWallet, String> {

    Optional<CryptoWallet> findByUserId(String userId);

}
