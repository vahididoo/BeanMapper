package com.appurate.intellij.plugin.atf.actions;

import com.appurate.intellij.plugin.atf.binding.*;
import com.appurate.intellij.plugin.atf.typesystem.psi.ATFPsiClass;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by vmansoori on 12/26/2015.
 */
public class ATFXMLModel {

    private ATFModel _xmlModel;


    public void setBindingClass(ATFPsiClass bindingClass) {
        BindingClass bindingClazz = new BindingClass();
        bindingClazz.setClazz(((PsiClass)bindingClass.getBasedOn()).getQualifiedName());
//        bindingClazz.setType(bindingClass.getType()); // FIXME: 1/6/2016 ATFType.getType needs to return the language
        _xmlModel.setBindingClass(bindingClazz);
    }

    ATFXMLModel() {
    }

    public String getSourceType() {
        return _xmlModel.getSource().getType();
    }

    public String getDestinationType() {
        return _xmlModel.getDestination().getType();
    }


    public ATFXMLModel(String sourceStr, String destinationStr) {

        _xmlModel = new ATFModel();
        Source source = new Source();
        source.setType(sourceStr);
        _xmlModel.setSource(source);

        Destination destination = new Destination();
        destination.setType(destinationStr);
        _xmlModel.setDestination(destination);
    }


    public void writeTo(OutputStream outputStream) throws Exception {
        try {
            JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.marshal(_xmlModel, outputStream);
        } catch (Throwable t) {
            Logger.getInstance(this.getClass()).error(t);
        }

    }


    public static ATFXMLModel readFile(VirtualFile file) throws JAXBException, IOException {

        ATFXMLModel thisInstance = new ATFXMLModel();
        JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();

        thisInstance._xmlModel = (ATFModel) unmarshaller.unmarshal(file.getInputStream());
        return thisInstance;
    }

    public String getBindingClass() {
        return _xmlModel.getBindingClass().getClazz();
    }
}
