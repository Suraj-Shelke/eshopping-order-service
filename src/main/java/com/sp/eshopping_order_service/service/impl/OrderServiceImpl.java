package com.sp.eshopping_order_service.service.impl;

import com.sp.eshopping_order_service.feign.PaymentService;
import com.sp.eshopping_order_service.feign.ProductService;
import com.sp.eshopping_order_service.payload.request.OrderRequest;
import com.sp.eshopping_order_service.payload.response.OrderResponse;
import com.sp.eshopping_order_service.repository.OrderRepository;
import com.sp.eshopping_order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final RestTemplate restTemplate;

    private final ProductService productService;

    private final PaymentService paymentService;

    @Override
    public long placeOrder(OrderRequest orderRequest) {
        return 0;
    }

    @Override
    public OrderResponse getOrderDetails(long orderId) {
        return null;
    }
}
