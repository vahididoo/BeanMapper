package com.appurate.intellij.plugin.atf.typesystem;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;

/**
 * Created by vmansoori on 12/31/2015.
 */
public interface ATFTypeAdapter {

    boolean accept(String extension);

    boolean accept(VirtualFile file);

    boolean accept(PsiFile psiFile);

    ATFType getATFType(VirtualFile file);

    ATFType getATFType(PsiFile psiFile);

    ATFType getATFType(String qualifiedName);

//    ATFType getATFType(String path, String name);
}
