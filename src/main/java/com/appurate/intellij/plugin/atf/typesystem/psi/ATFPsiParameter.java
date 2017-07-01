package com.appurate.intellij.plugin.atf.typesystem.psi;

import com.appurate.intellij.plugin.atf.typesystem.*;
import com.intellij.psi.PsiElement;

/**
 * Created by vmansoori on 3/17/2016.
 */
public class ATFPsiParameter extends ATFPsiNameType implements ATFParameter {
    public ATFPsiParameter(PsiElement basedOn, ATFPsiType parent) {
        super(basedOn, parent);
    }

    @Override
    public ATFType[] getChildren() {
        return null;
    }
}
