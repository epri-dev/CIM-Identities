
package com.epri._2016.cimidentities_;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CIMIdentity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CIMIdentity"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IdentifiedObject" type="{http://www.epri.com/2016/CIMIdentities#}IdentifiedObject"/&gt;
 *         &lt;element name="Names" type="{http://www.epri.com/2016/CIMIdentities#}Name" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CIMIdentity", propOrder = {
    "identifiedObject",
    "names"
})
public class CIMIdentity {

    @XmlElement(name = "IdentifiedObject", required = true)
    protected IdentifiedObject identifiedObject;
    @XmlElement(name = "Names", required = true)
    protected List<Name> names;

    /**
     * Gets the value of the identifiedObject property.
     * 
     * @return
     *     possible object is
     *     {@link IdentifiedObject }
     *     
     */
    public IdentifiedObject getIdentifiedObject() {
        return identifiedObject;
    }

    /**
     * Sets the value of the identifiedObject property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentifiedObject }
     *     
     */
    public void setIdentifiedObject(IdentifiedObject value) {
        this.identifiedObject = value;
    }

    /**
     * Gets the value of the names property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the names property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNames().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Name }
     * 
     * 
     */
    public List<Name> getNames() {
        if (names == null) {
            names = new ArrayList<Name>();
        }
        return this.names;
    }

    public void setIdentifiedObject(com.epri._2016.cimidentities.IdentifiedObject value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
