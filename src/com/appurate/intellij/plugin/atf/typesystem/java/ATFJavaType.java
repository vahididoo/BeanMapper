package com.appurate.intellij.plugin.atf.typesystem.java;

import com.appurate.intellij.plugin.atf.typesystem.ATFType;
import com.appurate.intellij.plugin.atf.typesystem.ATFTypeCategory;
import com.intellij.psi.*;
import com.intellij.psi.util.PropertyUtil;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Created by vmansoori on 12/31/2015.
 */
public class ATFJavaType extends ATFJavaTypeBase<PsiClass, ATFJavaType> {

    private HashMap<String, ATFType> properties;
    private PsiJavaFile javaFile;


    public ATFJavaType(ATFJavaType parent, PsiClass aClass) {
        super(parent, aClass);
        this.javaFile = (PsiJavaFile) aClass.getContainingFile();
        this.properties = getAllProperties();
    }

    public ATFJavaType(PsiClass aClass) {
        this(null, aClass);
    }

    @Override
    public ATFType[] getChildren() {
        return this.properties.values().toArray(new ATFType[this.properties.size()]);
    }


    private HashMap<String, ATFType> getAllProperties() {
        HashMap<String, ATFType> fieldHashMap = new HashMap<>();
        PsiField[] allFields = psiMember.getAllFields();
        for (PsiField aField : allFields) {
            fieldHashMap.put(aField.getName(), ATFJavaTypeAdapter.getATFType(this, aField));
        }
        return fieldHashMap;
    }


    public Map<String, PsiMethod> getSetters() {
        return getAccessorsByType(false, true);
    }

    @Nullable
    private Map<String, PsiMethod> getAccessorsByType(boolean acceptGetters, boolean acceptSetters) {
        if (javaFile.getClasses().length > 0) {
            PsiClass psiClass = javaFile.getClasses()[0];
            Map<String, PsiMethod> allAccessorsByType = PropertyUtil.getAllProperties(psiClass, acceptSetters,
                    acceptGetters, true);
            return allAccessorsByType;
        }
        return null;
    }

    public Map<String, PsiMethod> getGetters() {
        return getAccessorsByType(true, false);
    }

    @Override
    public ATFTypeCategory getType() {
        return ATFTypeCategory.COMPOSITE;
    }

    @Override
    public String getName() {
        return this.psiMember.getName();
    }

    @Override
    public String getDisplayName() {
        return this.getName();
    }

    @Override
    public String getPath() {
        return this.psiMember.getReference().getCanonicalText();
    }

}
