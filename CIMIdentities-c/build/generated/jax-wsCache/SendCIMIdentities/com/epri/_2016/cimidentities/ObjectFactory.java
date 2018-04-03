
package com.epri._2016.cimidentities;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.epri._2016.cimidentities package. 
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

    private final static QName _CIMIdentities_QNAME = new QName("http://www.epri.com/2016/CIMIdentities#", "CIMIdentities");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.epri._2016.cimidentities
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CIMIdentities }
     * 
     */
    public CIMIdentities createCIMIdentities() {
        return new CIMIdentities();
    }

    /**
     * Create an instance of {@link CIMIdentity }
     * 
     */
    public CIMIdentity createCIMIdentity() {
        return new CIMIdentity();
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
     * Create an instance of {@link IdentifiedObject }
     * 
     */
    public IdentifiedObject createIdentifiedObject() {
        return new IdentifiedObject();
    }

    /**
     * Create an instance of {@link Name }
     * 
     */
    public Name createName() {
        return new Name();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CIMIdentities }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.epri.com/2016/CIMIdentities#", name = "CIMIdentities")
    public JAXBElement<CIMIdentities> createCIMIdentities(CIMIdentities value) {
        return new JAXBElement<CIMIdentities>(_CIMIdentities_QNAME, CIMIdentities.class, null, value);
    }

}
