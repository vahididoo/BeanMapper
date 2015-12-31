package com.appurate.intellij.plugin.beanMapper.actions;

import com.appurate.intellij.plugin.beanMapper.binding.Destination;
import com.appurate.intellij.plugin.beanMapper.binding.GTXModel;
import com.appurate.intellij.plugin.beanMapper.binding.ObjectFactory;
import com.appurate.intellij.plugin.beanMapper.binding.Source;
import com.intellij.openapi.vfs.VirtualFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by vmansoori on 12/26/2015.
 */
public class GTXXMLModel {

    private GTXModel _xmlModel;

    GTXXMLModel() {
    }

    public GTXXMLModel(String sourceStr, String destinationStr) {

        _xmlModel = new GTXModel();
        Source source = new Source();
        source.setType(sourceStr);
        _xmlModel.setSource(source);

        Destination destination = new Destination();
        destination.setType(destinationStr);
        _xmlModel.setDestination(destination);
    }


    public void writeTo(OutputStream outputStream) throws Exception {

        JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.marshal(_xmlModel,outputStream);

    }


    public static GTXXMLModel readFile(VirtualFile file) throws JAXBException, IOException {

        GTXXMLModel thisInstance = new GTXXMLModel();
        JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();

        thisInstance._xmlModel = (GTXModel) unmarshaller.unmarshal(file.getInputStream());
        return thisInstance;
    }
}
