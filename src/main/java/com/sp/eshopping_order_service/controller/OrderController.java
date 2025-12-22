package com.sp.eshopping_order_service.controller;

import com.sp.eshopping_order_service.payload.request.OrderRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
    @PostMapping("/placeorder")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest) {

        return new ResponseEntity<>(1l,HttpStatus.OK);
    }
}
