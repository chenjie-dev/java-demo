package com.example.websocket.model;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class KlineData {
    private String symbol;
    private long openTime;
    private String open;
    private String high;
    private String low;
    private String close;
    private String volume;
    private long closeTime;
    private String quoteAssetVolume;
    private long numberOfTrades;
    private String takerBuyBaseAssetVolume;
    private String takerBuyQuoteAssetVolume;
    private String ignore;
} 