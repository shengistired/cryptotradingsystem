package com.crypto.trading.system.cryptotradingsystem.controller;

import com.crypto.trading.system.cryptotradingsystem.model.entity.CryptoWallet;
import com.crypto.trading.system.cryptotradingsystem.model.request.TradeCryptoRequest;
import com.crypto.trading.system.cryptotradingsystem.model.response.CryptoHistoryResponse;
import com.crypto.trading.system.cryptotradingsystem.model.response.CryptoPricesResponse;
import com.crypto.trading.system.cryptotradingsystem.model.response.SuccessResponse;
import com.crypto.trading.system.cryptotradingsystem.service.CryptoTransactionService;
import com.crypto.trading.system.cryptotradingsystem.service.RetrieveCryptoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/cryptoTradingSystem")
public class CryptoTradingSystemController {

    CryptoTransactionService cryptoTransactionService;
    RetrieveCryptoService retrieveCryptoService;

    public CryptoTradingSystemController(CryptoTransactionService cryptoTransactionService, RetrieveCryptoService retrieveCryptoService){
        this.cryptoTransactionService = cryptoTransactionService;
        this.retrieveCryptoService = retrieveCryptoService;
    }

    //Task 2
    @GetMapping("/retrieve/prices")
    ResponseEntity<CryptoPricesResponse> retrieveBestPrices(){
        log.info("Retrieve prices");
        return ResponseEntity.ok(retrieveCryptoService.retrievePrices());
    }

    //Task 3
    @PostMapping("/trade/{userId}")
    ResponseEntity<SuccessResponse> trade(@Valid @RequestBody TradeCryptoRequest tradeCryptoRequest, @PathVariable("userId") String userId){
        log.info("Start trade for: {}", userId);
        return ResponseEntity.ok(cryptoTransactionService.tradeCrypto(tradeCryptoRequest, userId));
    }

    //Task 4
    @GetMapping("/retrieve/balance/{userId}")
    ResponseEntity<CryptoWallet> retrieveWalletBalance(@PathVariable("userId") String userId){
        log.info("Retrieve balance: {}", userId);
        return ResponseEntity.ok(retrieveCryptoService.retrieveBalance(userId));
    }

    //Task 5
    @GetMapping("/retrieve/history/{userId}")
    ResponseEntity<CryptoHistoryResponse> retrieveTradeHistory(@PathVariable("userId") String userId){
        log.info("Retrieve history: {}", userId);
        return ResponseEntity.ok(retrieveCryptoService.retrieveHistory(userId));
    }
}
