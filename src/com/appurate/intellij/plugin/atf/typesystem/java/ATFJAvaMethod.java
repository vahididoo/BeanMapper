package com.appurate.intellij.plugin.atf.typesystem.java;

import com.appurate.intellij.plugin.atf.typesystem.ATFMethod;
import com.appurate.intellij.plugin.atf.typesystem.ATFType;
import com.intellij.psi.PsiMethod;

/**
 * Created by vmansoori on 1/6/2016.
 */
public class ATFJavaMethod extends ATFJavaTypeBase<PsiMethod, ATFJavaClass> implements ATFMethod{
    public ATFJavaMethod(ATFJavaClass parent, PsiMethod psiMember) {
        super(parent, psiMember);
    }

    @Override
    public ATFType[] getChildren() {
        return new ATFType[0];
    }

    @Override
    public String getName() {
        return psiMember.getName();
    }

    @Override
    public String getDisplayName() {
        return getName();
    }

    @Override
    public String getPath() {
        return null;
    }
}
