package com.appurate.intellij.plugin.atf.typesystem.java;

import com.appurate.intellij.plugin.atf.typesystem.ATFParameter;
import com.appurate.intellij.plugin.atf.typesystem.ATFType;
import com.appurate.intellij.plugin.common.util.CommonUtil;
import com.intellij.psi.*;

/**
 * Created by vmansoori on 1/7/2016.
 */
public class ATFJavaParameter implements ATFParameter {

    private final String name;
    private final ATFType type;
    private final PsiParameter psiParameter;

    public ATFJavaParameter(ATFType parent, String name, ATFType type) {
        this.name = name;
        this.type = type;
        psiParameter = PsiElementFactory.SERVICE.getInstance(CommonUtil.getActiveProject())
                .createParameterFromText("String", parent
                        .getBasedOn());

    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public ATFType getType() {
        return this.type;
    }

    public PsiParameter getBasedOn() {
        return psiParameter;
    }
}
