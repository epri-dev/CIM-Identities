
package ch.iec.tc57._2016.cimidentitiesqueriesmessage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import ch.iec.tc57._2011.schema.message.HeaderType;
import ch.iec.tc57._2011.schema.message.ReplyType;


/**
 * <p>Java class for CIMIdentitiesQueriesResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CIMIdentitiesQueriesResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Header" type="{http://iec.ch/TC57/2011/schema/message}HeaderType"/&gt;
 *         &lt;element name="Reply" type="{http://iec.ch/TC57/2011/schema/message}ReplyType"/&gt;
 *         &lt;element name="Payload" type="{http://iec.ch/TC57/2016/CIMIdentitiesQueriesMessage}CIMIdentitiesQueriesPayloadType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CIMIdentitiesQueriesResponseType", propOrder = {
    "header",
    "reply",
    "payload"
})
public class CIMIdentitiesQueriesResponseType {

    @XmlElement(name = "Header", required = true)
    protected HeaderType header;
    @XmlElement(name = "Reply", required = true)
    protected ReplyType reply;
    @XmlElement(name = "Payload")
    protected CIMIdentitiesQueriesPayloadType payload;

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
     * Gets the value of the reply property.
     * 
     * @return
     *     possible object is
     *     {@link ReplyType }
     *     
     */
    public ReplyType getReply() {
        return reply;
    }

    /**
     * Sets the value of the reply property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReplyType }
     *     
     */
    public void setReply(ReplyType value) {
        this.reply = value;
    }

    /**
     * Gets the value of the payload property.
     * 
     * @return
     *     possible object is
     *     {@link CIMIdentitiesQueriesPayloadType }
     *     
     */
    public CIMIdentitiesQueriesPayloadType getPayload() {
        return payload;
    }

    /**
     * Sets the value of the payload property.
     * 
     * @param value
     *     allowed object is
     *     {@link CIMIdentitiesQueriesPayloadType }
     *     
     */
    public void setPayload(CIMIdentitiesQueriesPayloadType value) {
        this.payload = value;
    }

}
