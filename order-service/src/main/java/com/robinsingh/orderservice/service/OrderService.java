package com.robinsingh.orderservice.service;

import com.robinsingh.orderservice.dto.OrderLineItemsDto;
import com.robinsingh.orderservice.dto.OrderRequest;
import com.robinsingh.orderservice.model.Order;
import com.robinsingh.orderservice.model.OrderLineItems;
import com.robinsingh.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest){
        Order order = Order.builder()
                .orderLineItemsList(
                        orderRequest.getOrderLineItemsDtoList().stream().map(this::mapToOrderList).toList()
                )
                .orderNumber(UUID.randomUUID().toString())
                .build();
        orderRepository.save(order);

    }
    public OrderLineItems mapToOrderList(OrderLineItemsDto orderLineItemsDto){
        return OrderLineItems.builder()
                .skuCode(orderLineItemsDto.getSkuCode())
                .price(orderLineItemsDto.getPrice())
                .quantity(orderLineItemsDto.getQuantity())
                .build();
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }
}
