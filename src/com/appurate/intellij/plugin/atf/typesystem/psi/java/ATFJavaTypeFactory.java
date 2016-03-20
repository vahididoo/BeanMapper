package com.appurate.intellij.plugin.atf.typesystem.psi.java;

import com.appurate.intellij.plugin.atf.typesystem.ATFMethod;
import com.appurate.intellij.plugin.atf.typesystem.ATFParameter;
import com.appurate.intellij.plugin.atf.typesystem.ATFProperty;
import com.appurate.intellij.plugin.atf.typesystem.ATFType;
import com.appurate.intellij.plugin.atf.typesystem.psi.ATFPsiClass;
import com.appurate.intellij.plugin.atf.typesystem.psi.ATFPsiTypeFactory;
import com.intellij.lang.java.parser.JavaParserUtil;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiJavaParserFacade;
import com.intellij.psi.impl.PsiJavaParserFacadeImpl;
import com.intellij.psi.impl.file.impl.FileManager;
import com.intellij.psi.impl.file.impl.JavaFileManager;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;

/**
 * Created by vmansoori on 3/19/2016.
 */
public class ATFJavaTypeFactory extends ATFPsiTypeFactory {
    public ATFJavaTypeFactory(Project project) {
        super(project);
    }

    @Override
    public ATFType getATFType(String qualifiedName) {
        PsiClass psiClass = JavaPsiFacade.getInstance(project).findClass(qualifiedName, GlobalSearchScope.projectScope(project));
        return new ATFPsiClass(psiClass,null);
    }

    @Override
    public ATFMethod getATFMethod(@NotNull Object element) {
        return null;
    }

    @Override
    public ATFProperty getATFProperty(@NotNull Object element) {
        return null;
    }

    @Override
    public ATFParameter getATFParameter(@NotNull Object element) {
        return null;
    }

    @Override
    public ATFParameter createParameter(@NotNull ATFMethod parent, @NotNull Object param) {
        return null;
    }
}
