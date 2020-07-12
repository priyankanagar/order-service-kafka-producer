package com.example.orders;

public interface PurchaseOrderService {

    PurchaseOrder createOrder(PurchaseOrder order);

    PurchaseOrder getOrderById(Long orderId);
}
