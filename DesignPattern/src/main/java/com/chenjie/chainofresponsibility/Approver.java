package com.chenjie.chainofresponsibility;

/**
 * 审批者抽象类
 */
public abstract class Approver {
    protected Approver successor; // 后继处理者
    protected String name; // 审批者姓名
    protected double approvalLimit; // 审批金额上限

    public Approver(String name, double approvalLimit) {
        this.name = name;
        this.approvalLimit = approvalLimit;
    }

    /**
     * 设置后继处理者
     */
    public void setSuccessor(Approver successor) {
        this.successor = successor;
    }

    /**
     * 处理请求的方法
     */
    public abstract void processRequest(PurchaseRequest request);
}