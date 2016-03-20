package com.appurate.intellij.plugin.atf.actions;

import com.appurate.intellij.plugin.atf.ExecutionUtil;
import com.appurate.intellij.plugin.atf.binding.*;
import com.appurate.intellij.plugin.atf.typesystem.ATFType;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.util.ThrowableComputable;
import com.intellij.openapi.vfs.VirtualFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by vmansoori on 12/26/2015.
 */
public class ATFXMLModel {

    private static Logger logger = Logger.getInstance(ATFXMLModel.class);
    private ATFModel _xmlModel;
    private VirtualFile virtualFile;

    private ATFXMLModel() {

    }


    public void setBindingClass(String bindingClass) {
        BindingClass bindingClazz = new BindingClass();
        bindingClazz.setClazz(bindingClass);
//        bindingClazz.setType(bindingClass.getType()); // FIXME: 1/6/2016 ATFType.getType needs to return the language
        _xmlModel.setBindingClass(bindingClazz);
    }

    ATFXMLModel(VirtualFile virtualFile, String sourceType, String destinationType) {
        _xmlModel = new ATFModel();
        Source source = new Source();
        source.setType(sourceType);
        _xmlModel.setSource(source);

        Destination destination = new Destination();
        destination.setType(destinationType);
        _xmlModel.setDestination(destination);
        this.virtualFile = virtualFile;
    }

    public String getSourceType() {
        return _xmlModel.getSource().getType();
    }

    public String getDestinationType() {
        return _xmlModel.getDestination().getType();
    }


    public void writeTo(OutputStream outputStream) throws Exception {
        try {
            JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.marshal(_xmlModel, outputStream);
        } catch (Throwable t) {
            Logger.getInstance(this.getClass()).error(t);
        } finally {
            outputStream.flush();
            outputStream.close();
        }

    }


    public static ATFXMLModel readFile(VirtualFile file) {

        ATFXMLModel thisInstance = new ATFXMLModel();
        JAXBContext jc = null;
        try {
            jc = JAXBContext.newInstance(ObjectFactory.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();

            thisInstance._xmlModel = (ATFModel) unmarshaller.unmarshal(file.getInputStream());

        } catch (JAXBException e) {
            e.printStackTrace();

            logger.error(e);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Throwable t) {
            logger.error(t);
        } finally {

        }
        return thisInstance;
    }

    public String getBindingClass() {
        return _xmlModel.getBindingClass().getClazz();
    }

    public void addMapping(ATFType[] sourceFields, ATFType destinationField) throws Throwable {

        Mappings mappings = _xmlModel.getMappings();
        if (mappings == null) {
            mappings = new Mappings();
            _xmlModel.setMappings(mappings);
        }

        Mapping mapping = new Mapping();
        SourceProperties sourcePropertiesElement = new SourceProperties();
        List<SourceProperty> sourceProperties = sourcePropertiesElement.getSourceProperty();
        for (ATFType sourceField : sourceFields) {
            SourceProperty property = new SourceProperty();
            property.setName(sourceField.getName());
            sourceProperties.add(property);
        }
        mapping.setSourceProperties(sourcePropertiesElement);

        DestinationProperty destinationProperty = new DestinationProperty();
        destinationProperty.setName(destinationField.getName());
        mapping.setDestinationProperty(destinationProperty);
        mappings.getMapping().add(mapping);
        this.write();
    }


    public VirtualFile write() throws Throwable {
        return ExecutionUtil.execute(new ThrowableComputable() {
            @Override
            public VirtualFile compute() throws Throwable {

//                    Mapping mappingClass = createBindingClass(project, dir, virtualFile);
//                    _xmlModel.setBindingClass(mappingClass.getMappingClassName());
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ATFXMLModel.this.writeTo(outputStream);
                Document document = FileDocumentManager.getInstance().getDocument(virtualFile);
                document.setText(outputStream.toString("UTF-8"));
                FileDocumentManager.getInstance().saveDocument(document);
                return virtualFile;
            }
        });
    }
}
