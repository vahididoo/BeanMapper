package com.appurate.intellij.plugin.atf.typesystem.java;

import com.appurate.intellij.plugin.atf.ExecutionUtil;
import com.appurate.intellij.plugin.atf.typesystem.*;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.util.ThrowableComputable;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.impl.file.PsiDirectoryFactory;
import com.intellij.psi.impl.file.PsiJavaDirectoryFactory;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PropertyUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by vmansoori on 12/31/2015.
 */
public class ATFJavaTypeFactory extends ATFTypeFactoryBase {

    private final PsiManager psiManager;
    private final PsiElementFactory elementFactory;

    public ATFJavaTypeFactory(Project project) {
        super(project);
        psiManager = PsiManager.getInstance(project);
        elementFactory = PsiElementFactory.SERVICE.getInstance(project);
    }

    @NotNull
    static ATFType getATFJavaType(ATFJavaClass owner, PsiClass aClass) {
        return new ATFJavaClass(aClass);
    }

    public static ATFType getATFType(ATFJavaClass owner, PsiMember psiMember) {
        // FIXME: 1/3/2016 Handle array type.
        if (psiMember instanceof PsiClass) {
            return getATFJavaType(owner, (PsiClass) psiMember);
        } else if (psiMember instanceof PsiField) {
            if (((PsiField) psiMember).getType() instanceof PsiPrimitiveType) {
                return getATFJavaPropertyType(owner, (PsiField) psiMember);
            } else if (((PsiField) psiMember).getType() instanceof PsiClassType) {
                PsiClass psiClass = ((PsiClassType) ((PsiField) psiMember).getType()).resolve();
                if (psiClass.getQualifiedName().contains("java.lang")) {
                    return getATFJavaPropertyType(owner, (PsiField) psiMember);
                } else {
                    return getATFJavaType(owner, psiClass);
                }
            }
        }
        return null;
    }

    private static ATFType getATFJavaPropertyType(ATFJavaClass owner, PsiField psiField) {
        List<PsiMethod> setters = PropertyUtil.getSetters((PsiClass) owner.getBasedOn(), psiField.getName());
        List<PsiMethod> getters = PropertyUtil.getGetters((PsiClass) owner.getBasedOn(), psiField.getName());
        // TODO: 1/2/2016 Find the property accessor and omit it if it's private or protected and doesn't have an
        // accessor
        return new ATFJavaProperty(owner, psiField, setters, getters);
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
    public ATFType getATFType(VirtualFile file) {
        PsiFile[] javaFiles = FilenameIndex.getFilesByName(getProject(), file.getName(), GlobalSearchScope
                .projectScope(getProject()));
        if (javaFiles.length > 0 && ((PsiJavaFile) javaFiles[0]).getClasses().length > 0) {
            return new ATFJavaClass(((PsiJavaFile) javaFiles[0]).getClasses()[0]);
        }
        return null;
    }

    @Override
    public ATFType getATFType(PsiFile psiFile) {
        if (psiFile.getFileType().equals(JavaFileType.INSTANCE) && ((PsiJavaFile) psiFile).getClasses().length > 0) {
            return new ATFJavaClass(((PsiJavaFile) psiFile).getClasses()[0]);

        }
        return null;
    }

    @Override
    public ATFType getATFType(String qualifiedName) {
        JavaPsiFacade instance = JavaPsiFacade.getInstance(getProject());
        PsiClass[] classes = instance.findClasses(qualifiedName, GlobalSearchScope.projectScope(getProject()));
        if (classes.length > 0) {
            return new ATFJavaClass(classes[0]);
        }
        return null;
    }

    @Override
    public ATFType createType(VirtualFile parent,  String name) {
        if (!parent.isDirectory()) {
            return null;
        }
        ATFType javaClass = null;
        PsiDirectoryFactory instance = PsiJavaDirectoryFactory.getInstance(project);
        final PsiDirectory psiDir = instance.createDirectory(parent);
        if (instance.isPackage(psiDir)) {
            try {
                return ExecutionUtil.execute(new ThrowableComputable() {
                    @Override
                    public ATFType compute() throws Throwable {
                        PsiPackage aPackage = JavaDirectoryService.getInstance().getPackage(psiDir);
                        String className = name;
                        PsiClass psiClass = JavaDirectoryService.getInstance().createClass(psiDir, className);
                        ATFJavaClass aClass = new ATFJavaClass(psiClass);
                        createConstructor(aClass, new Object[0]);
                        return aClass;
                    }
                });
            } catch (Throwable throwable) {
                Logger.getInstance(this.getClass()).error("Error creating class " + name, throwable.getMessage());
            }
        }
        return javaClass;
    }

    @Override
    public ATFMethod createMethod(@NotNull ATFClass parent, @NotNull String name, @Nullable ATFProperty... params) {
        return this.createMethod(parent, name, null, params);
    }

    @Override
    public ATFMethod createConstructor(@NotNull ATFClass parent, @Nullable Object... params) {
        PsiClassType stringType = PsiType.getJavaLangString(psiManager, GlobalSearchScope.projectScope(project));
        PsiParameter caller = elementFactory.createParameter("caller", stringType);
        PsiMethod method = elementFactory.createConstructor();
        method.getParameterList().add(caller);
        ATFMethod atfMethod = new ATFJavaMethod((ATFJavaClass) parent, method);
        parent.add(method);
        return atfMethod;
    }

    @Override
    public ATFProperty createProperty(@NotNull ATFClass parent, @NotNull Object param) {
        return null;
    }


    @Override
    public ATFMethod createMethod(@NotNull ATFClass parent, @NotNull String name, @Nullable ATFType returnType,
                                  @Nullable ATFProperty... params) {
        PsiType psiReturnType = PsiPrimitiveType.VOID;// FIXME: 1/7/2016 Different return types need to be handled.
        // This only handles Void.
        Computable<PsiMethod> callable = new Computable<PsiMethod>() {
            @Override
            public PsiMethod compute() {
                PsiMethod method = elementFactory.createMethod(name, psiReturnType);
                for (ATFProperty param : params) {
                    method.getParameterList().add(param.getBasedOn());
                }
                parent.add(method);
                return method;
            }
        };
        return new ATFJavaMethod((ATFJavaClass) parent,  ExecutionUtil.execute(callable));
    }

    private ATFMethod createMethod(ATFJavaClass parent, PsiMethod method) {
        return new ATFJavaMethod(parent, method);
    }

    public ATFJavaParameter getParameterFromProperty(ATFMethod parent,ATFProperty source){
        ATFParameter parameter = new ATFJavaParameter(parent,source.getName(),source.getType());
    }

}
