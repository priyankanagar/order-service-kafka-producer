package com.example.orders;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@Transactional
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private static final String topicName = "orders";

    @Autowired
    private PurchaseOrderRepository orderRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    @Override
    public PurchaseOrder createOrder(PurchaseOrder order) {
        PurchaseOrder newOrder = orderRepository.save(order);
        sendMessage(newOrder);
        return newOrder;
    }

    @Override
    public PurchaseOrder getOrderById(Long orderId) {
        return orderRepository.findById(orderId).get();
    }

    private void sendMessage(PurchaseOrder msg) {
        ListenableFuture<SendResult<String, String>> future =
                kafkaTemplate.send(topicName, Long.toString(msg.getId()), msg.toString());

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("Sent message=[" + msg +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=["
                        + msg + "] due to : " + ex.getMessage());
            }
        });
    }

}
