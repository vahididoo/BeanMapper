package com.appurate.intellij.plugin.atf.typesystem.psi.impl;

import com.appurate.intellij.plugin.atf.typesystem.psi.ATFPsiClass;
import com.appurate.intellij.plugin.atf.typesystem.psi.ATFPsiProperty;
import com.appurate.intellij.plugin.atf.typesystem.ATFType;
import com.appurate.intellij.plugin.atf.typesystem.ATFTypeCategory;
import com.intellij.psi.*;

import java.util.List;

/**
 * Created by vmansoori on 12/31/2015.
 */
public class ATFPsiPropertyImpl extends ATFPsiTypeBase<PsiField, ATFPsiClass> implements ATFPsiProperty {

    private final List<PsiMethod> getters;
    private final List<PsiMethod> setters;


    public ATFPsiPropertyImpl(ATFPsiClass parent, PsiField aField, List<PsiMethod> setters, List<PsiMethod> getters) {
        super(parent, aField);
        this.getters = getters;
        this.setters = setters;
    }

    @Override
    public ATFType[] getChildren() {
/*
        PsiType propertyType = this.getBasedOn().getType();
        ATFType[] atfTypes = new ATFType[0];
        if (this.getType().equals(ATFTypeCategory.COMPOSITE)) {
            atfTypes = new ATFType[]{new ATFPsiClassImpl(((PsiClassType) propertyType).resolve())};
        }
        return atfTypes;
*/
        return new ATFType[0];
    }


    @Override
    public ATFTypeCategory getType() {
        PsiType propertyType = this.getBasedOn().getType();
        if (propertyType instanceof PsiPrimitiveType || propertyType instanceof PsiArrayType) {
            return ATFTypeCategory.SIMPLE;
        } else if (propertyType instanceof PsiClassType) {
            return ATFTypeCategory.COMPOSITE;
        }
        return null;
    }

    @Override
    public String getName() {
        return this.getBasedOn().getName();
    }

    @Override
    public String getDisplayName() {
        return this.getBasedOn().getName();
    }

    @Override
    public String getPath() {
        return null;
    }


}
