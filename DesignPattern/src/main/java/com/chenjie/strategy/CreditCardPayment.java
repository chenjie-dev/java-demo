package com.chenjie.strategy;

/**
 * 信用卡支付策略实现
 */
public class CreditCardPayment implements PaymentStrategy {
    private String name;
    private String cardNumber;
    private String cvv;
    private String expiryDate;

    /**
     * 构造函数
     * @param name 持卡人姓名
     * @param cardNumber 卡号
     * @param cvv 安全码
     * @param expiryDate 过期日期
     */
    public CreditCardPayment(String name, String cardNumber, String cvv, String expiryDate) {
        this.name = name;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
    }

    @Override
    public void pay(int amount) {
        System.out.println(amount + "元已通过信用卡支付(卡号:" + maskCardNumber(cardNumber) + ")");
    }

    /**
     * 隐藏部分卡号信息
     * @param cardNumber 原始卡号
     * @return 隐藏后的卡号
     */
    private String maskCardNumber(String cardNumber) {
        if (cardNumber.length() > 4) {
            return "****-****-****-" + cardNumber.substring(cardNumber.length() - 4);
        }
        return cardNumber;
    }
}