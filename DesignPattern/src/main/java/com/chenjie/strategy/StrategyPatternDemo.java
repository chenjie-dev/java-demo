package com.chenjie.strategy;

/**
 * 策略模式演示类
 */
public class StrategyPatternDemo {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();

        // 使用信用卡支付
        System.out.println("=== 使用信用卡支付 ===");
        cart.setPaymentStrategy(new CreditCardPayment("张三", "1234567890123456", "123", "12/25"));
        cart.checkout(100);

        // 使用PayPal支付
        System.out.println("\n=== 使用PayPal支付 ===");
        cart.setPaymentStrategy(new PayPalPayment("zhangsan@example.com", "mypassword"));
        cart.checkout(50);

        // 使用现金支付
        System.out.println("\n=== 使用现金支付 ===");
        cart.setPaymentStrategy(new CashPayment());
        cart.checkout(25);

        // 测试未设置支付策略的情况
        System.out.println("\n=== 未设置支付策略 ===");
        cart.setPaymentStrategy(null);
        cart.checkout(10);
    }
}