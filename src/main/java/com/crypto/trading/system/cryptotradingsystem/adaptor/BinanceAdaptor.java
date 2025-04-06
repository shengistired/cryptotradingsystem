package com.crypto.trading.system.cryptotradingsystem.adaptor;

import com.crypto.trading.system.cryptotradingsystem.model.response.BinanceData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "binance", url = "https://api.binance.com")
public interface BinanceAdaptor {

    @GetMapping(value = "/api/v3/ticker/bookTicker")
    List<BinanceData> retrieveBinanceData();
}
