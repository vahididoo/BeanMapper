package com.appurate.intellij.plugin.atf.typesystem.psi;

import com.appurate.intellij.plugin.atf.typesystem.ATFClass;
import com.appurate.intellij.plugin.atf.typesystem.ATFMethod;
import com.appurate.intellij.plugin.atf.typesystem.ATFProperty;
import com.appurate.intellij.plugin.atf.typesystem.ATFType;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.util.PropertyUtil;
import javafx.beans.property.Property;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by vmansoori on 3/17/2016.
 */
public class ATFPsiClass extends ATFPsiType implements ATFClass {
    public ATFPsiClass(PsiClass basedOn, ATFClass parent) {
        super(basedOn, parent);
    }

    @Override
    public void add(PsiMethod method) {

    }

    @Override
    public String getQualifiedName() {
        return null;
    }

    @Override
    public ATFMethod[] getAllMethods() {
        List<ATFMethod> methods = new ArrayList<>();
        for (PsiMethod method : ((PsiClass) this.getBasedOn()).getAllMethods()) {
            methods.add(new ATFPsiMethod(method, this));
        }
        return new ATFMethod[0];
    }

    @Override
    public ATFProperty[] getAllProperties() {
        return new ATFProperty[0];
    }

    @Override
    public ATFType[] getChildren() {
        String[] allProperties = PropertyUtil.getReadableProperties(((PsiClass) this.getBasedOn()), true);
        List<ATFPsiProperty> properties = new ArrayList<>();
        for (String psiField : allProperties) {
            PsiField propertyField = PropertyUtil.findPropertyField((PsiClass) this.basedOn, psiField, false);
            properties.add(new ATFPsiProperty(this, propertyField));

        }
        return new ATFType[0];
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return null;
    }
}
