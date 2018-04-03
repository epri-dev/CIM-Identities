
package ch.iec.tc57._2016.cimidentitiesqueriesmessage;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ch.iec.tc57._2016.cimidentitiesqueriesmessage package. 
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

    private final static QName _CIMIdentitiesQueriesRequestMessage_QNAME = new QName("http://iec.ch/TC57/2016/CIMIdentitiesQueriesMessage", "CIMIdentitiesQueriesRequestMessage");
    private final static QName _CIMIdentitiesQueriesFaultMessage_QNAME = new QName("http://iec.ch/TC57/2016/CIMIdentitiesQueriesMessage", "CIMIdentitiesQueriesFaultMessage");
    private final static QName _CIMIdentitiesQueriesResponseMessage_QNAME = new QName("http://iec.ch/TC57/2016/CIMIdentitiesQueriesMessage", "CIMIdentitiesQueriesResponseMessage");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ch.iec.tc57._2016.cimidentitiesqueriesmessage
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CIMIdentitiesQueriesResponseMessageType }
     * 
     */
    public CIMIdentitiesQueriesResponseMessageType createCIMIdentitiesQueriesResponseMessageType() {
        return new CIMIdentitiesQueriesResponseMessageType();
    }

    /**
     * Create an instance of {@link CIMIdentitiesQueriesRequestMessageType }
     * 
     */
    public CIMIdentitiesQueriesRequestMessageType createCIMIdentitiesQueriesRequestMessageType() {
        return new CIMIdentitiesQueriesRequestMessageType();
    }

    /**
     * Create an instance of {@link CIMIdentitiesQueriesFaultMessageType }
     * 
     */
    public CIMIdentitiesQueriesFaultMessageType createCIMIdentitiesQueriesFaultMessageType() {
        return new CIMIdentitiesQueriesFaultMessageType();
    }

    /**
     * Create an instance of {@link CIMIdentitiesQueriesPayloadType }
     * 
     */
    public CIMIdentitiesQueriesPayloadType createCIMIdentitiesQueriesPayloadType() {
        return new CIMIdentitiesQueriesPayloadType();
    }

    /**
     * Create an instance of {@link CIMIdentitiesQueriesRequestType }
     * 
     */
    public CIMIdentitiesQueriesRequestType createCIMIdentitiesQueriesRequestType() {
        return new CIMIdentitiesQueriesRequestType();
    }

    /**
     * Create an instance of {@link CIMIdentitiesQueriesResponseType }
     * 
     */
    public CIMIdentitiesQueriesResponseType createCIMIdentitiesQueriesResponseType() {
        return new CIMIdentitiesQueriesResponseType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CIMIdentitiesQueriesRequestMessageType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://iec.ch/TC57/2016/CIMIdentitiesQueriesMessage", name = "CIMIdentitiesQueriesRequestMessage")
    public JAXBElement<CIMIdentitiesQueriesRequestMessageType> createCIMIdentitiesQueriesRequestMessage(CIMIdentitiesQueriesRequestMessageType value) {
        return new JAXBElement<CIMIdentitiesQueriesRequestMessageType>(_CIMIdentitiesQueriesRequestMessage_QNAME, CIMIdentitiesQueriesRequestMessageType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CIMIdentitiesQueriesFaultMessageType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://iec.ch/TC57/2016/CIMIdentitiesQueriesMessage", name = "CIMIdentitiesQueriesFaultMessage")
    public JAXBElement<CIMIdentitiesQueriesFaultMessageType> createCIMIdentitiesQueriesFaultMessage(CIMIdentitiesQueriesFaultMessageType value) {
        return new JAXBElement<CIMIdentitiesQueriesFaultMessageType>(_CIMIdentitiesQueriesFaultMessage_QNAME, CIMIdentitiesQueriesFaultMessageType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CIMIdentitiesQueriesResponseMessageType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://iec.ch/TC57/2016/CIMIdentitiesQueriesMessage", name = "CIMIdentitiesQueriesResponseMessage")
    public JAXBElement<CIMIdentitiesQueriesResponseMessageType> createCIMIdentitiesQueriesResponseMessage(CIMIdentitiesQueriesResponseMessageType value) {
        return new JAXBElement<CIMIdentitiesQueriesResponseMessageType>(_CIMIdentitiesQueriesResponseMessage_QNAME, CIMIdentitiesQueriesResponseMessageType.class, null, value);
    }

}
