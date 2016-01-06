package com.appurate.intellij.plugin.atf.mapping.java;

import com.appurate.intellij.plugin.atf.ExecutionUtil;
import com.appurate.intellij.plugin.atf.mapping.MappingClass;
import com.appurate.intellij.plugin.atf.mapping.MappingFactory;
import com.appurate.intellij.plugin.atf.mapping.MappingMethod;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.ThrowableComputable;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.impl.file.PsiDirectoryFactory;
import com.intellij.psi.impl.file.PsiJavaDirectoryFactory;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiClassUtil;
import com.intellij.psi.util.PsiUtil;

/**
 * Created by vmansoori on 1/6/2016.
 */
public class JavaMappingFactory implements MappingFactory {

    private Project project;
    private PsiElementFactory elementFactory;
    private PsiManager psiManager;

    public JavaMappingFactory(Project project) {
        this.project = project;
        elementFactory = JavaPsiFacade.getInstance(project).getElementFactory();
        psiManager = PsiManager.getInstance(project);
    }

    @Override
    public MappingClass createMapping(VirtualFile parent, String name) {
        if (!parent.isDirectory()) {
            return null;
        }
        MappingClass javaClass = null;
        PsiDirectoryFactory instance = PsiJavaDirectoryFactory.getInstance(project);
        PsiDirectory psiDir = instance.createDirectory(parent);
        if (instance.isPackage(psiDir)) {
            try {
                return ExecutionUtil.execute(new ThrowableComputable() {
                    @Override
                    public MappingClass compute() throws Throwable {
                        PsiPackage aPackage = JavaDirectoryService.getInstance().getPackage(psiDir);
                        String className = name;
                        PsiClass psiClass = JavaDirectoryService.getInstance().createClass(psiDir, className);
                        psiClass = createConstructore(psiClass);
                        return (new JavaMappingClass(psiClass));
                    }
                });
            } catch (Throwable throwable) {
                Logger.getInstance(this.getClass()).error("Error creating class " + name, throwable.getMessage());
            }
        }
        return javaClass;
    }

    private PsiClass createConstructore(PsiClass psiClass) {
        PsiClassType stringType = PsiType.getJavaLangString(psiManager, GlobalSearchScope.projectScope(project));
        PsiParameter caller = elementFactory.createParameter("caller", stringType);
        PsiMethod method = elementFactory.createConstructor();
        method.getParameterList().add(caller);
        psiClass.add(method);
        return psiClass;
    }

    @Override
    public MappingMethod createMappingMethod(Object... params) {
        return null;
    }
}
