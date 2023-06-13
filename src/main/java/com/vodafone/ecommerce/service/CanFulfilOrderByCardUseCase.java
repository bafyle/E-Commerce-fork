package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.model.PaymentRequest;
import com.vodafone.ecommerce.util.NetworkHandler;

public class CanFulfilOrderByCardUseCase {

    private static final NetworkHandler networkHandler = new NetworkHandler();

    public static boolean canFulfilOrder(PaymentRequest paymentRequest)
    {
        return networkHandler.checkout(paymentRequest);
    }
}
