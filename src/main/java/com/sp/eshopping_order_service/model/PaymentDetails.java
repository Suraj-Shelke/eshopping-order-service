package com.sp.eshopping_order_service.model;

import com.sp.eshopping_order_service.utils.PaymentMode;

import java.time.Instant;


public class PaymentDetails{
    private long paymentId;
    private PaymentMode paymentMode;
    private String paymentStatus;
    private Instant paymentDate;

    public PaymentDetails() {
    }

    public PaymentDetails(long paymentId, PaymentMode paymentMode, String paymentStatus, Instant paymentDate) {
        this.paymentId = paymentId;
        this.paymentMode = paymentMode;
        this.paymentStatus = paymentStatus;
        this.paymentDate = paymentDate;
    }

    public long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Instant getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Instant paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PaymentDetails{");
        sb.append("paymentId=").append(paymentId);
        sb.append(", paymentMode=").append(paymentMode);
        sb.append(", paymentStatus='").append(paymentStatus).append('\'');
        sb.append(", paymentDate=").append(paymentDate);
        sb.append('}');
        return sb.toString();
    }
}
