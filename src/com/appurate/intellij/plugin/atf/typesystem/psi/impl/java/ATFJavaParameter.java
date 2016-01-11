package com.appurate.intellij.plugin.atf.typesystem.psi.impl.java;

import com.appurate.intellij.plugin.atf.typesystem.psi.ATFPsiParameter;
import com.appurate.intellij.plugin.atf.typesystem.ATFType;
import com.appurate.intellij.plugin.atf.typesystem.psi.ATFPsiReference;
import com.appurate.intellij.plugin.common.util.CommonUtil;
import com.intellij.psi.*;

/**
 * Created by vmansoori on 1/7/2016.
 */
public class ATFJavaParameter implements ATFPsiParameter {

    private final String name;
    private final ATFPsiReference type;
    private final PsiParameter psiParameter;

    public ATFJavaParameter(String name, ATFPsiReference type) {
        this.name = name;
        this.type = type;
        psiParameter = PsiElementFactory.SERVICE.getInstance(CommonUtil.getActiveProject())
                .createParameterFromText(type.resolve().getName() + " " + name, null);

    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public ATFPsiReference getType() {
        return this.type;
    }

    public PsiParameter getBasedOn() {
        return psiParameter;
    }
}
