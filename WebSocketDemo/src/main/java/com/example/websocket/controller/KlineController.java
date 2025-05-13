package com.example.websocket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KlineController {
    
    @GetMapping("/")
    public String index() {
        return "kline";
    }
} 