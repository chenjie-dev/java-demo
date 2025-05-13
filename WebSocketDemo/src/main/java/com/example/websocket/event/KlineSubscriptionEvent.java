package com.example.websocket.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class KlineSubscriptionEvent extends ApplicationEvent {
    private final String symbol;
    private final String clientId;

    public KlineSubscriptionEvent(Object source, String symbol, String clientId) {
        super(source);
        this.symbol = symbol;
        this.clientId = clientId;
    }
} 