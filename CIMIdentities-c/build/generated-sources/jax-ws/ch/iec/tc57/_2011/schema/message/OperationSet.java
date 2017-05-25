
package ch.iec.tc57._2011.schema.message;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Each operation set is a collection of operations that may require operational-integrity and/or sequence control.
 * 
 * <p>Java class for OperationSet complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OperationSet">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="enforceMsgSequence" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="enforceTransactionalIntegrity" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Operation" type="{http://iec.ch/TC57/2011/schema/message}OperationType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OperationSet", propOrder = {
    "enforceMsgSequence",
    "enforceTransactionalIntegrity",
    "operation"
})
public class OperationSet {

    protected Boolean enforceMsgSequence;
    protected Boolean enforceTransactionalIntegrity;
    @XmlElement(name = "Operation")
    protected List<OperationType> operation;

    /**
     * Gets the value of the enforceMsgSequence property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEnforceMsgSequence() {
        return enforceMsgSequence;
    }

    /**
     * Sets the value of the enforceMsgSequence property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEnforceMsgSequence(Boolean value) {
        this.enforceMsgSequence = value;
    }

    /**
     * Gets the value of the enforceTransactionalIntegrity property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEnforceTransactionalIntegrity() {
        return enforceTransactionalIntegrity;
    }

    /**
     * Sets the value of the enforceTransactionalIntegrity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEnforceTransactionalIntegrity(Boolean value) {
        this.enforceTransactionalIntegrity = value;
    }

    /**
     * Gets the value of the operation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the operation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOperation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OperationType }
     * 
     * 
     */
    public List<OperationType> getOperation() {
        if (operation == null) {
            operation = new ArrayList<OperationType>();
        }
        return this.operation;
    }

}
