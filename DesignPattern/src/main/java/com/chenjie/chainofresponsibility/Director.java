package com.chenjie.chainofresponsibility;



/**
 * 主任审批者
 */
public class Director extends Approver {
    public Director(String name) {
        super(name, 5000); // 主任审批上限5000元
    }

    @Override
    public void processRequest(PurchaseRequest request) {
        if (request.getAmount() <= approvalLimit) {
            System.out.println("主任 " + name + " 审批了采购单 #" + request.getId() +
                    "，金额: " + request.getAmount() + " 元，描述: " + request.getDescription());
        } else if (successor != null) {
            successor.processRequest(request);
        } else {
            System.out.println("采购单 #" + request.getId() + " 需要更高权限审批，金额: " +
                    request.getAmount() + " 元");
        }
    }
}