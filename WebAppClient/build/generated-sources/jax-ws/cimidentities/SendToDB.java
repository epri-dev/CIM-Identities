
package cimidentities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SendToDB complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SendToDB"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="n_name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nt_name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nt_des" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nta_name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nta_des" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="uuid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="uuidEntered" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SendToDB", propOrder = {
    "nName",
    "ntName",
    "ntDes",
    "ntaName",
    "ntaDes",
    "uuid",
    "uuidEntered"
})
public class SendToDB {

    @XmlElement(name = "n_name")
    protected String nName;
    @XmlElement(name = "nt_name")
    protected String ntName;
    @XmlElement(name = "nt_des")
    protected String ntDes;
    @XmlElement(name = "nta_name")
    protected String ntaName;
    @XmlElement(name = "nta_des")
    protected String ntaDes;
    protected String uuid;
    protected boolean uuidEntered;

    /**
     * Gets the value of the nName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNName() {
        return nName;
    }

    /**
     * Sets the value of the nName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNName(String value) {
        this.nName = value;
    }

    /**
     * Gets the value of the ntName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNtName() {
        return ntName;
    }

    /**
     * Sets the value of the ntName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNtName(String value) {
        this.ntName = value;
    }

    /**
     * Gets the value of the ntDes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNtDes() {
        return ntDes;
    }

    /**
     * Sets the value of the ntDes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNtDes(String value) {
        this.ntDes = value;
    }

    /**
     * Gets the value of the ntaName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNtaName() {
        return ntaName;
    }

    /**
     * Sets the value of the ntaName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNtaName(String value) {
        this.ntaName = value;
    }

    /**
     * Gets the value of the ntaDes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNtaDes() {
        return ntaDes;
    }

    /**
     * Sets the value of the ntaDes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNtaDes(String value) {
        this.ntaDes = value;
    }

    /**
     * Gets the value of the uuid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Sets the value of the uuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUuid(String value) {
        this.uuid = value;
    }

    /**
     * Gets the value of the uuidEntered property.
     * 
     */
    public boolean isUuidEntered() {
        return uuidEntered;
    }

    /**
     * Sets the value of the uuidEntered property.
     * 
     */
    public void setUuidEntered(boolean value) {
        this.uuidEntered = value;
    }

}
