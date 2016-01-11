package com.appurate.intellij.plugin.atf.typesystem.psi.impl;

import com.appurate.intellij.plugin.atf.typesystem.ATFTypeManager;
import com.appurate.intellij.plugin.atf.typesystem.psi.ATFPsiClass;
import com.appurate.intellij.plugin.atf.typesystem.psi.ATFPsiReference;
import com.appurate.intellij.plugin.common.util.CommonUtil;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiReference;

/**
 * Created by vmansoori on 1/9/2016.
 */
public class ATFPsiReferenceImpl implements ATFPsiReference {

    private PsiReference basedOn;

    public ATFPsiReferenceImpl(PsiReference basedOn) {
        this.basedOn = basedOn;
    }

    @Override
    public ATFPsiClass resolve() {
        PsiClass psiClass = (PsiClass) basedOn.resolve();
        return (ATFPsiClassImpl) ((ATFPsiTypeFactory) ATFTypeManager.getInstance(CommonUtil.getActiveProject())
                .getTypeFactory(ATFTypeManager.LANGUAGE_JAVA))
                .getATFType(null, psiClass);
    }

    @Override
    public String getReferenceString() {
        return basedOn.getCanonicalText();
    }
}
