package com.crypto.trading.system.cryptotradingsystem.service;

import com.crypto.trading.system.cryptotradingsystem.exception.InvalidDataException;
import com.crypto.trading.system.cryptotradingsystem.model.entity.CryptoData;
import com.crypto.trading.system.cryptotradingsystem.model.entity.CryptoHistory;
import com.crypto.trading.system.cryptotradingsystem.model.entity.CryptoWallet;
import com.crypto.trading.system.cryptotradingsystem.model.response.CryptoHistoryResponse;
import com.crypto.trading.system.cryptotradingsystem.model.response.CryptoPricesResponse;
import com.crypto.trading.system.cryptotradingsystem.repository.CryptoDataRepository;
import com.crypto.trading.system.cryptotradingsystem.repository.CryptoHistoryRepository;
import com.crypto.trading.system.cryptotradingsystem.repository.CryptoWalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RetrieveCryptoService {

    CryptoWalletRepository cryptoWalletRepository;
    CryptoHistoryRepository cryptoHistoryRepository;
    CryptoDataRepository cryptoDataRepository;
    public RetrieveCryptoService(CryptoWalletRepository cryptoWalletRepository, CryptoHistoryRepository cryptoHistoryRepository,  CryptoDataRepository cryptoDataRepository){
        this.cryptoWalletRepository = cryptoWalletRepository;
        this.cryptoHistoryRepository = cryptoHistoryRepository;
        this.cryptoDataRepository = cryptoDataRepository;

    }

    public CryptoWallet retrieveBalance(String userId){
        Optional<CryptoWallet> cryptoWallet =  cryptoWalletRepository.findByUserId(userId);
        if(cryptoWallet.isEmpty()){
            throw new InvalidDataException();
        }
        return cryptoWallet.get();
    }

    public CryptoHistoryResponse retrieveHistory(String userId){
        List<CryptoHistory> cryptoHistories =  cryptoHistoryRepository.findAllByUserId(userId);
        return new CryptoHistoryResponse("OK", cryptoHistories);
    }

    public CryptoPricesResponse retrievePrices(){
        List<CryptoData> cryptoDataList =  cryptoDataRepository.findAll();
        return new CryptoPricesResponse("OK", cryptoDataList);
    }
}
