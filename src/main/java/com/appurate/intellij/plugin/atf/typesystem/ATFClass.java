package com.appurate.intellij.plugin.atf.typesystem;

import com.appurate.intellij.plugin.atf.typesystem.ATFReference;
import com.appurate.intellij.plugin.atf.typesystem.ATFType;
import com.intellij.psi.PsiMethod;

/**
 * Created by vmansoori on 1/6/2016.
 */
public interface ATFClass extends ATFType {
    void add(PsiMethod method);
    ATFReference getReferenceType();
    String getQualifiedName();

    ATFMethod[] getAllMethods();
    ATFProperty[] getAllProperties();
}
