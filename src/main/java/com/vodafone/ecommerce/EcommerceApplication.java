package com.vodafone.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);




		// Testing Rest API - Works fine

//        ValidationCardService validationCardService = new ValidationCardService();
//        validationCardService.validateCard("1111111111111111", "1234", "01", "2024");

//        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
//        marshaller.setContextPath("com.vodafone.ecommerce.soap_consumer");
//
//        SoapClient client = new SoapClient();
//        client.setDefaultUri("http://localhost:9090/wsdl");
//        client.setMarshaller(marshaller);
//        client.setUnmarshaller(marshaller);
//
//        HasValidCardUseCase hasValidCardUseCase = new HasValidCardUseCase(client);
//
//        System.out.println("Should return True: " +
//                hasValidCardUseCase.isValidCard("1111111111111111", "1234", "01", "2024"));
//
//        System.out.println("Should return False: " +
//                hasValidCardUseCase.isValidCard("111111111111111", "1234", "09", "2023"));
//
//        System.out.println("Should return False: " +
//                hasValidCardUseCase.isValidCard("1111111111111111", "134", "09", "2023"));
//
//        System.out.println("Should return False: " +
//                hasValidCardUseCase.isValidCard("1111111111111111", "1234", "11", "2023"));
//
//        System.out.println("Should return False: " +
//                hasValidCardUseCase.isValidCard("1111111111111111", "1234", "11", "2025"));
//

// Testing Rest API - Works fine

//        System.out.println("True: " +
//                CanFulfilOrderByCardUseCase.canFulfilOrder(new PaymentRequest("1111111111111111", 1D)));



	}

}
