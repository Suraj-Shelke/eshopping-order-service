package com.sp.eshopping_order_service.payload.response;

import com.sp.eshopping_order_service.model.PaymentDetails;
import com.sp.eshopping_order_service.model.ProductDetails;

import java.time.Instant;


public class OrderResponse {
    private long orderId;
    private Instant orderDate;
    private String orderStatus;
    private long amount;
    private ProductDetails productDetails;
    private PaymentDetails paymentDetails;

    public OrderResponse() {
    }

    public OrderResponse(long orderId, Instant orderDate, String orderStatus, long amount, ProductDetails productDetails, PaymentDetails paymentDetails) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.amount = amount;
        this.productDetails = productDetails;
        this.paymentDetails = paymentDetails;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public Instant getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Instant orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public ProductDetails getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(ProductDetails productDetails) {
        this.productDetails = productDetails;
    }

    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderResponse{");
        sb.append("orderId=").append(orderId);
        sb.append(", orderDate=").append(orderDate);
        sb.append(", orderStatus='").append(orderStatus).append('\'');
        sb.append(", amount=").append(amount);
        sb.append(", productDetails=").append(productDetails);
        sb.append(", paymentDetails=").append(paymentDetails);
        sb.append('}');
        return sb.toString();
    }
}
