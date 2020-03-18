package com.es.phoneshop.model.order;

import java.util.Optional;
import java.util.UUID;

public interface OrderDao {
    Optional<Order> getOrder(UUID id);
    void save(Order order);
    void delete(UUID id);
}
