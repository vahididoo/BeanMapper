package com.appurate.intellij.plugin.atf.editor.typesystem;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;

/**
 * Created by vmansoori on 12/31/2015.
 */
public interface ATFTypeAdapter {

    boolean accept(String extension);

    boolean accept(VirtualFile file);

    boolean accept(PsiFile psiFile);

    ATFType getGtxType(VirtualFile file);

    ATFType getGtxType(PsiFile psiFile);

    ATFType getGtxType(String qualifiedName);

//    ATFType getGtxType(String path, String name);
}
