package com.sp.eshopping_order_service.service;

import com.sp.eshopping_order_service.payload.request.OrderRequest;
import com.sp.eshopping_order_service.payload.response.OrderResponse;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(long orderId);
}
