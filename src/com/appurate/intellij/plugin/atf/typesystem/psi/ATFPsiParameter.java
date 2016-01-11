package com.appurate.intellij.plugin.atf.typesystem.psi;

import com.intellij.psi.PsiElement;

/**
 * Created by vmansoori on 1/7/2016.
 */
public interface ATFPsiParameter {

    String getName();

    ATFPsiReference getType();

    PsiElement getBasedOn();
}
