package com.appurate.intellij.plugin.atf.typesystem.psi.impl;

import com.appurate.intellij.plugin.atf.typesystem.ATFType;
import com.appurate.intellij.plugin.atf.typesystem.ATFTypeCategory;
import com.appurate.intellij.plugin.atf.typesystem.psi.ATFPsiReference;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMember;

/**
 * Created by vmansoori on 1/2/2016.
 */
public abstract class ATFPsiTypeBase<T extends PsiMember, U extends ATFType> implements ATFType {

    protected final U parent;
    protected final T psiMember;

    public ATFPsiTypeBase(U parent, T psiMember) {
        this.psiMember = psiMember;
        this.parent = parent;
    }

    @Override
    public ATFTypeCategory getType() {
        //// TODO: 1/2/2016 This method should return the type category based on the type of the PsiMember passed in.
        return null;
    }

    @Override
    public T getBasedOn() {
        return this.psiMember;
    }

    @Override
    public U getParent() {
        return parent;
    }

    public ATFPsiReference getReferenceType() {
        if (psiMember instanceof PsiElement) {
            return new ATFPsiReferenceImpl(psiMember.getReference());
        }
        return null;
    }


}
