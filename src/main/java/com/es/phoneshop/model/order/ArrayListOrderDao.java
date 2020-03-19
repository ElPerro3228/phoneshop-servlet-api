package com.es.phoneshop.model.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ArrayListOrderDao implements OrderDao {

    private List<Order> orderList;
    private static ArrayListOrderDao instance;

    private ArrayListOrderDao(){
        orderList = new ArrayList<>();
    }

    public static ArrayListOrderDao getInstance(){
        if (instance == null) {
            instance = new ArrayListOrderDao();
        }
        return instance;
    }

    @Override
    public synchronized Optional<Order> getOrder(UUID id) {
        return orderList.stream()
                .filter(order -> order.getId().equals(id))
                .findFirst();
    }

    @Override
    public synchronized void save(Order order) {
        if (order.getId() != null) {
            Optional<Order> found = getOrder(order.getId());
            if (found.isPresent()) {
                update(found.get(), order);
            } else {
                addOrder(order);
            }
        }
    }

    private void addOrder(Order order) {
        orderList.add(order);
    }

    private void update(Order found, Order order) {
        found.setName(order.getName());
        found.setSurname(order.getSurname());
        found.setAddress(order.getAddress());
        found.setPhoneNumber(order.getPhoneNumber());
        found.setCart(order.getCart());
        found.setId(order.getId());
        found.setSubtotalPrice(order.getSubtotalPrice());
        found.setDeliveryPrice(order.getDeliveryPrice());
        found.setPayment(order.getPayment());
    }

    @Override
    public synchronized void delete(UUID id) {
        orderList.removeIf(order -> order.getId().equals(id));
    }
}
