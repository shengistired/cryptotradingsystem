package com.crypto.trading.system.cryptotradingsystem.adaptor;

import com.crypto.trading.system.cryptotradingsystem.model.response.HuobiDataList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "huobi", url = "https://api.huobi.pro")
public interface HuobiAdaptor {

    @GetMapping(value = "/market/tickers")
    HuobiDataList retrieveHuobiData();
}
