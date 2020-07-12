package com.example.orders;

import org.springframework.data.repository.CrudRepository;

public interface PurchaseOrderRepository extends CrudRepository<PurchaseOrder, Long> {

    PurchaseOrder findById(long id);
}
