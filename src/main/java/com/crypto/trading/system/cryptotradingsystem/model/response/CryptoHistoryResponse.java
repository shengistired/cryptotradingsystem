package com.crypto.trading.system.cryptotradingsystem.model.response;

import com.crypto.trading.system.cryptotradingsystem.model.entity.CryptoHistory;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CryptoHistoryResponse {
    private String status;
    private List<CryptoHistory> data;
}
