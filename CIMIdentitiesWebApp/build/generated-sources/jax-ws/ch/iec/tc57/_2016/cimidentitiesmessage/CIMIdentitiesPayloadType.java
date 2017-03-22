
package ch.iec.tc57._2016.cimidentitiesmessage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import ch.iec.tc57._2016.schema.message.OperationSet;
import com.epri._2016.cimidentities_.CIMIdentities;


/**
 * <p>Java class for CIMIdentitiesPayloadType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CIMIdentitiesPayloadType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://www.epri.com/2016/CIMIdentities#}CIMIdentities"/&gt;
 *         &lt;element name="OperationSet" type="{http://iec.ch/TC57/2016/schema/message}OperationSet" minOccurs="0"/&gt;
 *         &lt;element name="Compressed" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Format" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CIMIdentitiesPayloadType", propOrder = {
    "cimIdentities",
    "operationSet",
    "compressed",
    "format"
})
public class CIMIdentitiesPayloadType {

    @XmlElement(name = "CIMIdentities", namespace = "http://www.epri.com/2016/CIMIdentities#", required = true)
    protected CIMIdentities cimIdentities;
    @XmlElement(name = "OperationSet")
    protected OperationSet operationSet;
    @XmlElement(name = "Compressed")
    protected String compressed;
    @XmlElement(name = "Format")
    protected String format;

    /**
     * Gets the value of the cimIdentities property.
     * 
     * @return
     *     possible object is
     *     {@link CIMIdentities }
     *     
     */
    public CIMIdentities getCIMIdentities() {
        return cimIdentities;
    }

    /**
     * Sets the value of the cimIdentities property.
     * 
     * @param value
     *     allowed object is
     *     {@link CIMIdentities }
     *     
     */
    public void setCIMIdentities(CIMIdentities value) {
        this.cimIdentities = value;
    }

    /**
     * Gets the value of the operationSet property.
     * 
     * @return
     *     possible object is
     *     {@link OperationSet }
     *     
     */
    public OperationSet getOperationSet() {
        return operationSet;
    }

    /**
     * Sets the value of the operationSet property.
     * 
     * @param value
     *     allowed object is
     *     {@link OperationSet }
     *     
     */
    public void setOperationSet(OperationSet value) {
        this.operationSet = value;
    }

    /**
     * Gets the value of the compressed property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompressed() {
        return compressed;
    }

    /**
     * Sets the value of the compressed property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompressed(String value) {
        this.compressed = value;
    }

    /**
     * Gets the value of the format property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormat() {
        return format;
    }

    /**
     * Sets the value of the format property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormat(String value) {
        this.format = value;
    }

}
