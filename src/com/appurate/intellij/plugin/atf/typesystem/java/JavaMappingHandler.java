package com.appurate.intellij.plugin.atf.typesystem.java;

import com.appurate.intellij.plugin.atf.typesystem.ATFClass;
import com.appurate.intellij.plugin.atf.typesystem.ATFProperty;
import com.appurate.intellij.plugin.atf.typesystem.ATFTypeFactory;
import com.appurate.intellij.plugin.atf.typesystem.ATFTypeManager;
import com.intellij.openapi.project.Project;

/**
 * Created by vmansoori on 1/7/2016.
 */
public class JavaMappingHandler {

    private final ATFClass _mappingClass;
    private final Project project;
    private final ATFJavaTypeFactory typeFactory;


    public JavaMappingHandler(Project project, ATFClass mappingClass) {
        this._mappingClass = mappingClass;
        this.project = project;
        typeFactory = (ATFJavaTypeFactory) ATFTypeManager.getInstance(this.project).getTypeFactory("java");
    }

    public void createOrUpdateMappingMethod(String name, ATFProperty... params) {
        typeFactory.createMethod(_mappingClass,name,params);
    }
}
