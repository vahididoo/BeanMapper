package com.appurate.intellij.plugin.atf.actions;

import com.appurate.intellij.plugin.atf.binding.ATFModel;
import com.appurate.intellij.plugin.atf.binding.Destination;
import com.appurate.intellij.plugin.atf.binding.ObjectFactory;
import com.appurate.intellij.plugin.atf.binding.Source;
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
public class ATFXMLModel {

    private ATFModel _xmlModel;

    ATFXMLModel() {
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

        JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.marshal(_xmlModel,outputStream);

    }


    public static ATFXMLModel readFile(VirtualFile file) throws JAXBException, IOException {

        ATFXMLModel thisInstance = new ATFXMLModel();
        JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();

        thisInstance._xmlModel = (ATFModel) unmarshaller.unmarshal(file.getInputStream());
        return thisInstance;
    }
}
