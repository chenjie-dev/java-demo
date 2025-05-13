package com.example.websocket.service;

import com.example.websocket.event.KlineSubscriptionEvent;
import com.example.websocket.handler.BinanceWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class BinanceWebSocketConnectionManager {
    private static final String BINANCE_WS_URL = "wss://stream.binance.com:9443/ws";
    private final Map<String, WebSocketConnectionManager> connectionManagers = new ConcurrentHashMap<>();
    private final WebSocketClient webSocketClient;
    private final ScheduledExecutorService scheduler;
    private final ObjectMapper objectMapper;
    private final KlineDataService klineDataService;
    private final BinanceWebSocketHandler binanceWebSocketHandler;

    @Autowired
    public BinanceWebSocketConnectionManager(
            WebSocketClient webSocketClient,
            ObjectMapper objectMapper,
            KlineDataService klineDataService,
            BinanceWebSocketHandler binanceWebSocketHandler) {
        this.webSocketClient = webSocketClient;
        this.objectMapper = objectMapper;
        this.klineDataService = klineDataService;
        this.binanceWebSocketHandler = binanceWebSocketHandler;
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
    }

    @PostConstruct
    public void init() {
        scheduler.scheduleAtFixedRate(this::reconnectIfNeeded, 1, 1, TimeUnit.MINUTES);
    }

    @EventListener
    public void handleKlineSubscriptionEvent(KlineSubscriptionEvent event) {
        String symbol = event.getSymbol();
        String clientId = event.getClientId();
        log.info("收到K线订阅事件: symbol={}, clientId={}", symbol, clientId);
        subscribeKline(symbol);
    }

    public void subscribeKline(String symbol) {
        try {
            String url = String.format("%s/%s@kline_1m", BINANCE_WS_URL, symbol.toLowerCase());
            log.info("准备订阅K线数据: symbol={}, url={}", symbol, url);
            
            // 检查是否已经存在该交易对的连接
            if (connectionManagers.containsKey(symbol)) {
                WebSocketConnectionManager existingManager = connectionManagers.get(symbol);
                if (existingManager.isRunning()) {
                    log.info("交易对{}已经存在活跃的订阅连接", symbol);
                    return;
                } else {
                    log.info("发现交易对{}存在非活跃连接，将重新创建", symbol);
                    existingManager.stop();
                    connectionManagers.remove(symbol);
                }
            }
            
            WebSocketConnectionManager connectionManager = new WebSocketConnectionManager(
                    webSocketClient,
                    binanceWebSocketHandler,
                    url
            );
            
            log.info("创建新的WebSocket连接管理器: symbol={}", symbol);
            connectionManager.start();
            connectionManagers.put(symbol, connectionManager);
            log.info("成功订阅K线数据: symbol={}", symbol);
        } catch (Exception e) {
            log.error("订阅K线数据失败: symbol={}", symbol, e);
            throw e;
        }
    }

    public void unsubscribeKline(String symbol) {
        WebSocketConnectionManager connectionManager = connectionManagers.remove(symbol);
        if (connectionManager != null) {
            connectionManager.stop();
            log.info("已取消订阅K线数据: {}", symbol);
        }
    }

    private void reconnectIfNeeded() {
        connectionManagers.forEach((symbol, manager) -> {
            if (!manager.isRunning()) {
                log.info("检测到连接断开，尝试重新连接: {}", symbol);
                manager.stop();
                subscribeKline(symbol);
            }
        });
    }

    @PreDestroy
    public void destroy() {
        connectionManagers.values().forEach(WebSocketConnectionManager::stop);
        scheduler.shutdown();
    }
} 