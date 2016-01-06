package com.appurate.intellij.plugin.atf.mapping.java;

import com.appurate.intellij.plugin.atf.mapping.MappingClass;
import com.intellij.psi.PsiClass;


/**
 * Created by vmansoori on 1/6/2016.
 */
public class JavaMappingClass implements MappingClass{

    public JavaMappingClass(PsiClass _basedOn) {
        this._basedOn = _basedOn;
    }

    private PsiClass _basedOn;
}
