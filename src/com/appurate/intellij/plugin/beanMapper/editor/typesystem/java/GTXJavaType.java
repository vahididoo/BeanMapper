package com.appurate.intellij.plugin.beanMapper.editor.typesystem.java;

import com.appurate.intellij.plugin.beanMapper.editor.typesystem.GTXType;
import com.appurate.intellij.plugin.beanMapper.editor.typesystem.GTXTypeCategory;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.util.PropertyUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vmansoori on 12/31/2015.
 */
public class GTXJavaType implements GTXType {

    private Map<String, PsiMethod> getters;
    private Map<String, PsiMethod> properties;
    private PsiJavaFile javaFile;
    private Map<String, PsiMethod> setters;

    public GTXJavaType(@NotNull String className) {
        PsiFile[] psiFiles = FilenameIndex.getFilesByName(null, null, null);
        PsiFile psiFile;
        if (psiFiles.length > 0) {
            psiFile = psiFiles[0];
        }
    }


    public GTXJavaType(PsiJavaFile javaFile) {
        this.javaFile = javaFile;
        this.properties = this.getAllProperties();
        this.getters = this.getGetters();
        this.setters = this.getSetters();
    }

    @Override
    public GTXType[] getChildren() {
        return new GTXType[0];
    }

    private Map<String, PsiMethod> getAllProperties() {

        return new HashMap<>();
    }

    public Map<String, PsiMethod> getSetters() {
        return getAccessorsByType(false, true);
    }

    @Nullable
    private Map<String, PsiMethod> getAccessorsByType(boolean acceptGetters, boolean acceptSetters) {
        if (javaFile.getClasses().length > 0) {
            PsiClass psiClass = javaFile.getClasses()[0];
            Map<String, PsiMethod> allAccessorsByType = PropertyUtil.getAllProperties(psiClass, acceptSetters, acceptGetters, true);
            return allAccessorsByType;
        }
        return null;
    }

    public Map<String, PsiMethod> getGetters() {
        return getAccessorsByType(true, false);
    }

    @Override
    public GTXType getParent() {
        return null;
    }

    @Override
    public GTXTypeCategory getType() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public String getPath() {
        return null;
    }
}
