package com.vodafone.ecommerce.soap_consumer;

import jakarta.xml.bind.JAXBElement;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;


public class SoapClient extends WebServiceGatewaySupport {
    public ValidateCardResponse validateCard(String url, Object request) {
        JAXBElement res = (JAXBElement) getWebServiceTemplate().marshalSendAndReceive(url, request);
        return (ValidateCardResponse) res.getValue();


    }


}
