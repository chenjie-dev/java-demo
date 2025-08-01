package com.chenjie.chainofresponsibility;

/**
 * 采购请求类
 */
public class PurchaseRequest {
    private int id;
    private String description;
    private double amount;

    public PurchaseRequest(int id, String description, double amount) {
        this.id = id;
        this.description = description;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }
}