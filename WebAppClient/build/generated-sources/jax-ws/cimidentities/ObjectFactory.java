
package cimidentities;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cimidentities package. 
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

    private final static QName _SendToDB_QNAME = new QName("http://cimidentities/", "SendToDB");
    private final static QName _SendToDBResponse_QNAME = new QName("http://cimidentities/", "SendToDBResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cimidentities
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SendToDB }
     * 
     */
    public SendToDB createSendToDB() {
        return new SendToDB();
    }

    /**
     * Create an instance of {@link SendToDBResponse }
     * 
     */
    public SendToDBResponse createSendToDBResponse() {
        return new SendToDBResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendToDB }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cimidentities/", name = "SendToDB")
    public JAXBElement<SendToDB> createSendToDB(SendToDB value) {
        return new JAXBElement<SendToDB>(_SendToDB_QNAME, SendToDB.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendToDBResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cimidentities/", name = "SendToDBResponse")
    public JAXBElement<SendToDBResponse> createSendToDBResponse(SendToDBResponse value) {
        return new JAXBElement<SendToDBResponse>(_SendToDBResponse_QNAME, SendToDBResponse.class, null, value);
    }

}
