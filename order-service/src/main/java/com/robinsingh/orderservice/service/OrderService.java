package com.robinsingh.orderservice.service;

import com.robinsingh.inventoryservice.dto.InventoryResponse;
import com.robinsingh.orderservice.dto.OrderLineItemsDto;
import com.robinsingh.orderservice.dto.OrderRequest;
import com.robinsingh.orderservice.model.Order;
import com.robinsingh.orderservice.model.OrderLineItems;
import com.robinsingh.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public void placeOrder(OrderRequest orderRequest){
        Order order = Order.builder()
                .orderLineItemsList(
                        orderRequest.getOrderLineItemsDtoList().stream().map(this::mapToOrderList).toList()
                )
                .orderNumber(UUID.randomUUID().toString())
                .build();

        // insert logic to check whether the product exists in inventory or not
        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        InventoryResponse[] inventoryResponseArray = webClient.get()
                .uri("http://localhost:8082/api/inventory",
                        uriBuilder ->  uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allProductInStock = Arrays.stream(inventoryResponseArray)
                .allMatch(InventoryResponse::isInStock);


        if(allProductInStock)
            orderRepository.save(order);
        else
            throw new IllegalArgumentException("Product is not in inventory");
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
