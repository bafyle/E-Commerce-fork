package com.vodafone.ecommerce.util;

import com.vodafone.ecommerce.model.PaymentRequest;
import com.vodafone.ecommerce.util.NetworkHandler;

public class PaymentUtil {

    private static final NetworkHandler networkHandler = new NetworkHandler();

    public static boolean canFulfilOrder(PaymentRequest paymentRequest)
    {
        return networkHandler.checkout(paymentRequest);
    }
}
