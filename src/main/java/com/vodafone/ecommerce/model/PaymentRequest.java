package com.vodafone.ecommerce.model;

public class PaymentRequest {
    private String cardNumber;
    private Double amountDue;

    public PaymentRequest(String cardNumber, Double amountDue) {
        this.cardNumber = cardNumber;
        this.amountDue = amountDue;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Double getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(Double amountDue) {
        this.amountDue = amountDue;
    }
}
