package com.appurate.intellij.plugin.atf.typesystem.psi.impl.java;

import com.appurate.intellij.plugin.atf.typesystem.ATFType;
import com.appurate.intellij.plugin.atf.typesystem.psi.ATFPsiProperty;
import com.appurate.intellij.plugin.atf.typesystem.psi.impl.ATFPsiTypeFactory;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;

/**
 * Created by vmansoori on 12/31/2015.
 */
public class ATFJavaTypeFactory extends ATFPsiTypeFactory {

    private final PsiManager psiManager;

    public ATFJavaTypeFactory(Project project) {
        super(project);
        psiManager = PsiManager.getInstance(project);
    }

    @Override
    public boolean accept(String extension) {
        return extension != null && extension.equals("java");
    }

    @Override
    public ATFType getATFType(VirtualFile file) {
        PsiFile[] javaFiles = FilenameIndex.getFilesByName(getProject(), file.getName(), GlobalSearchScope
                .projectScope(getProject()));
        if (javaFiles.length > 0 && ((PsiJavaFile) javaFiles[0]).getClasses().length > 0) {
            return getATFPsiType(null, ((PsiJavaFile) javaFiles[0]).getClasses()[0]);
        }
        return null;
    }

    @Override
    public ATFType getATFType(PsiFile psiFile) {
        if (psiFile.getFileType().equals(JavaFileType.INSTANCE) && ((PsiJavaFile) psiFile).getClasses().length > 0) {
            return getATFPsiType(null, ((PsiJavaFile) psiFile).getClasses()[0]);

        }
        return null;
    }

    @Override
    public ATFType getATFType(String qualifiedName) {
        JavaPsiFacade instance = JavaPsiFacade.getInstance(getProject());
        PsiClass[] classes = instance.findClasses(qualifiedName, GlobalSearchScope.projectScope(getProject()));
        if (classes.length > 0) {
            return getATFPsiType(null, classes[0]);
        }
        return null;
    }

    public ATFJavaParameter getParameterFromProperty(ATFPsiProperty source) {
        return new ATFJavaParameter(source.getName(), source.getReferenceType());
    }
}
