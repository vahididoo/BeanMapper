package com.appurate.intellij.plugin.experimental;

import com.intellij.psi.PsiClass;
import com.intellij.psi.impl.JavaPsiFacadeImpl;
import com.intellij.psi.impl.source.PsiClassImpl;

/**
 * Created by vmansoori on 1/11/2016.
 */
public class TestPsiGeneration {

    public static void main(String[] args) {
        PsiClass psiClass = JavaPsiFacadeImpl.getElementFactory(null).createClass("Vahid");
    }
}
