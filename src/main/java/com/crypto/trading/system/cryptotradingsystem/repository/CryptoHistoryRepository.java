package com.crypto.trading.system.cryptotradingsystem.repository;

import com.crypto.trading.system.cryptotradingsystem.model.entity.CryptoHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CryptoHistoryRepository extends JpaRepository<CryptoHistory, String> {

    List<CryptoHistory> findAllByUserId(String userId);

}
