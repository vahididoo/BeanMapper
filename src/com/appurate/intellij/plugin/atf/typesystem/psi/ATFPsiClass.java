package com.appurate.intellij.plugin.atf.typesystem.psi;

import com.appurate.intellij.plugin.atf.typesystem.ATFType;
import com.intellij.psi.PsiMethod;

/**
 * Created by vmansoori on 1/6/2016.
 */
public interface ATFPsiClass extends ATFType {
    void add(PsiMethod method);

}
