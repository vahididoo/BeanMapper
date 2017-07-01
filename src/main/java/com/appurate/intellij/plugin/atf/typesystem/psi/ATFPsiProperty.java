package com.appurate.intellij.plugin.atf.typesystem.psi;

import com.appurate.intellij.plugin.atf.typesystem.*;
import com.intellij.psi.*;
import com.intellij.psi.util.PropertyUtil;

/**
 * Created by vmansoori on 3/17/2016.
 */
public class ATFPsiProperty extends ATFPsiType implements ATFProperty {

    private final PsiMethod propertyGetter;
    private final PsiMethod propertySetter;
    private boolean isReadable;
    private boolean isWritable;

    public ATFPsiProperty(ATFPsiClass parent, PsiField psiField) {
        super(psiField, parent);
        PsiClass psiClass = (PsiClass) parent.getBasedOn();
        propertyGetter = PropertyUtil.findPropertyGetter(psiClass, psiField.getName(), true, true);
        propertySetter = PropertyUtil.findPropertySetter(psiClass, psiField.getName(), true, true);
    }

    public ATFPsiProperty(PsiField basedOn, ATFPsiClass parent, boolean isReadable, boolean isWritable) {
        this(parent, basedOn);
        this.isReadable = isReadable;
        this.isWritable = isWritable;
    }

    @Override
    public boolean isWritable() {
        return this.isWritable || this.getGetter() != null || ((PsiField) this.getBasedOn()).getModifierList().textMatches(PsiModifier.PUBLIC);
    }

    @Override
    public ATFMethod getGetter() {
        return new ATFPsiMethod(this.propertyGetter, ((ATFPsiClass) this.getParent()));
    }

    @Override
    public String getName() {
        return ((PsiField) basedOn).getName();
    }

    @Override
    public String getDisplayName() {
        return getName();
    }


    @Override
    public ATFMethod getSetter() {
        return new ATFPsiMethod(this.propertySetter, ((ATFPsiClass) this.getParent()));
    }

    @Override
    public ATFType[] getChildren() {
        throw new RuntimeException("Not Implemented");
    }

    @Override
    public ATFType getParent() {
        return parent;
    }

    @Override
    public ATFPsiType getType() {

        return null;
    }


}
