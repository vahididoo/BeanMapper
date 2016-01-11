package com.appurate.intellij.plugin.atf.typesystem.psi.impl;

import com.appurate.intellij.plugin.atf.typesystem.ATFType;
import com.appurate.intellij.plugin.atf.typesystem.psi.ATFPsiClass;
import com.intellij.psi.PsiMethod;

/**
 * Created by vmansoori on 1/6/2016.
 */
public class ATFPsiMethodImpl extends ATFPsiTypeBase<PsiMethod, ATFPsiClass> implements com.appurate.intellij.plugin.atf
        .typesystem.psi.ATFPsiMethod {
    public ATFPsiMethodImpl(ATFPsiClass parent, PsiMethod psiMember) {
        super(parent,psiMember);
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
