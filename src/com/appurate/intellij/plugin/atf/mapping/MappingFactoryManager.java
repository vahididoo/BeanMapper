package com.appurate.intellij.plugin.atf.mapping;

import com.appurate.intellij.plugin.atf.mapping.java.JavaMappingFactory;
import com.intellij.openapi.project.Project;

/**
 * Created by vmansoori on 1/6/2016.
 */
public class MappingFactoryManager {
//    private static MappingFactoryManager ourInstance = new MappingFactoryManager(project);
    private Project project;

    public static MappingFactoryManager getInstance(Project project) {
        return new MappingFactoryManager(project);
    }

    private MappingFactoryManager(Project project) {
        this.project = project;
    }

    public MappingFactory getFactory(String factoryType) {
        // TODO: 1/6/2016 This method needs to return the correct mapping factory based on the language type passed in.
        return new JavaMappingFactory(this.project);
    }
}
