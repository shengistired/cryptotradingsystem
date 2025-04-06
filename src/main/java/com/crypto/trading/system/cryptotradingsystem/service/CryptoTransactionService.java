package com.crypto.trading.system.cryptotradingsystem.service;

import com.crypto.trading.system.cryptotradingsystem.exception.InvalidDataException;
import com.crypto.trading.system.cryptotradingsystem.exception.PriceMismatchException;
import com.crypto.trading.system.cryptotradingsystem.model.entity.CryptoData;
import com.crypto.trading.system.cryptotradingsystem.model.entity.CryptoHistory;
import com.crypto.trading.system.cryptotradingsystem.model.entity.CryptoItem;
import com.crypto.trading.system.cryptotradingsystem.model.entity.CryptoWallet;
import com.crypto.trading.system.cryptotradingsystem.model.request.TradeCryptoRequest;
import com.crypto.trading.system.cryptotradingsystem.model.response.SuccessResponse;
import com.crypto.trading.system.cryptotradingsystem.repository.CryptoDataRepository;
import com.crypto.trading.system.cryptotradingsystem.repository.CryptoHistoryRepository;
import com.crypto.trading.system.cryptotradingsystem.repository.CryptoWalletRepository;
import com.crypto.trading.system.cryptotradingsystem.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class CryptoTransactionService {

    CryptoDataRepository cryptoDataRepository;
    CryptoWalletRepository cryptoWalletRepository;
    CryptoHistoryRepository cryptoHistoryRepository;


    public CryptoTransactionService(CryptoDataRepository cryptoDataRepository,
                                    CryptoWalletRepository cryptoWalletRepository,
                                    CryptoHistoryRepository cryptoHistoryRepository){
        this.cryptoDataRepository = cryptoDataRepository;
        this.cryptoWalletRepository = cryptoWalletRepository;
        this.cryptoHistoryRepository = cryptoHistoryRepository;

    }
    public SuccessResponse tradeCrypto(TradeCryptoRequest tradeCryptoRequest, String userId){
        Optional<CryptoWallet> crptoWalletOptional = cryptoWalletRepository.findByUserId(userId);
        if(crptoWalletOptional.isEmpty()){
            log.error("Crypto wallet not found for: {} ", userId);
            throw new InvalidDataException();
        }
        CryptoWallet cryptoWallet = crptoWalletOptional.get();
        if(tradeCryptoRequest.getType().equalsIgnoreCase("BUY")){
            log.info("Start buy: {}", userId);
            setUpBuyCrypto(tradeCryptoRequest, cryptoWallet);
        }
        else if(tradeCryptoRequest.getType().equalsIgnoreCase("SELL")){
            log.info("Start sell: {}", userId);
            setUpSellCrypto(tradeCryptoRequest, cryptoWallet);
        }
        else{
            log.info("Invalid type");
            throw new InvalidDataException();
        }
        cryptoWalletRepository.save(cryptoWallet);
        return new SuccessResponse("OK");
    }

    private void setUpBuyCrypto(TradeCryptoRequest tradeCryptoRequest, CryptoWallet cryptoWallet){
        String symbol = tradeCryptoRequest.getSymbol().toUpperCase();
        Optional<CryptoData> bestBuyPriceOptional = cryptoDataRepository.findTopBySymbolOrderByAskPriceAsc(symbol);
        if(bestBuyPriceOptional.isEmpty()){
            log.error("symbol not found");
            throw new InvalidDataException();
        }
        CryptoData bestBuyPrice = bestBuyPriceOptional.get();
        double quantityAvailableToBuy;
        if(tradeCryptoRequest.getQty() > bestBuyPrice.getAskQty()){
            quantityAvailableToBuy = bestBuyPrice.getAskQty();
        }
        else{
            quantityAvailableToBuy = tradeCryptoRequest.getQty();
        }
        Double usdtToPay = quantityAvailableToBuy * bestBuyPrice.getAskPrice();
        Double ownedUsdt = cryptoWallet.getUsdt();
        if(ownedUsdt < usdtToPay){
            log.error("Not enough USDT to pay");
            throw new PriceMismatchException("USDT");
        }
        cryptoWallet.setUsdt(ownedUsdt - usdtToPay);
        if(cryptoWallet.getCryptoItems().isEmpty()){
            List<CryptoItem> cryptoItems = new ArrayList<>();
            CryptoItem cryptoItem = new CryptoItem();
            cryptoItem.setSymbol(symbol);
            cryptoItem.setQty(quantityAvailableToBuy);
            cryptoItem.setValue(usdtToPay);
            cryptoItem.setCryptoWallet(cryptoWallet);
            cryptoItems.add(cryptoItem);
            cryptoWallet.setCryptoItems(cryptoItems);
            return;
        }
        cryptoWallet.getCryptoItems().stream().filter(
                f -> f.getSymbol().equalsIgnoreCase(symbol)
        ).findAny().ifPresentOrElse(
                p -> {
                    p.setQty(p.getQty() + quantityAvailableToBuy);
                    p.setValue(p.getValue() + usdtToPay);
                },
                () -> {
                    CryptoItem newItem = new CryptoItem();
                    newItem.setSymbol(symbol);
                    newItem.setQty(quantityAvailableToBuy);
                    newItem.setValue(usdtToPay);
                    newItem.setCryptoWallet(cryptoWallet);
                    cryptoWallet.getCryptoItems().add(newItem);
                }
        );
        setUpTransactionHistory(cryptoWallet, bestBuyPrice, quantityAvailableToBuy, ownedUsdt, true);

    }

    private void setUpSellCrypto(TradeCryptoRequest tradeCryptoRequest, CryptoWallet cryptoWallet){
        String symbol = tradeCryptoRequest.getSymbol().toUpperCase();
        Optional<CryptoData> bestSellPriceOptional = cryptoDataRepository.findTopBySymbolOrderByBidPriceDesc(symbol);
        if(bestSellPriceOptional.isEmpty()){
            throw new InvalidDataException();
        }
        CryptoData bestSellPrice = bestSellPriceOptional.get();
        double quantityToSell;
        if(tradeCryptoRequest.getQty() > bestSellPrice.getBidQty()){
            quantityToSell = bestSellPrice.getBidQty();
        }
        else{
            quantityToSell = tradeCryptoRequest.getQty();
        }
        Double usdtToGain = quantityToSell * bestSellPrice.getBidPrice();
        cryptoWallet.getCryptoItems().stream().filter(
                f -> f.getSymbol().equalsIgnoreCase(symbol)
        ).findAny().ifPresentOrElse(
                p -> {
                    if(quantityToSell > p.getQty()){
                        throw new InvalidDataException();
                    }
                    p.setQty(p.getQty() - quantityToSell);
                    if(p.getValue() < usdtToGain){
                        log.error("Not enough {} to pay", symbol);
                        throw new PriceMismatchException(symbol);
                    }
                    p.setValue(p.getValue() - usdtToGain);
                },
                () -> {
                    throw new InvalidDataException();
                }
        );
        cryptoWallet.setUsdt(cryptoWallet.getUsdt() + usdtToGain);
        setUpTransactionHistory(cryptoWallet, bestSellPrice, quantityToSell, usdtToGain, false);
    }

    private void setUpTransactionHistory(CryptoWallet cryptoWallet, CryptoData cryptoData, Double qty, Double price, boolean isAsk){
        CryptoHistory cryptoHistory = new CryptoHistory();
        String id = UUID.randomUUID().toString();
        cryptoHistory.setId(id);
        cryptoHistory.setSymbol(cryptoData.getSymbol());
        cryptoHistory.setSource(cryptoData.getSource());
        if(isAsk){
            cryptoHistory.setAskPrice(price);
            cryptoHistory.setAskQty(qty);
            cryptoHistory.setBidPrice(cryptoData.getBidPrice());
            cryptoHistory.setBidQty(cryptoData.getBidQty());
        }
        else{
            cryptoHistory.setAskPrice(cryptoData.getAskPrice());
            cryptoHistory.setAskQty(cryptoData.getAskQty());
            cryptoHistory.setBidPrice(price);
            cryptoHistory.setBidQty(qty);
        }
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        cryptoHistory.setUpdateTimestamp(timestamp);
        cryptoHistory.setUserId(cryptoWallet.getUserId());
        cryptoWallet.setLatestTransaction(id);
        cryptoWallet.setLatestPurchaseTimestamp(timestamp);

        cryptoHistoryRepository.save(cryptoHistory);
    }
}
