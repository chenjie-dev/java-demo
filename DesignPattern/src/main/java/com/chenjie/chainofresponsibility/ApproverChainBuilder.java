package com.chenjie.chainofresponsibility;



/**
 * 审批链构建器
 */
public class ApproverChainBuilder {
    public static Approver buildApproverChain() {
        Approver director = new Director("张主任");
        Approver vicePresident = new VicePresident("李副总裁");
        Approver president = new President("王总裁");

        director.setSuccessor(vicePresident);
        vicePresident.setSuccessor(president);

        return director; // 返回链的起点
    }
}