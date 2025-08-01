package com.chenjie.strategy;

/**
 * PayPal支付策略实现
 */
public class PayPalPayment implements PaymentStrategy {
    private String email;
    private String password;

    /**
     * 构造函数
     * @param email PayPal邮箱
     * @param password PayPal密码
     */
    public PayPalPayment(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public void pay(int amount) {
        System.out.println(amount + "元已通过PayPal支付(邮箱:" + email + ")");
    }
}