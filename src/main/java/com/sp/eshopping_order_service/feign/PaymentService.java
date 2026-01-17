package com.sp.eshopping_order_service.feign;

import com.sp.eshopping_order_service.payload.request.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "eshopping-payment-service" )
public interface PaymentService {
    @PostMapping("/payment")
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest);
}
