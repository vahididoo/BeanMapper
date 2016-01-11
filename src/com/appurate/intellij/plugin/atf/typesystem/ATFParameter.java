package com.appurate.intellij.plugin.atf.typesystem;

import com.intellij.psi.PsiParameter;

/**
 * Created by vmansoori on 1/7/2016.
 */
public interface ATFParameter {

    String getName();

    ATFType getType();
}
