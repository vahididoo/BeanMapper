package com.appurate.intellij.plugin.atf.typesystem;

import com.intellij.psi.PsiMethod;

/**
 * Created by vmansoori on 1/6/2016.
 */
public interface ATFClass extends ATFType{
    void add(PsiMethod method);
}
