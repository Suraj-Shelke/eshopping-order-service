package com.sp.eshopping_order_service.service.impl;

import com.sp.eshopping_order_service.exception.OrderServiceCustomException;
import com.sp.eshopping_order_service.feign.PaymentService;
import com.sp.eshopping_order_service.feign.ProductService;
import com.sp.eshopping_order_service.model.Order;
import com.sp.eshopping_order_service.model.PaymentDetails;
import com.sp.eshopping_order_service.model.ProductDetails;
import com.sp.eshopping_order_service.payload.request.OrderRequest;
import com.sp.eshopping_order_service.payload.request.PaymentRequest;
import com.sp.eshopping_order_service.payload.response.OrderResponse;
import com.sp.eshopping_order_service.payload.response.PaymentResponse;
import com.sp.eshopping_order_service.payload.response.ProductResponse;
import com.sp.eshopping_order_service.repository.OrderRepository;
import com.sp.eshopping_order_service.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger log= LoggerFactory.getLogger(OrderServiceImpl.class);
    private final OrderRepository orderRepository;

    private final RestTemplate restTemplate;

    private final ProductService productService;

    private final PaymentService paymentService;
    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, RestTemplate restTemplate, ProductService productService, PaymentService paymentService) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
        this.productService = productService;
        this.paymentService = paymentService;
    }

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

        PaymentRequest paymentRequest =new PaymentRequest();
        paymentRequest.setOrderId(order.getId());
        paymentRequest.setPaymentMode(orderRequest.getPaymentMode());
        paymentRequest.setAmount(orderRequest.getTotalAmount());

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

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //headers.set("Authorization", bearerToken);

        HttpEntity<String> request = new HttpEntity<String>(headers);

        log.info("OrderServiceImpl | getOrderDetails | Get order details for Order Id : {}", orderId);

        Order order
                = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderServiceCustomException("Order not found for the order Id:" + orderId,
                        "NOT_FOUND",
                        404));

        log.info("OrderServiceImpl | getOrderDetails | Invoking Product service to fetch the product for id: {}", order.getProductId());
        ProductResponse productResponse
                = restTemplate.getForObject(
                "http://product-service/product/" + order.getProductId(),
                ProductResponse.class
        );

//        ResponseEntity<ProductResponse> responseProduct = restTemplate.exchange(
//                "http://product-service/product/" + order.getProductId(),
//                HttpMethod.GET, request, ProductResponse.class);
//        ProductResponse productResponse = responseProduct.getBody();

        log.info("OrderServiceImpl | getOrderDetails | Getting payment information form the payment Service");
        PaymentResponse paymentResponse
                = restTemplate.getForObject(
                "http://payment-service/payment/order/" + order.getId(),
                PaymentResponse.class
        );

//        ResponseEntity<PaymentResponse> responsePayment = restTemplate.exchange(
//                "http://payment-service/payment/order/" + order.getId(),
//                HttpMethod.GET, request, PaymentResponse.class);
//        PaymentResponse paymentResponse = responsePayment.getBody();

        ProductDetails productDetails = new ProductDetails();
        productDetails.setProductName(productResponse.getProductName());
        productDetails.setProductId(productResponse.getProductId());

        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setPaymentId(paymentResponse.getPaymentId());
        paymentDetails.setPaymentStatus(paymentResponse.getStatus());
        paymentDetails.setPaymentMode(paymentResponse.getPaymentMode());
        paymentDetails.setPaymentDate(paymentResponse.getPaymentDate());

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderId(order.getId());
        orderResponse.setOrderStatus(order.getOrderStatus());
        orderResponse.setAmount(order.getAmount());
        orderResponse.setOrderDate(order.getOrderDate());
        orderResponse.setProductDetails(productDetails);
        orderResponse.setPaymentDetails(paymentDetails);

        log.info("OrderServiceImpl | getOrderDetails | orderResponse : " + orderResponse.toString());

        return orderResponse;
    }
}
