package com.example.websocket.service;

import com.example.websocket.model.KlineData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Service
public class KlineDataService {
    private final Map<String, CopyOnWriteArrayList<KlineData>> klineDataMap = new ConcurrentHashMap<>();
    private final Map<String, WebSocketSession> clientSessions = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper;

    @Autowired
    public KlineDataService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void handleKlineMessage(String message) {
        try {
            JsonNode root = objectMapper.readTree(message);
            JsonNode kline = root.get("k");
            
            KlineData klineData = KlineData.builder()
                    .symbol(kline.get("s").asText())
                    .openTime(kline.get("t").asLong())
                    .open(kline.get("o").asText())
                    .high(kline.get("h").asText())
                    .low(kline.get("l").asText())
                    .close(kline.get("c").asText())
                    .volume(kline.get("v").asText())
                    .closeTime(kline.get("T").asLong())
                    .quoteAssetVolume(kline.get("q").asText())
                    .numberOfTrades(kline.get("n").asLong())
                    .takerBuyBaseAssetVolume(kline.get("V").asText())
                    .takerBuyQuoteAssetVolume(kline.get("Q").asText())
                    .ignore(kline.get("B").asText())
                    .build();

            log.info("处理K线数据: symbol={}, time={}, close={}", 
                    klineData.getSymbol(), 
                    klineData.getOpenTime(), 
                    klineData.getClose());
            
            klineDataMap.computeIfAbsent(klineData.getSymbol(), k1 -> new CopyOnWriteArrayList<>()).add(klineData);
            
            broadcastKlineData(klineData);
        } catch (Exception e) {
            log.error("处理K线数据失败: {}", message, e);
        }
    }

    public void addClientSession(String clientId, WebSocketSession session) {
        clientSessions.put(clientId, session);
        log.info("添加客户端会话: clientId={}", clientId);
    }

    public void removeClientSession(String clientId) {
        clientSessions.remove(clientId);
        log.info("移除客户端会话: clientId={}", clientId);
    }

    private void broadcastKlineData(KlineData klineData) {
        String message;
        try {
            message = objectMapper.writeValueAsString(klineData);
            TextMessage textMessage = new TextMessage(message);
            
            int sentCount = 0;
            for (WebSocketSession session : clientSessions.values()) {
                if (session.isOpen()) {
                    session.sendMessage(textMessage);
                    sentCount++;
                }
            }
            log.info("广播K线数据: 发送给{}个客户端", sentCount);
        } catch (IOException e) {
            log.error("广播K线数据失败", e);
        }
    }

    public CopyOnWriteArrayList<KlineData> getKlineData(String symbol) {
        return klineDataMap.getOrDefault(symbol, new CopyOnWriteArrayList<>());
    }
} 