package com.appurate.intellij.plugin.atf.typesystem.psi;

import com.intellij.psi.PsiElement;

/**
 * Created by vmansoori on 3/17/2016.
 */
public abstract class ATFPsiNameType extends ATFPsiType {
    public ATFPsiNameType(PsiElement basedOn, ATFPsiType parent) {
        super(basedOn, parent);
    }

    public String getName() {
        return null;
    }

    public String getDisplayName() {
        return null;
    }
}
