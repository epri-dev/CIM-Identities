
package ch.iec.tc57._2016.cimidentitiesqueries;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ch.iec.tc57._2016.cimidentitiesqueries package. 
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

    private final static QName _CIMIdentitiesQueries_QNAME = new QName("http://iec.ch/TC57/2016/CIMIdentitiesQueries#", "CIMIdentitiesQueries");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ch.iec.tc57._2016.cimidentitiesqueries
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CIMIdentitiesQueries }
     * 
     */
    public CIMIdentitiesQueries createCIMIdentitiesQueries() {
        return new CIMIdentitiesQueries();
    }

    /**
     * Create an instance of {@link EndDeviceGroup }
     * 
     */
    public EndDeviceGroup createEndDeviceGroup() {
        return new EndDeviceGroup();
    }

    /**
     * Create an instance of {@link NameTypeAuthority }
     * 
     */
    public NameTypeAuthority createNameTypeAuthority() {
        return new NameTypeAuthority();
    }

    /**
     * Create an instance of {@link NameType }
     * 
     */
    public NameType createNameType() {
        return new NameType();
    }

    /**
     * Create an instance of {@link Name }
     * 
     */
    public Name createName() {
        return new Name();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CIMIdentitiesQueries }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://iec.ch/TC57/2016/CIMIdentitiesQueries#", name = "CIMIdentitiesQueries")
    public JAXBElement<CIMIdentitiesQueries> createCIMIdentitiesQueries(CIMIdentitiesQueries value) {
        return new JAXBElement<CIMIdentitiesQueries>(_CIMIdentitiesQueries_QNAME, CIMIdentitiesQueries.class, null, value);
    }

}
