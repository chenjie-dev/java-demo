package com.chenjie.chainofresponsibility;



/**
 * 副总裁审批者
 */
public class VicePresident extends Approver {
    public VicePresident(String name) {
        super(name, 10000); // 副总裁审批上限10000元
    }

    @Override
    public void processRequest(PurchaseRequest request) {
        if (request.getAmount() <= approvalLimit) {
            System.out.println("副总裁 " + name + " 审批了采购单 #" + request.getId() +
                    "，金额: " + request.getAmount() + " 元，描述: " + request.getDescription());
        } else if (successor != null) {
            successor.processRequest(request);
        } else {
            System.out.println("采购单 #" + request.getId() + " 需要更高权限审批，金额: " +
                    request.getAmount() + " 元");
        }
    }
}