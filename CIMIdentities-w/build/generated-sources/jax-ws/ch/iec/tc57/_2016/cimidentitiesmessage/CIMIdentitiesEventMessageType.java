
package ch.iec.tc57._2016.cimidentitiesmessage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import ch.iec.tc57._2011.schema.message.HeaderType;


/**
 * <p>Java class for CIMIdentitiesEventMessageType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CIMIdentitiesEventMessageType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Header" type="{http://iec.ch/TC57/2011/schema/message}HeaderType"/&gt;
 *         &lt;element name="Payload" type="{http://iec.ch/TC57/2016/CIMIdentitiesMessage}CIMIdentitiesPayloadType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CIMIdentitiesEventMessageType", propOrder = {
    "header",
    "payload"
})
public class CIMIdentitiesEventMessageType {

    @XmlElement(name = "Header", required = true)
    protected HeaderType header;
    @XmlElement(name = "Payload")
    protected CIMIdentitiesPayloadType payload;

    /**
     * Gets the value of the header property.
     * 
     * @return
     *     possible object is
     *     {@link HeaderType }
     *     
     */
    public HeaderType getHeader() {
        return header;
    }

    /**
     * Sets the value of the header property.
     * 
     * @param value
     *     allowed object is
     *     {@link HeaderType }
     *     
     */
    public void setHeader(HeaderType value) {
        this.header = value;
    }

    /**
     * Gets the value of the payload property.
     * 
     * @return
     *     possible object is
     *     {@link CIMIdentitiesPayloadType }
     *     
     */
    public CIMIdentitiesPayloadType getPayload() {
        return payload;
    }

    /**
     * Sets the value of the payload property.
     * 
     * @param value
     *     allowed object is
     *     {@link CIMIdentitiesPayloadType }
     *     
     */
    public void setPayload(CIMIdentitiesPayloadType value) {
        this.payload = value;
    }

}
