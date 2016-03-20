package com.appurate.intellij.plugin.atf.typesystem.psi;

import com.appurate.intellij.plugin.atf.typesystem.ATFReference;
import com.appurate.intellij.plugin.atf.typesystem.ATFType;
import com.intellij.psi.PsiElement;

/**
 * Created by vmansoori on 3/17/2016.
 */
public abstract class ATFPsiType implements ATFType {
    protected final ATFType parent;
    protected PsiElement basedOn;

    public ATFPsiType(PsiElement basedOn, ATFType parent) {
        this.basedOn = basedOn;
        this.parent = parent;
    }

    public PsiElement getBasedOn() {
        return basedOn;
    }

    public ATFReference getReferenceType() {

        return new ATFPsiReference(basedOn.getReference(), this.getParent());
    }


    @Override
    public ATFType getParent() {
        return parent;
    }
}
