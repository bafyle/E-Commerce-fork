package com.vodafone.ecommerce.util;


import com.vodafone.ecommerce.model.PaymentRequest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class NetworkHandler {

    private static final String PAYMENT_RESOURCE_URI = "http://localhost:8080/demo/payment/checkout";
    public Boolean checkout(PaymentRequest paymentRequest)
    {
        try {
            // json formatted data
            String json = new StringBuilder()
                    .append("{")
                    .append("\"cardNumber\": ")
                    .append(paymentRequest.getCardNumber()).append(" ,")
                    .append("\"amountDue\": ")
                    .append(paymentRequest.getAmountDue())
                    .append("}").toString();

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .header("Content-Type", "application/json")
                    .uri(URI.create(PAYMENT_RESOURCE_URI))
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Status code: " + response.statusCode());
            System.out.println("\n Body: " + response.body());

            return response.statusCode() == 200;
        }
        catch (IOException | InterruptedException e)
        {
            return false;
        }
    }
}
