
package com.appurate.intellij.plugin.beanMapper.binding;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}sourceProperties"/>
 *         &lt;element ref="{}destinationProperty"/>
 *         &lt;element ref="{}transformerClass"/>
 *         &lt;element ref="{}transformation"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "sourceProperties",
    "destinationProperty",
    "transformerClass",
    "transformation"
})
@XmlRootElement(name = "mapping")
public class Mapping {

    @XmlElement(required = true)
    protected SourceProperties sourceProperties;
    @XmlElement(required = true)
    protected DestinationProperty destinationProperty;
    @XmlElement(required = true)
    protected TransformerClass transformerClass;
    @XmlElement(required = true)
    protected String transformation;

    /**
     * Gets the value of the sourceProperties property.
     * 
     * @return
     *     possible object is
     *     {@link SourceProperties }
     *     
     */
    public SourceProperties getSourceProperties() {
        return sourceProperties;
    }

    /**
     * Sets the value of the sourceProperties property.
     * 
     * @param value
     *     allowed object is
     *     {@link SourceProperties }
     *     
     */
    public void setSourceProperties(SourceProperties value) {
        this.sourceProperties = value;
    }

    /**
     * Gets the value of the destinationProperty property.
     * 
     * @return
     *     possible object is
     *     {@link DestinationProperty }
     *     
     */
    public DestinationProperty getDestinationProperty() {
        return destinationProperty;
    }

    /**
     * Sets the value of the destinationProperty property.
     * 
     * @param value
     *     allowed object is
     *     {@link DestinationProperty }
     *     
     */
    public void setDestinationProperty(DestinationProperty value) {
        this.destinationProperty = value;
    }

    /**
     * Gets the value of the transformerClass property.
     * 
     * @return
     *     possible object is
     *     {@link TransformerClass }
     *     
     */
    public TransformerClass getTransformerClass() {
        return transformerClass;
    }

    /**
     * Sets the value of the transformerClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransformerClass }
     *     
     */
    public void setTransformerClass(TransformerClass value) {
        this.transformerClass = value;
    }

    /**
     * Gets the value of the transformation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransformation() {
        return transformation;
    }

    /**
     * Sets the value of the transformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransformation(String value) {
        this.transformation = value;
    }

}
