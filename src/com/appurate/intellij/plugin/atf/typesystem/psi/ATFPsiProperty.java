package com.appurate.intellij.plugin.atf.typesystem.psi;

import com.appurate.intellij.plugin.atf.typesystem.ATFType;
import com.appurate.intellij.plugin.atf.typesystem.psi.impl.ATFPsiTypeBase;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiMember;

/**
 * Created by vmansoori on 1/6/2016.
 */
public interface ATFPsiProperty extends ATFType {


    ATFPsiReference getReferenceType();
}
