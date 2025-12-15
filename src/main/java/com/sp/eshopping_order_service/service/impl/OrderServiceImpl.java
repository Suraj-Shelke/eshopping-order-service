package com.sp.eshopping_order_service.service.impl;

import com.sp.eshopping_order_service.feign.PaymentService;
import com.sp.eshopping_order_service.feign.ProductService;
import com.sp.eshopping_order_service.model.Order;
import com.sp.eshopping_order_service.payload.request.OrderRequest;
import com.sp.eshopping_order_service.payload.request.PaymentRequest;
import com.sp.eshopping_order_service.payload.response.OrderResponse;
import com.sp.eshopping_order_service.repository.OrderRepository;
import com.sp.eshopping_order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final RestTemplate restTemplate;

    private final ProductService productService;

    private final PaymentService paymentService;

    @Override
    public long placeOrder(OrderRequest orderRequest) {


        log.info("OrderServiceImpl | placeOrder is called");

        //Order Entity -> Save the data with Status Order Created
        //Product Service - Block Products (Reduce the Quantity)
        //Payment Service -> Payments -> Success-> COMPLETE, Else
        //CANCELLED

        log.info("OrderServiceImpl | placeOrder | Placing Order Request orderRequest : " + orderRequest.toString());

        log.info("OrderServiceImpl | placeOrder | Calling productService through FeignClient");

        productService.reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity());

        log.info("OrderServiceImpl | placeOrder | Creating Order with Status CREATED");

        Order order=new Order();
        order.setProductId(orderRequest.getProductId());
        order.setOrderStatus("CREATED");
        order.setAmount(orderRequest.getTotalAmount());
        order.setOrderDate(Instant.now());
        order.setQuantity(orderRequest.getQuantity());

        order = orderRepository.save(order);

        log.info("OrderServiceImpl | placeOrder | Calling Payment Service to complete the payment");

        PaymentRequest paymentRequest
                = PaymentRequest.builder()
                .orderId(order.getId())
                .paymentMode(orderRequest.getPaymentMode())
                .amount(orderRequest.getTotalAmount())
                .build();

        String orderStatus = null;

        try {
            paymentService.doPayment(paymentRequest);
            log.info("OrderServiceImpl | placeOrder | Payment done Successfully. Changing the Oder status to PLACED");
            orderStatus = "PLACED";
        } catch (Exception e) {
            log.error("OrderServiceImpl | placeOrder | Error occurred in payment. Changing order status to PAYMENT_FAILED");
            orderStatus = "PAYMENT_FAILED";
        }

        order.setOrderStatus(orderStatus);

        orderRepository.save(order);

        log.info("OrderServiceImpl | placeOrder | Order Places successfully with Order Id: {}", order.getId());

        return order.getId();
    }

    @Override
    public OrderResponse getOrderDetails(long orderId) {
        return null;
    }
}
