package com.appurate.intellij.plugin.beanMapper.editor.typesystem;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;

/**
 * Created by vmansoori on 12/31/2015.
 */
public interface GTXTypeAdapter {

    boolean accept(String extension);

    boolean accept(VirtualFile file);

    boolean accept(PsiFile psiFile);

    GTXType getGtxType(VirtualFile file);

    GTXType getGtxType(PsiFile psiFile);

    GTXType getGtxType(String qualifiedName);

    GTXType getGtxType(String path, String name);
}
