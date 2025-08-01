package com.chenjie.chainofresponsibility;



/**
 * 责任链模式演示类
 */
public class ChainOfResponsibilityDemo {
    public static void main(String[] args) {
        // 构建审批链
        Approver approverChain = ApproverChainBuilder.buildApproverChain();

        // 创建采购请求
        PurchaseRequest request1 = new PurchaseRequest(1, "办公用品", 3000);
        PurchaseRequest request2 = new PurchaseRequest(2, "服务器设备", 8000);
        PurchaseRequest request3 = new PurchaseRequest(3, "公司车辆", 15000);
        PurchaseRequest request4 = new PurchaseRequest(4, "海外投资", 500000);

        // 处理请求
        System.out.println("=== 处理采购请求 ===");
        approverChain.processRequest(request1);
        approverChain.processRequest(request2);
        approverChain.processRequest(request3);
        approverChain.processRequest(request4);
    }
}