package com.appurate.intellij.plugin.atf.editor.typesystem.java;

import com.appurate.intellij.plugin.atf.editor.typesystem.ATFType;
import com.appurate.intellij.plugin.atf.editor.typesystem.ATFTypeAdapterBase;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PropertyUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by vmansoori on 12/31/2015.
 */
public class ATFJavaTypeAdapter extends ATFTypeAdapterBase {


    public ATFJavaTypeAdapter(Project project) {
        super(project);
    }

    @NotNull
    static ATFType getGtxJavaType(ATFJavaType owner, PsiClass aClass) {
        return new ATFJavaType(aClass);
    }

    public static ATFType getGtxType(ATFJavaType owner, PsiMember psiMember) {
        // FIXME: 1/3/2016 Handle array type.
        if (psiMember instanceof PsiClass) {
            return getGtxJavaType(owner, (PsiClass) psiMember);
        }else if (psiMember instanceof PsiField) {
            if(((PsiField) psiMember).getType() instanceof PsiPrimitiveType) {
                return getGtxJavaPropertyType(owner, (PsiField) psiMember);
            }else if(((PsiField) psiMember).getType() instanceof PsiClassType){
                PsiClass psiClass = ((PsiClassType) ((PsiField) psiMember).getType()).resolve();
                if(psiClass.getQualifiedName().contains("java.lang")){
                    return getGtxJavaPropertyType(owner, (PsiField) psiMember);
                }else {
                    return getGtxJavaType(owner, psiClass);
                }
            }
        }
        return null;
    }

    private static ATFType getGtxJavaPropertyType(ATFJavaType owner, PsiField psiField) {
        List<PsiMethod> setters = PropertyUtil.getSetters((PsiClass) owner.getBasedOn(), psiField.getName());
        List<PsiMethod> getters = PropertyUtil.getGetters((PsiClass) owner.getBasedOn(), psiField.getName());
        // TODO: 1/2/2016 Find the property accessor and omit it if it's private or protected and doesn't have an accessor
        return new ATFJavaPropertyType(owner, psiField, setters, getters);
    }

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
    public ATFType getGtxType(VirtualFile file) {
        PsiFile[] javaFiles = FilenameIndex.getFilesByName(getProject(), file.getName(), GlobalSearchScope.projectScope(getProject()));
        if (javaFiles.length > 0 && ((PsiJavaFile) javaFiles[0]).getClasses().length > 0) {
            return new ATFJavaType(((PsiJavaFile) javaFiles[0]).getClasses()[0]);
        }
        return null;
    }

    @Override
    public ATFType getGtxType(PsiFile psiFile) {
        if (psiFile.getFileType().equals(JavaFileType.INSTANCE) && ((PsiJavaFile) psiFile).getClasses().length > 0) {
            return new ATFJavaType(((PsiJavaFile) psiFile).getClasses()[0]);

        }
        return null;
    }

    @Override
    public ATFType getGtxType(String qualifiedName) {
        JavaPsiFacade instance = JavaPsiFacade.getInstance(getProject());
        PsiClass[] classes = instance.findClasses(qualifiedName, GlobalSearchScope.projectScope(getProject()));
        if (classes.length > 0) {
            return new ATFJavaType(classes[0]);
        }
        return null;
    }

}
