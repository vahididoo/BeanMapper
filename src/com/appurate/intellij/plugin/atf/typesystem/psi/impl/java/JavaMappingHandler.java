package com.appurate.intellij.plugin.atf.typesystem.psi.impl.java;

import com.appurate.intellij.plugin.atf.typesystem.psi.ATFPsiClass;
import com.appurate.intellij.plugin.atf.typesystem.psi.ATFPsiParameter;
import com.appurate.intellij.plugin.atf.typesystem.ATFTypeManager;
import com.appurate.intellij.plugin.atf.typesystem.psi.ATFPsiProperty;
import com.intellij.openapi.project.Project;

/**
 * Created by vmansoori on 1/7/2016.
 */
public class JavaMappingHandler {

    private final ATFPsiClass _mappingClass;
    private final Project project;
    private final ATFJavaTypeFactory typeFactory;


    public JavaMappingHandler(Project project, ATFPsiClass mappingClass) {
        this._mappingClass = mappingClass;
        this.project = project;
        typeFactory = (ATFJavaTypeFactory) ATFTypeManager.getInstance(this.project).getTypeFactory("java");
    }

    public void createOrUpdateMappingMethod(String name, ATFPsiParameter... params) {
        typeFactory.createMethod(_mappingClass, name, params);
    }

    public void createOrUpdateMappingMethod(String name, ATFPsiProperty... properties) {
        ATFPsiParameter[] params = new ATFPsiParameter[properties.length];
        for (int i = 0; i < properties.length; i++) {
            params[i] = typeFactory.getParameterFromProperty(properties[i]);
        }
        typeFactory.createMethod(_mappingClass, name, params);
    }


}
