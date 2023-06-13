package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.soap_consumer.ObjectFactory;
import com.vodafone.ecommerce.soap_consumer.SoapClient;
import com.vodafone.ecommerce.soap_consumer.ValidateCard;
import com.vodafone.ecommerce.soap_consumer.ValidateCardResponse;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;

@Service
public class ValidationCardService {


    public boolean validateCard(String cardNumber, String pinNumber, String expirationMonth, String expirationYear){ // Month must be 2 digits (##)
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.vodafone.ecommerce.soap_consumer");

        SoapClient client = new SoapClient();
        client.setDefaultUri("http://localhost:9090/wsdl");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);

        ObjectFactory factory = new ObjectFactory();
        ValidateCard validateCard = new ValidateCard();
        validateCard.setCardNumber(Long.parseLong(cardNumber));
        validateCard.setPin(Integer.parseInt(pinNumber));
        validateCard.setExpireDate(expirationYear +"-" + expirationMonth + "-28");

        ValidateCardResponse validateCardResponse = client.validateCard("http://localhost:9090/wsdl", factory.createValidateCard(validateCard));
        return validateCardResponse.getResult().equals("Valid");
    }
}
