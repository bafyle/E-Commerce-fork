
package com.vodafone.ecommerce.soap_consumer;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for validateCard complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="validateCard"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="cardNumber" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="pin" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="expireDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validateCard", propOrder = {
    "cardNumber",
    "pin",
    "expireDate"
})
public class ValidateCard {

    protected long cardNumber;
    protected int pin;
    protected String expireDate;

    /**
     * Gets the value of the cardNumber property.
     * 
     */
    public long getCardNumber() {
        return cardNumber;
    }

    /**
     * Sets the value of the cardNumber property.
     * 
     */
    public void setCardNumber(long value) {
        this.cardNumber = value;
    }

    /**
     * Gets the value of the pin property.
     * 
     */
    public int getPin() {
        return pin;
    }

    /**
     * Sets the value of the pin property.
     * 
     */
    public void setPin(int value) {
        this.pin = value;
    }

    /**
     * Gets the value of the expireDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpireDate() {
        return expireDate;
    }

    /**
     * Sets the value of the expireDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpireDate(String value) {
        this.expireDate = value;
    }

}
