package com.appurate.intellij.plugin.atf.typesystem.psi;

import com.appurate.intellij.plugin.atf.typesystem.ATFReference;
import com.appurate.intellij.plugin.atf.typesystem.ATFType;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiReference;

/**
 * Created by vmansoori on 3/18/2016.
 */
public class ATFPsiReference implements ATFReference {

    private final PsiReference basedOn;
    private final ATFType parent;

    public ATFPsiReference(PsiReference basedOn, ATFType parent) {
        this.basedOn = basedOn;
        this.parent = parent;
    }

    public ATFPsiClass resolve() {
        return new ATFPsiClass(((PsiClass) basedOn.resolve()), null);
    }


    @Override
    public String getReferenceString() {
        return basedOn.getCanonicalText();
    }
}
