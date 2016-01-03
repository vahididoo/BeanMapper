
package com.appurate.intellij.plugin.atf.binding;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.appurate.intellij.plugin.atf.binding package. 
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

    private final static QName _Transformation_QNAME = new QName("", "transformation");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.appurate.intellij.plugin.atf.binding
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SourceProperties }
     * 
     */
    public SourceProperties createSourceProperties() {
        return new SourceProperties();
    }

    /**
     * Create an instance of {@link SourceProperty }
     * 
     */
    public SourceProperty createSourceProperty() {
        return new SourceProperty();
    }

    /**
     * Create an instance of {@link Source }
     * 
     */
    public Source createSource() {
        return new Source();
    }

    /**
     * Create an instance of {@link Mappings }
     * 
     */
    public Mappings createMappings() {
        return new Mappings();
    }

    /**
     * Create an instance of {@link Mapping }
     * 
     */
    public Mapping createMapping() {
        return new Mapping();
    }

    /**
     * Create an instance of {@link DestinationProperty }
     * 
     */
    public DestinationProperty createDestinationProperty() {
        return new DestinationProperty();
    }

    /**
     * Create an instance of {@link TransformerClass }
     * 
     */
    public TransformerClass createTransformerClass() {
        return new TransformerClass();
    }

    /**
     * Create an instance of {@link Destination }
     * 
     */
    public Destination createDestination() {
        return new Destination();
    }

    /**
     * Create an instance of {@link ATFModel }
     * 
     */
    public ATFModel createATFModel() {
        return new ATFModel();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "transformation")
    public JAXBElement<String> createTransformation(String value) {
        return new JAXBElement<String>(_Transformation_QNAME, String.class, null, value);
    }

}
