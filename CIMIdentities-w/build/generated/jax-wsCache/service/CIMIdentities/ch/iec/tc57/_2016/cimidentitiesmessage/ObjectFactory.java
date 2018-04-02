
package ch.iec.tc57._2016.cimidentitiesmessage;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ch.iec.tc57._2016.cimidentitiesmessage package. 
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

    private final static QName _CreateCIMIdentities_QNAME = new QName("http://iec.ch/TC57/2016/CIMIdentitiesMessage", "CreateCIMIdentities");
    private final static QName _ChangeCIMIdentities_QNAME = new QName("http://iec.ch/TC57/2016/CIMIdentitiesMessage", "ChangeCIMIdentities");
    private final static QName _CancelCIMIdentities_QNAME = new QName("http://iec.ch/TC57/2016/CIMIdentitiesMessage", "CancelCIMIdentities");
    private final static QName _CloseCIMIdentities_QNAME = new QName("http://iec.ch/TC57/2016/CIMIdentitiesMessage", "CloseCIMIdentities");
    private final static QName _DeleteCIMIdentities_QNAME = new QName("http://iec.ch/TC57/2016/CIMIdentitiesMessage", "DeleteCIMIdentities");
    private final static QName _CreatedCIMIdentities_QNAME = new QName("http://iec.ch/TC57/2016/CIMIdentitiesMessage", "CreatedCIMIdentities");
    private final static QName _ChangedCIMIdentities_QNAME = new QName("http://iec.ch/TC57/2016/CIMIdentitiesMessage", "ChangedCIMIdentities");
    private final static QName _CanceledCIMIdentities_QNAME = new QName("http://iec.ch/TC57/2016/CIMIdentitiesMessage", "CanceledCIMIdentities");
    private final static QName _ClosedCIMIdentities_QNAME = new QName("http://iec.ch/TC57/2016/CIMIdentitiesMessage", "ClosedCIMIdentities");
    private final static QName _DeletedCIMIdentities_QNAME = new QName("http://iec.ch/TC57/2016/CIMIdentitiesMessage", "DeletedCIMIdentities");
    private final static QName _CIMIdentitiesResponseMessage_QNAME = new QName("http://iec.ch/TC57/2016/CIMIdentitiesMessage", "CIMIdentitiesResponseMessage");
    private final static QName _CIMIdentitiesFaultMessage_QNAME = new QName("http://iec.ch/TC57/2016/CIMIdentitiesMessage", "CIMIdentitiesFaultMessage");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ch.iec.tc57._2016.cimidentitiesmessage
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CIMIdentitiesRequestMessageType }
     * 
     */
    public CIMIdentitiesRequestMessageType createCIMIdentitiesRequestMessageType() {
        return new CIMIdentitiesRequestMessageType();
    }

    /**
     * Create an instance of {@link CIMIdentitiesEventMessageType }
     * 
     */
    public CIMIdentitiesEventMessageType createCIMIdentitiesEventMessageType() {
        return new CIMIdentitiesEventMessageType();
    }

    /**
     * Create an instance of {@link CIMIdentitiesResponseMessageType }
     * 
     */
    public CIMIdentitiesResponseMessageType createCIMIdentitiesResponseMessageType() {
        return new CIMIdentitiesResponseMessageType();
    }

    /**
     * Create an instance of {@link CIMIdentitiesFaultMessageType }
     * 
     */
    public CIMIdentitiesFaultMessageType createCIMIdentitiesFaultMessageType() {
        return new CIMIdentitiesFaultMessageType();
    }

    /**
     * Create an instance of {@link CIMIdentitiesPayloadType }
     * 
     */
    public CIMIdentitiesPayloadType createCIMIdentitiesPayloadType() {
        return new CIMIdentitiesPayloadType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CIMIdentitiesRequestMessageType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://iec.ch/TC57/2016/CIMIdentitiesMessage", name = "CreateCIMIdentities")
    public JAXBElement<CIMIdentitiesRequestMessageType> createCreateCIMIdentities(CIMIdentitiesRequestMessageType value) {
        return new JAXBElement<CIMIdentitiesRequestMessageType>(_CreateCIMIdentities_QNAME, CIMIdentitiesRequestMessageType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CIMIdentitiesRequestMessageType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://iec.ch/TC57/2016/CIMIdentitiesMessage", name = "ChangeCIMIdentities")
    public JAXBElement<CIMIdentitiesRequestMessageType> createChangeCIMIdentities(CIMIdentitiesRequestMessageType value) {
        return new JAXBElement<CIMIdentitiesRequestMessageType>(_ChangeCIMIdentities_QNAME, CIMIdentitiesRequestMessageType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CIMIdentitiesRequestMessageType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://iec.ch/TC57/2016/CIMIdentitiesMessage", name = "CancelCIMIdentities")
    public JAXBElement<CIMIdentitiesRequestMessageType> createCancelCIMIdentities(CIMIdentitiesRequestMessageType value) {
        return new JAXBElement<CIMIdentitiesRequestMessageType>(_CancelCIMIdentities_QNAME, CIMIdentitiesRequestMessageType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CIMIdentitiesRequestMessageType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://iec.ch/TC57/2016/CIMIdentitiesMessage", name = "CloseCIMIdentities")
    public JAXBElement<CIMIdentitiesRequestMessageType> createCloseCIMIdentities(CIMIdentitiesRequestMessageType value) {
        return new JAXBElement<CIMIdentitiesRequestMessageType>(_CloseCIMIdentities_QNAME, CIMIdentitiesRequestMessageType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CIMIdentitiesRequestMessageType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://iec.ch/TC57/2016/CIMIdentitiesMessage", name = "DeleteCIMIdentities")
    public JAXBElement<CIMIdentitiesRequestMessageType> createDeleteCIMIdentities(CIMIdentitiesRequestMessageType value) {
        return new JAXBElement<CIMIdentitiesRequestMessageType>(_DeleteCIMIdentities_QNAME, CIMIdentitiesRequestMessageType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CIMIdentitiesEventMessageType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://iec.ch/TC57/2016/CIMIdentitiesMessage", name = "CreatedCIMIdentities")
    public JAXBElement<CIMIdentitiesEventMessageType> createCreatedCIMIdentities(CIMIdentitiesEventMessageType value) {
        return new JAXBElement<CIMIdentitiesEventMessageType>(_CreatedCIMIdentities_QNAME, CIMIdentitiesEventMessageType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CIMIdentitiesEventMessageType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://iec.ch/TC57/2016/CIMIdentitiesMessage", name = "ChangedCIMIdentities")
    public JAXBElement<CIMIdentitiesEventMessageType> createChangedCIMIdentities(CIMIdentitiesEventMessageType value) {
        return new JAXBElement<CIMIdentitiesEventMessageType>(_ChangedCIMIdentities_QNAME, CIMIdentitiesEventMessageType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CIMIdentitiesEventMessageType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://iec.ch/TC57/2016/CIMIdentitiesMessage", name = "CanceledCIMIdentities")
    public JAXBElement<CIMIdentitiesEventMessageType> createCanceledCIMIdentities(CIMIdentitiesEventMessageType value) {
        return new JAXBElement<CIMIdentitiesEventMessageType>(_CanceledCIMIdentities_QNAME, CIMIdentitiesEventMessageType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CIMIdentitiesEventMessageType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://iec.ch/TC57/2016/CIMIdentitiesMessage", name = "ClosedCIMIdentities")
    public JAXBElement<CIMIdentitiesEventMessageType> createClosedCIMIdentities(CIMIdentitiesEventMessageType value) {
        return new JAXBElement<CIMIdentitiesEventMessageType>(_ClosedCIMIdentities_QNAME, CIMIdentitiesEventMessageType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CIMIdentitiesEventMessageType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://iec.ch/TC57/2016/CIMIdentitiesMessage", name = "DeletedCIMIdentities")
    public JAXBElement<CIMIdentitiesEventMessageType> createDeletedCIMIdentities(CIMIdentitiesEventMessageType value) {
        return new JAXBElement<CIMIdentitiesEventMessageType>(_DeletedCIMIdentities_QNAME, CIMIdentitiesEventMessageType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CIMIdentitiesResponseMessageType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://iec.ch/TC57/2016/CIMIdentitiesMessage", name = "CIMIdentitiesResponseMessage")
    public JAXBElement<CIMIdentitiesResponseMessageType> createCIMIdentitiesResponseMessage(CIMIdentitiesResponseMessageType value) {
        return new JAXBElement<CIMIdentitiesResponseMessageType>(_CIMIdentitiesResponseMessage_QNAME, CIMIdentitiesResponseMessageType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CIMIdentitiesFaultMessageType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://iec.ch/TC57/2016/CIMIdentitiesMessage", name = "CIMIdentitiesFaultMessage")
    public JAXBElement<CIMIdentitiesFaultMessageType> createCIMIdentitiesFaultMessage(CIMIdentitiesFaultMessageType value) {
        return new JAXBElement<CIMIdentitiesFaultMessageType>(_CIMIdentitiesFaultMessage_QNAME, CIMIdentitiesFaultMessageType.class, null, value);
    }

}
