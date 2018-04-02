
package com.epri._2016.cimidentities_;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CIMIdentities complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CIMIdentities"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CIMIdentity" type="{http://www.epri.com/2016/CIMIdentities#}CIMIdentity" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CIMIdentities", propOrder = {
    "cimIdentity"
})
public class CIMIdentities {

    @XmlElement(name = "CIMIdentity", required = true)
    protected List<CIMIdentity> cimIdentity;

    /**
     * Gets the value of the cimIdentity property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cimIdentity property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCIMIdentity().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CIMIdentity }
     * 
     * 
     */
    public List<CIMIdentity> getCIMIdentity() {
        if (cimIdentity == null) {
            cimIdentity = new ArrayList<CIMIdentity>();
        }
        return this.cimIdentity;
    }

}
