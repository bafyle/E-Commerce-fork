package com.vodafone.ecommerce;

import com.vodafone.ecommerce.model.PaymentRequest;
import com.vodafone.ecommerce.soap_consumer.SoapClient;
import com.vodafone.ecommerce.util.PaymentUtil;
import com.vodafone.ecommerce.util.ValidationCardUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}
}
