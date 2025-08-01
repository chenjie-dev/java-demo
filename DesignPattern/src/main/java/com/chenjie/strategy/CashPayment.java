package com.chenjie.strategy;

/**
 * 现金支付策略实现
 */
public class CashPayment implements PaymentStrategy {
    @Override
    public void pay(int amount) {
        System.out.println(amount + "元已通过现金支付");
    }
}