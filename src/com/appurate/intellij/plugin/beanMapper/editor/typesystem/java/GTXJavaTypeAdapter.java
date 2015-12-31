package com.appurate.intellij.plugin.beanMapper.editor.typesystem.java;

import com.appurate.intellij.plugin.beanMapper.editor.typesystem.GTXType;
import com.appurate.intellij.plugin.beanMapper.editor.typesystem.GTXTypeAdapter;
import com.appurate.intellij.plugin.common.util.CommonUtil;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.UserDataCache;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.impl.file.PsiFileImplUtil;
import com.intellij.psi.impl.file.PsiPackageImpl;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PackageScope;
import com.intellij.psi.util.ClassUtil;
import com.intellij.psi.util.PsiUtil;
import com.intellij.util.PathUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * Created by vmansoori on 12/31/2015.
 */
public class GTXJavaTypeAdapter implements GTXTypeAdapter {


    @Override
    public boolean accept(String extension) {
        return extension != null && extension.equals("java");
    }

    @Override
    public boolean accept(VirtualFile file) {
        return file != null && accept(file.getExtension());
    }

    @Override
    public boolean accept(PsiFile psiFile) {
        return psiFile != null && accept(psiFile.getVirtualFile());
    }

    @Override
    public GTXType getGtxType(VirtualFile file) {
        return null;
    }

    @Override
    public GTXType getGtxType(PsiFile psiFile) {
        if (psiFile.getFileType().equals(JavaFileType.INSTANCE)) {
            return new GTXJavaType((PsiJavaFile) psiFile);
        }
        return null;
    }

    @Override
    public GTXType getGtxType(String qualifiedName) {
        String className = ClassUtil.extractClassName(qualifiedName);
        String packageName = ClassUtil.extractPackageName(qualifiedName);
        return getGtxType(packageName, className);
    }

    @Override
    public GTXType getGtxType(String path, String name) {
        PsiPackage psiPackage = new PsiPackageImpl(PsiManager.getInstance(this.getProject()), path);
        Collection<VirtualFile> virtualFiles = FilenameIndex.getVirtualFilesByName(this.getProject(), name, new PackageScope(psiPackage, false, true));
        if (virtualFiles.size() > 0) {
            VirtualFile file = virtualFiles.iterator().next();
            PsiFile psiFile = PsiManager.getInstance(this.getProject()).findFile(file);
            if (psiFile.getFileType().equals(JavaFileType.INSTANCE)) {
                return new GTXJavaType((PsiJavaFile) psiFile);
            }
        }
        return null;
    }

    public Project getProject() {
        return CommonUtil.getActiveProject();
    }
}
