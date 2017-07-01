package com.appurate.intellij.plugin.atf.typesystem;

import com.appurate.intellij.plugin.atf.typesystem.ATFType;
import com.appurate.intellij.plugin.atf.typesystem.ATFTypeCategory;
import com.intellij.psi.PsiMember;

/**
 * Created by vmansoori on 1/2/2016.
 */
public abstract class ATFJavaTypeBase<T extends PsiMember,U extends ATFType> implements ATFType {

    protected final U parent;
    protected final T psiMember;

    public ATFJavaTypeBase(U parent, T psiMember) {
        this.psiMember = psiMember;
        this.parent = parent;
    }


    public ATFType getType() {
        //// TODO: 1/2/2016 This method should return the type category based on the type of the PsiMember passed in.
        return null;
    }

    public T getBasedOn() {
        return this.psiMember;
    }

    @Override
    public U getParent() {
        return parent;
    }
}
