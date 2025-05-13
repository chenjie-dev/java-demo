package com.example.websocket.handler;

import com.example.websocket.event.KlineSubscriptionEvent;
import com.example.websocket.service.KlineDataService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.UUID;

@Slf4j
@Component
public class BinanceWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final KlineDataService klineDataService;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public BinanceWebSocketHandler(
            ObjectMapper objectMapper,
            KlineDataService klineDataService,
            ApplicationEventPublisher eventPublisher) {
        this.objectMapper = objectMapper;
        this.klineDataService = klineDataService;
        this.eventPublisher = eventPublisher;
        log.info("BinanceWebSocketHandler已初始化");
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String clientId = UUID.randomUUID().toString();
        session.getAttributes().put("clientId", clientId);
        
        // 检查是否是币安服务器的连接
        if (session.getUri() == null) {
            log.info("币安WebSocket服务器连接已建立: clientId={}", clientId);
            return;
        }
        
        // 处理客户端连接
        try {
            String path = session.getUri().getPath();
            String symbol = path.substring(path.lastIndexOf('/') + 1).toLowerCase();
            log.info("WebSocket客户端连接已建立: clientId={}, symbol={}", clientId, symbol);
            
            klineDataService.addClientSession(clientId, session);
            
            // 发布订阅事件
            eventPublisher.publishEvent(new KlineSubscriptionEvent(this, symbol, clientId));
            log.info("已发布K线订阅事件: symbol={}, clientId={}", symbol, clientId);
        } catch (Exception e) {
            log.error("处理WebSocket连接失败: clientId={}", clientId, e);
            session.close(CloseStatus.SERVER_ERROR);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.debug("收到WebSocket消息: {}", payload);
        
        // 处理PING消息
        if (payload.contains("\"ping\"")) {
            String pongMessage = payload.replace("ping", "pong");
            session.sendMessage(new TextMessage(pongMessage));
            log.debug("已发送PONG消息");
            return;
        }
        
        // 处理K线数据
        if (payload.contains("\"kline\"")) {
            klineDataService.handleKlineMessage(payload);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String clientId = (String) session.getAttributes().get("clientId");
        if (clientId == null) {
            log.warn("收到未知客户端的连接关闭事件");
            return;
        }

        String symbol = null;
        try {
            if (session.getUri() != null) {
                String path = session.getUri().getPath();
                symbol = path.substring(path.lastIndexOf('/') + 1).toLowerCase();
            }
        } catch (Exception e) {
            log.warn("获取交易对信息失败: clientId={}", clientId, e);
        }
        
        klineDataService.removeClientSession(clientId);
        log.info("WebSocket客户端连接已关闭: clientId={}, symbol={}, status={}", 
                clientId, 
                symbol != null ? symbol : "unknown", 
                status);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        String clientId = (String) session.getAttributes().get("clientId");
        if (clientId == null) {
            log.warn("收到未知客户端的传输错误事件", exception);
            return;
        }

        String symbol = null;
        try {
            if (session.getUri() != null) {
                String path = session.getUri().getPath();
                symbol = path.substring(path.lastIndexOf('/') + 1).toLowerCase();
            }
        } catch (Exception e) {
            log.warn("获取交易对信息失败: clientId={}", clientId, e);
        }
        
        klineDataService.removeClientSession(clientId);
        log.error("WebSocket传输错误: clientId={}, symbol={}", 
                clientId, 
                symbol != null ? symbol : "unknown", 
                exception);
    }
} 