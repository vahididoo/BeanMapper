package com.appurate.intellij.plugin.atf.typesystem;

import com.intellij.psi.PsiMember;

/**
 * Created by vmansoori on 12/30/2015.
 */
public interface ATFType {
    ATFType[] getChildren();
    ATFType getParent();
    ATFTypeCategory getType();
    String getName();
    String getDisplayName();
    String getPath();
    PsiMember getBasedOn();
    String toString();


}
