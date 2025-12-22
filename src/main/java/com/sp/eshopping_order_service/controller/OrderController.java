package com.sp.eshopping_order_service.controller;

import com.sp.eshopping_order_service.payload.request.OrderRequest;
import com.sp.eshopping_order_service.payload.response.OrderResponse;
import com.sp.eshopping_order_service.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {


    private final OrderService orderService;
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/placeorder")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest) {
        log.info("OrderController | placeOrder is called");
        log.info("OrderController | placeOrder | orderRequest: {}", orderRequest.toString());
        long orderId = orderService.placeOrder(orderRequest);
        log.info("Order Id: {}", orderId);
        return new ResponseEntity<>(orderId,HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable long orderId) {
        log.info("OrderController | getOrderDetails is called");
        OrderResponse orderResponse
                = orderService.getOrderDetails(orderId);
        log.info("OrderController | getOrderDetails | orderResponse : " + orderResponse.toString());
        return new ResponseEntity<>(orderResponse,
                HttpStatus.OK);
    }
}
