package com.appurate.intellij.plugin.atf.typesystem.psi.impl;

import com.appurate.intellij.plugin.atf.ExecutionUtil;
import com.appurate.intellij.plugin.atf.typesystem.ATFType;
import com.appurate.intellij.plugin.atf.typesystem.ATFTypeFactoryBase;
import com.appurate.intellij.plugin.atf.typesystem.psi.ATFPsiClass;
import com.appurate.intellij.plugin.atf.typesystem.psi.ATFPsiMethod;
import com.appurate.intellij.plugin.atf.typesystem.psi.ATFPsiParameter;
import com.appurate.intellij.plugin.atf.typesystem.psi.ATFPsiProperty;
import com.appurate.intellij.plugin.atf.typesystem.psi.impl.java.ATFJavaTypeFactory;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.util.ThrowableComputable;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.impl.file.PsiDirectoryFactory;
import com.intellij.psi.impl.file.PsiJavaDirectoryFactory;
import com.intellij.psi.util.PropertyUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by vmansoori on 1/9/2016.
 */
public abstract class ATFPsiTypeFactory extends ATFTypeFactoryBase {

    protected final PsiManager psiManager;
    protected final PsiElementFactory elementFactory;

    public ATFPsiTypeFactory(Project project) {
        super(project);
        psiManager = PsiManager.getInstance(project);
        elementFactory = PsiElementFactory.SERVICE.getInstance(project);
    }


    protected static ATFPsiProperty getATFPsiPropertyType(ATFPsiClassImpl owner, PsiField psiField) {
        List<PsiMethod> setters = PropertyUtil.getSetters((PsiClass) owner.getBasedOn(), psiField.getName());
        List<PsiMethod> getters = PropertyUtil.getGetters((PsiClass) owner.getBasedOn(), psiField.getName());
        // TODO: 1/2/2016 Find the property accessor and omit it if it's private or protected and doesn't have an
        // accessor
        return new ATFPsiPropertyImpl(owner, psiField, setters, getters);
    }

    @NotNull
    protected static ATFType getATFPsiType(ATFPsiClassImpl owner, PsiClass aClass) {
        return new ATFPsiClassImpl(aClass);
    }


    @Override
    public ATFType createType(VirtualFile parent, String name) {
        if (!parent.isDirectory()) {
            return null;
        }
        ATFType javaClass = null;
        PsiDirectoryFactory instance = PsiJavaDirectoryFactory.getInstance(this.project);
        final PsiDirectory psiDir = instance.createDirectory(parent);
        if (instance.isPackage(psiDir)) {
            try {
                ATFPsiClass atfType = (ATFPsiClass) createType(name, psiDir);
                createConstructor(atfType, null);
            } catch (Throwable throwable) {
                Logger.getInstance(this.getClass()).error("Error creating class " + name, throwable.getMessage());
            }
        }
        return javaClass;
    }

    private ATFType createType(final String name, final PsiDirectory psiDir) throws Throwable {
        ThrowableComputable computable = new ThrowableComputable() {
            @Override
            public ATFType compute() throws Throwable {
                PsiPackage aPackage = JavaDirectoryService.getInstance().getPackage(psiDir);
                String className = name;
                PsiClass psiClass = JavaDirectoryService.getInstance().createClass(psiDir, className);
                ATFPsiClassImpl aClass = new ATFPsiClassImpl(psiClass);

                return aClass;
            }
        };
        return ExecutionUtil.execute(computable);
    }

    private ATFPsiMethod createConstructor(com.appurate.intellij.plugin.atf.typesystem.psi.ATFPsiClass aClass,
                                           ATFPsiParameter... objects) {
        return createMethod(aClass, aClass.getName(), objects);
    }


    public ATFPsiMethod createMethod(@NotNull com.appurate.intellij.plugin.atf.typesystem.psi.ATFPsiClass parent,
                                     @NotNull String name, @Nullable ATFPsiParameter...
            params) {
        return this.createMethod(parent, name, null, params);
    }

    public ATFPsiMethod createMethod(@NotNull com.appurate.intellij.plugin.atf.typesystem.psi.ATFPsiClass parent,
                                     @NotNull String name, @Nullable ATFType returnType,
                                     @Nullable ATFPsiParameter... params) {
        PsiType psiReturnType = PsiPrimitiveType.VOID;// FIXME: 1/7/2016 Different return types need to be handled.
        // This only handles Void.
        Computable<PsiMethod> callable = new Computable<PsiMethod>() {
            @Override
            public PsiMethod compute() {
                PsiMethod method = elementFactory.createMethod(name, psiReturnType);
                if (params != null) {
                    for (ATFPsiParameter param : params) {
                        method.getParameterList().add(param.getBasedOn());
                    }
                }
                parent.add(method);
                return method;
            }
        };
        return new ATFPsiMethodImpl(parent, ExecutionUtil.execute(callable));
    }


    @Override
    public boolean accept(PsiFile psiFile) {
        return psiFile != null && accept(psiFile.getVirtualFile());
    }

    public ATFType getATFType(ATFPsiClassImpl owner, PsiMember psiMember) {
        // FIXME: 1/3/2016 Handle array type.
        if (psiMember instanceof PsiClass) {
            return ATFJavaTypeFactory.getATFPsiType(owner, (PsiClass) psiMember);
        } else if (psiMember instanceof PsiField) {
            if (((PsiField) psiMember).getType() instanceof PsiPrimitiveType) {
                return getATFPsiPropertyType(owner, (PsiField) psiMember);
            } else if (((PsiField) psiMember).getType() instanceof PsiClassType) {
                PsiClass psiClass = ((PsiClassType) ((PsiField) psiMember).getType()).resolve();
                if (psiClass.getQualifiedName().contains("java.lang")) {
                    return getATFPsiPropertyType(owner, (PsiField) psiMember);
                } else {
                    return ATFJavaTypeFactory.getATFPsiType(owner, psiClass);
                }
            }
        }
        return null;
    }
}
