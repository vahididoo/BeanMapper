package com.appurate.intellij.plugin.atf.typesystem.psi.impl.java;

import com.appurate.intellij.plugin.atf.typesystem.psi.impl.ATFPsiClassImpl;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiJavaFile;

/**
 * Created by vmansoori on 1/10/2016.
 */
public class ATFJavaClass extends ATFPsiClassImpl {
    private PsiJavaFile javaFile;

    public ATFJavaClass(ATFPsiClassImpl parent, PsiClass aClass) {
        super(parent, aClass);
        this.javaFile = (PsiJavaFile) aClass.getContainingFile();
    }

    public ATFJavaClass(PsiClass aClass) {
        this(null, aClass);
    }
}
