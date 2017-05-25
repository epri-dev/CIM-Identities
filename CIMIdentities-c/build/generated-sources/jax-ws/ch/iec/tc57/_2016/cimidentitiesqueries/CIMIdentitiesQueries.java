
package ch.iec.tc57._2016.cimidentitiesqueries;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CIMIdentitiesQueries complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CIMIdentitiesQueries">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EndDeviceGroup" type="{http://iec.ch/TC57/2016/CIMIdentitiesQueries#}EndDeviceGroup" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CIMIdentitiesQueries", propOrder = {
    "endDeviceGroup"
})
public class CIMIdentitiesQueries {

    @XmlElement(name = "EndDeviceGroup")
    protected List<EndDeviceGroup> endDeviceGroup;

    /**
     * Gets the value of the endDeviceGroup property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the endDeviceGroup property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEndDeviceGroup().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EndDeviceGroup }
     * 
     * 
     */
    public List<EndDeviceGroup> getEndDeviceGroup() {
        if (endDeviceGroup == null) {
            endDeviceGroup = new ArrayList<EndDeviceGroup>();
        }
        return this.endDeviceGroup;
    }

}
