package com.example.orders;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository orderRepository;


    @Override
    public PurchaseOrder createOrder(PurchaseOrder order) {
        return orderRepository.save(order);
    }

    @Override
    public PurchaseOrder getOrderById(Long orderId) {
        return orderRepository.findById(orderId).get();
    }


}
