package com.readingisgood.getirhomeassignment.services;

import com.readingisgood.getirhomeassignment.enities.Order;
import com.readingisgood.getirhomeassignment.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public Order saveOrder(Order order) {


        return orderRepository.save(order);
    }

    public Order findOrderById(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);

        return order.orElse(null);
    }

    public Set<Order> findOrdersBetweenDates() {
        return Set.of();
    }


}
