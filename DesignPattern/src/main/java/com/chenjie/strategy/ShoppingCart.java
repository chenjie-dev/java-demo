package com.chenjie.strategy;

/**
 * 购物车类，作为策略模式的上下文
 */
public class ShoppingCart {
    private PaymentStrategy paymentStrategy;

    /**
     * 设置支付策略
     * @param paymentStrategy 支付策略实现
     */
    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    /**
     * 结账方法
     * @param amount 支付金额
     */
    public void checkout(int amount) {
        if (paymentStrategy == null) {
            System.out.println("请先设置支付方式");
            return;
        }
        paymentStrategy.pay(amount);
    }
}