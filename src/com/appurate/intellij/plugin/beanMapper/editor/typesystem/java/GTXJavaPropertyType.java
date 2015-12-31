package com.appurate.intellij.plugin.beanMapper.editor.typesystem.java;

import com.appurate.intellij.plugin.beanMapper.editor.typesystem.GTXType;
import com.appurate.intellij.plugin.beanMapper.editor.typesystem.GTXTypeCategory;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.util.MethodSignatureUtil;
import com.intellij.psi.util.PropertyUtil;

/**
 * Created by vmansoori on 12/31/2015.
 */
public class GTXJavaPropertyType implements GTXType{

    private PsiMethod propertyGetter;
    private PsiMethod propertySetter;

    public GTXJavaPropertyType(PsiMethod propertyGetter, PsiMethod propertySetter) {
        this.propertyGetter = propertyGetter;
        this.propertySetter = propertySetter;
    }

    @Override
    public GTXType[] getChildren() {
        PropertyUtil.getPropertyType(propertySetter);
        return new GTXType[0];
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
