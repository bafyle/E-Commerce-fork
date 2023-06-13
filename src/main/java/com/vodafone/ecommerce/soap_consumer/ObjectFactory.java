
package com.vodafone.ecommerce.soap_consumer;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.vodafone.ecommerce.soap_consumer package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ValidateCard_QNAME = new QName("http://vodafone.com/", "validateCard");
    private final static QName _ValidateCardResponse_QNAME = new QName("http://vodafone.com/", "validateCardResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.vodafone.ecommerce.soap_consumer
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ValidateCard }
     * 
     */
    public ValidateCard createValidateCard() {
        return new ValidateCard();
    }

    /**
     * Create an instance of {@link ValidateCardResponse }
     * 
     */
    public ValidateCardResponse createValidateCardResponse() {
        return new ValidateCardResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateCard }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ValidateCard }{@code >}
     */
    @XmlElementDecl(namespace = "http://vodafone.com/", name = "validateCard")
    public JAXBElement<ValidateCard> createValidateCard(ValidateCard value) {
        return new JAXBElement<ValidateCard>(_ValidateCard_QNAME, ValidateCard.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateCardResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ValidateCardResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://vodafone.com/", name = "validateCardResponse")
    public JAXBElement<ValidateCardResponse> createValidateCardResponse(ValidateCardResponse value) {
        return new JAXBElement<ValidateCardResponse>(_ValidateCardResponse_QNAME, ValidateCardResponse.class, null, value);
    }

}
