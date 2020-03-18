package com.es.phoneshop.model.order;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ArrayListOrderDaoTest {
    @Spy
    private OrderDao orderDao = ArrayListOrderDao.getInstance();
    @Captor
    private ArgumentCaptor<Order> orderArgumentCaptor;

    @Before
    public void setup() {
        doCallRealMethod().when(orderDao).save(orderArgumentCaptor.capture());
        generateTestData();
    }

    @Test
    public void shouldReturnExistingOrderWhenUUIDisCorrect() {
        Optional<Order> optionalOrder = orderDao.getOrder(orderArgumentCaptor.getAllValues().get(0).getId());
        assertTrue(optionalOrder.isPresent());
    }

    @Test
    public void shouldNotReturnOrderWhenUUIDisWrong() {
        Optional<Order> optionalOrder = orderDao.getOrder(UUID.randomUUID());
        assertFalse(optionalOrder.isPresent());
    }

    @Test
    public void shouldUpdateOrderWhenOrderIsAlreadyInList() {
        Order order = new Order();
        order.setId(orderArgumentCaptor.getValue().getId());
        order.setName("Stas");
        orderDao.save(order);
        assertEquals(order.getName(), orderDao.getOrder(orderArgumentCaptor.getValue().getId()).get().getName());
    }

    @Test
    public void shouldRemoveOrderWhenUUIDExist() {
        orderDao.delete(orderArgumentCaptor.getValue().getId());
        assertFalse(orderDao.getOrder(orderArgumentCaptor.getValue().getId()).isPresent());
    }

    @Test
    public void shouldNotRemoveOrderWhenUUIDisWrong() {
        orderDao.delete(UUID.randomUUID());
        for (Order order : orderArgumentCaptor.getAllValues()) {
            assertTrue(orderDao.getOrder(order.getId()).isPresent());
        }
    }


    private void generateTestData() {
        Order order = new Order();
        order.setId(UUID.randomUUID());
        Order order2 = new Order();
        order2.setId(UUID.randomUUID());
        orderDao.save(order);
        orderDao.save(order2);
    }
}
