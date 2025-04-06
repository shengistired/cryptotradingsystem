package com.crypto.trading.system.cryptotradingsystem.service;

import com.crypto.trading.system.cryptotradingsystem.adaptor.BinanceAdaptor;
import com.crypto.trading.system.cryptotradingsystem.adaptor.HuobiAdaptor;
import com.crypto.trading.system.cryptotradingsystem.exception.InvalidDataException;
import com.crypto.trading.system.cryptotradingsystem.model.entity.CryptoData;
import com.crypto.trading.system.cryptotradingsystem.model.response.BinanceData;
import com.crypto.trading.system.cryptotradingsystem.model.response.HuobiData;
import com.crypto.trading.system.cryptotradingsystem.model.response.HuobiDataList;
import com.crypto.trading.system.cryptotradingsystem.repository.CryptoDataRepository;
import com.crypto.trading.system.cryptotradingsystem.util.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduledService {

    BinanceAdaptor binanceAdaptor;

    HuobiAdaptor huobiAdaptor;

    CryptoDataRepository cryptoDataRepository;

    public ScheduledService(BinanceAdaptor binanceAdaptor, HuobiAdaptor huobiAdaptor, CryptoDataRepository cryptoDataRepository){
        this.binanceAdaptor = binanceAdaptor;
        this.huobiAdaptor = huobiAdaptor;
        this.cryptoDataRepository = cryptoDataRepository;
    }

    //TASK 1
    @Scheduled(fixedRate = 10000)
    public void scheduleFixedRateTask() {
        HuobiDataList huobiDataList = huobiAdaptor.retrieveHuobiData();
        List<HuobiData> huobiDatas = huobiDataList.getData();
        Optional<HuobiData> highestBidETHPriceHuobi = huobiDatas.stream()
                .filter(data -> data.getBid() != null  && data.getBid() != 0 && data.getSymbol().equalsIgnoreCase(Constants.ETHUSDT))
                .max(Comparator.comparingDouble(HuobiData::getBid));

        Optional<HuobiData> lowestAskETHDataHuobi = huobiDatas.stream()
                .filter(data -> data.getAsk() != null && data.getAsk() != 0 && data.getSymbol().equalsIgnoreCase(Constants.ETHUSDT))
                .min(Comparator.comparingDouble(HuobiData::getAsk));

        Optional<HuobiData> highestBidBTCPriceHuobi = huobiDatas.stream()
                .filter(data -> data.getBid() != null && data.getBid() != 0 && data.getSymbol().equalsIgnoreCase(Constants.BTCUSDT))
                .max(Comparator.comparingDouble(HuobiData::getBid));

        Optional<HuobiData> lowestAskBTCDataHuobi = huobiDatas.stream()
                .filter(data -> data.getAsk() != null && data.getAsk() != 0 && data.getSymbol().equalsIgnoreCase(Constants.BTCUSDT))
                .min(Comparator.comparingDouble(HuobiData::getAsk));
        if(highestBidETHPriceHuobi.isEmpty() || lowestAskETHDataHuobi.isEmpty() || highestBidBTCPriceHuobi.isEmpty() || lowestAskBTCDataHuobi.isEmpty()){
            throw new InvalidDataException();
        }
        List<BinanceData> binanceDataList = binanceAdaptor.retrieveBinanceData();
        Optional<BinanceData> highestBidETHPriceBinance = binanceDataList.stream()
                .filter(data -> data.getBidPrice() != null && !data.getBidPrice().isEmpty() && Double.parseDouble(data.getBidPrice()) != 0 && data.getSymbol().equalsIgnoreCase(Constants.ETHUSDT))
                .max(Comparator.comparingDouble(data -> Double.parseDouble(data.getBidPrice())));

        Optional<BinanceData> lowestAskETHDataBinance = binanceDataList.stream()
                .filter(data -> data.getAskPrice() != null && !data.getAskPrice().isEmpty()  && Double.parseDouble(data.getAskPrice()) != 0 && data.getSymbol().equalsIgnoreCase(Constants.ETHUSDT))
                .min(Comparator.comparingDouble(data -> Double.parseDouble(data.getAskPrice())));

        Optional<BinanceData> highestBidBTCPriceBinance = binanceDataList.stream()
                .filter(data -> data.getBidPrice() != null && !data.getBidPrice().isEmpty() && Double.parseDouble(data.getBidPrice()) != 0 && data.getSymbol().equalsIgnoreCase(Constants.BTCUSDT))
                .max(Comparator.comparingDouble(data -> Double.parseDouble(data.getBidPrice())));

        Optional<BinanceData> lowestAskBTCDataBinance = binanceDataList.stream()
                .filter(data -> data.getAskPrice() != null && !data.getAskPrice().isEmpty()  && Double.parseDouble(data.getAskPrice()) != 0 && data.getSymbol().equalsIgnoreCase(Constants.BTCUSDT))
                .min(Comparator.comparingDouble(data -> Double.parseDouble(data.getAskPrice())));
        if(highestBidETHPriceBinance.isEmpty() || lowestAskETHDataBinance.isEmpty() || highestBidBTCPriceBinance.isEmpty() || lowestAskBTCDataBinance.isEmpty()){
            throw new InvalidDataException();
        }
        List<CryptoData> cryptoDataList = new ArrayList<>();
        cryptoDataList.add(compareSource(highestBidETHPriceHuobi.get(), highestBidBTCPriceBinance.get(), false));
        cryptoDataList.add(compareSource(lowestAskETHDataHuobi.get(), lowestAskETHDataBinance.get(), true));
        cryptoDataList.add(compareSource(highestBidBTCPriceHuobi.get(), highestBidBTCPriceBinance.get(), false));
        cryptoDataList.add(compareSource(lowestAskBTCDataHuobi.get(), lowestAskBTCDataBinance.get(), true));
        cryptoDataRepository.deleteAll();
        cryptoDataRepository.saveAll(cryptoDataList);
    }

    private CryptoData compareSource(HuobiData huobiData, BinanceData binanceData, boolean isAsk){
        //sell
        if(isAsk){
            if(huobiData.getAsk() > Double.parseDouble(binanceData.getAskPrice())){
                return new CryptoData(binanceData);
            }
            return new CryptoData(huobiData);
        }
        if(huobiData.getBid() > Double.parseDouble(binanceData.getBidPrice())){
            return new CryptoData(huobiData);
        }
        return new CryptoData(binanceData);
    }

    private List<CryptoData> binanceFiltering(){

        return null;
    }


}
