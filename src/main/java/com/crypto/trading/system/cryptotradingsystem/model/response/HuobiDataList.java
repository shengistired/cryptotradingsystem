package com.crypto.trading.system.cryptotradingsystem.model.response;

import lombok.Data;

import java.util.List;

@Data
public class HuobiDataList {
    private List<HuobiData> data;
    private String status;
    private long ts;
}
