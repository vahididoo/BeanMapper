package com.appurate.intellij.plugin.atf.typesystem.psi;

import com.appurate.intellij.plugin.atf.typesystem.ATFMethod;
import com.appurate.intellij.plugin.atf.typesystem.ATFParameter;
import com.appurate.intellij.plugin.atf.typesystem.ATFType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vmansoori on 3/17/2016.
 */
public class ATFPsiMethod extends ATFPsiNameType implements ATFMethod {


    public ATFPsiMethod(PsiElement basedOn, ATFPsiClass parent) {
        super(basedOn, parent);
    }

    @Override
    public ATFType[] getChildren() {
        return null;
    }

    @Override
    public ATFParameter[] getParameters() {
        List<ATFParameter> parameterList = new ArrayList<>();
        for (PsiParameter psiParameter : ((PsiMethod) getBasedOn()).getParameterList().getParameters()) {
            parameterList.add(new ATFPsiParameter(psiParameter, this));
        }
        return parameterList.toArray(new ATFParameter[0]);
    }

    @Override
    public ATFType getReturnType() {
//        ((PsiMethod) getBasedOn()).getReturnType()
        return null;
    }
}
