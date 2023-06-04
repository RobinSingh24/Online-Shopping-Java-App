package com.robinsingh.orderservice.controller;

import com.robinsingh.orderservice.dto.OrderRequest;
import com.robinsingh.orderservice.model.Order;
import com.robinsingh.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {


    private final OrderService orderService;

    @PostMapping
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        orderService.placeOrder(orderRequest);
        return "Order placed successfully";
    }

    @GetMapping
    public List<Order> getOrders(){
        return orderService.getOrders();
    }
}
