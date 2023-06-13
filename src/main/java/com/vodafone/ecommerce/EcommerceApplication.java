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




	//	 Testing Rest API - Works fine

        ValidationCardUtil.validateCard("1111111111111111", "1234", "01", "2024");

        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.vodafone.ecommerce.soap_consumer");

        SoapClient client = new SoapClient();
        client.setDefaultUri("http://localhost:9090/wsdl");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);

       // HasValidCardUseCase hasValidCardUseCase = new HasValidCardUseCase(client);

        System.out.println("Should return True: " +
                ValidationCardUtil.validateCard("1111111111111111", "1234", "01", "2024"));

        System.out.println("Should return False: " +
				ValidationCardUtil.validateCard("111111111111111", "1234", "09", "2023"));

        System.out.println("Should return False: " +
				ValidationCardUtil.validateCard("1111111111111111", "134", "09", "2023"));

        System.out.println("Should return False: " +
				ValidationCardUtil.validateCard("1111111111111111", "1234", "11", "2023"));

        System.out.println("Should return False: " +
				ValidationCardUtil.validateCard("1111111111111111", "1234", "11", "2025"));


 //Testing Rest API - Works fine

        System.out.println("True: " +
                PaymentUtil.canFulfilOrder(new PaymentRequest("1111111111111111", 1D)));



	}

}
