package com.chenjie.chainofresponsibility;



/**
 * 总裁审批者
 */
public class President extends Approver {
    public President(String name) {
        super(name, Double.MAX_VALUE); // 总裁可以审批任何金额
    }

    @Override
    public void processRequest(PurchaseRequest request) {
        System.out.println("总裁 " + name + " 审批了采购单 #" + request.getId() +
                "，金额: " + request.getAmount() + " 元，描述: " + request.getDescription());
    }
}