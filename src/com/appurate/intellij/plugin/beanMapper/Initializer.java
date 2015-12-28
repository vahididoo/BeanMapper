package com.appurate.intellij.plugin.beanMapper;

import com.appurate.intellij.plugin.beanMapper.filetypes.GTXFileType;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.fileTypes.ExtensionFileNameMatcher;
import com.intellij.openapi.fileTypes.FileTypeManager;
import org.jetbrains.annotations.NotNull;

/**
 * Created by vmansoori on 12/26/2015.
 */
public class Initializer implements ApplicationComponent {
    public Initializer() {
    }

    @Override
    public void initComponent() {
        registerFileTypes();
    }

    private void registerFileTypes() {
        final FileTypeManager manager = FileTypeManager.getInstance();
        final GTXFileType fileType = new GTXFileType();
        class RegisterGTXFileType implements Runnable {
            @Override
            public void run() {
                manager.associate(fileType, new ExtensionFileNameMatcher("gtx"));
            }
        }
        ApplicationManager.getApplication().runWriteAction(new RegisterGTXFileType());
    }

    @Override
    public void disposeComponent() {
        // TODO: insert component disposal logic here
    }

    @Override
    @NotNull
    public String getComponentName() {
        return "Initializer";
    }
}
