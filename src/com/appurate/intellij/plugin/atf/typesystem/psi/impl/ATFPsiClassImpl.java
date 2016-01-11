package com.appurate.intellij.plugin.atf.typesystem.psi.impl;

import com.appurate.intellij.plugin.atf.typesystem.ATFTypeManager;
import com.appurate.intellij.plugin.atf.typesystem.ATFType;
import com.appurate.intellij.plugin.atf.typesystem.ATFTypeCategory;
import com.appurate.intellij.plugin.atf.typesystem.psi.ATFPsiClass;
import com.appurate.intellij.plugin.atf.typesystem.psi.ATFPsiReference;
import com.appurate.intellij.plugin.atf.typesystem.psi.impl.java.ATFJavaTypeFactory;
import com.appurate.intellij.plugin.common.util.CommonUtil;
import com.intellij.psi.*;
import com.intellij.psi.util.PropertyUtil;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Created by vmansoori on 12/31/2015.
 */
public class ATFPsiClassImpl extends ATFPsiTypeBase<PsiClass, ATFPsiClassImpl> implements ATFPsiClass {


    private HashMap<String, ATFType> properties;


    public ATFPsiClassImpl(ATFPsiClassImpl parent, PsiClass aClass) {
        super(parent, aClass);
        this.properties = getAllProperties();
    }

    public ATFPsiClassImpl(PsiClass aClass) {
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
            fieldHashMap.put(aField.getName(), ((ATFJavaTypeFactory) ATFTypeManager.getInstance(CommonUtil
                    .getActiveProject())
                    .getTypeFactory("java")).getATFType
                    (this, aField));
        }
        return fieldHashMap;
    }


    public Map<String, PsiMethod> getSetters() {
        return getAccessorsByType(false, true);
    }

    @Nullable
    private Map<String, PsiMethod> getAccessorsByType(boolean acceptGetters, boolean acceptSetters) {
        Map<String, PsiMethod> allAccessorsByType = PropertyUtil.getAllProperties(psiMember, acceptSetters,
                acceptGetters, true);
        return allAccessorsByType;
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
        if (this.psiMember != null && this.psiMember.getReference() != null) {
            return this.psiMember.getReference().getCanonicalText();
        }
        return null;
    }

    @Override
    public void add(PsiMethod method) {
        this.psiMember.add(method);
    }

    @Override
    public ATFPsiReference getReferenceType() {
        PsiReference reference = this.getBasedOn().getReference();
        ATFPsiReference atfReference = new ATFPsiReferenceImpl(reference);
        return null;
    }
}
