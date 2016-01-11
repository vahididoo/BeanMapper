package com.appurate.intellij.plugin.atf.typesystem.psi;

/**
 * Created by vmansoori on 1/9/2016.
 */
public interface ATFPsiReference {
    ATFPsiClass resolve();

    String getReferenceString();

}
