package com.chenjie.strategy;


/**
 * 支付策略接口
 * 定义所有支持的支付方式的通用接口
 */
public interface PaymentStrategy {
    /**
     * 支付方法
     * @param amount 支付金额
     */
    void pay(int amount);
}