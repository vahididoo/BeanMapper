package com.appurate.intellij.plugin.atf.typesystem;


import com.appurate.intellij.plugin.atf.typesystem.psi.impl.ATFPsiTypeFactory;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;

/**
 * Created by vmansoori on 1/5/2016.
 */
public interface ATFTypeFactory {

    boolean accept(String extension);

    boolean accept(VirtualFile file);

    boolean accept(PsiFile psiFile);

    ATFType getATFType(VirtualFile file);

    ATFType getATFType(PsiFile psiFile);

    ATFType getATFType(String qualifiedName);

    ATFType createType(VirtualFile parent, String name);

}
