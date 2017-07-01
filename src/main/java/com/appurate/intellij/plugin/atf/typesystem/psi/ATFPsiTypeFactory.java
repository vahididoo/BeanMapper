package com.appurate.intellij.plugin.atf.typesystem.psi;

import com.appurate.intellij.plugin.atf.typesystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by vmansoori on 3/18/2016.
 */
public abstract class ATFPsiTypeFactory extends ATFTypeFactoryBase {
    protected ATFPsiTypeFactory(Project project) {
        super(project);
    }

    @Override
    public boolean accept(String extension) {
        return false;
    }

    @Override
    public boolean accept(VirtualFile file) {
        return false;
    }

    @Override
    public boolean accept(PsiFile psiFile) {
        return false;
    }


    @Override
    public abstract ATFType getATFType(String qualifiedName);


    @Override
    public ATFMethod createMethod(@NotNull ATFClass parent, @NotNull String name, @Nullable ATFParameter... params) {
        return null;
    }

    @Override
    public ATFMethod createConstructor(@NotNull ATFClass parent, @Nullable Object... params) {
        return null;
    }

    @Override
    public ATFProperty createProperty(@NotNull ATFClass parent, @NotNull Object param) {
        return null;
    }

    @Override
    public ATFMethod createMethod(@NotNull ATFClass parent, @NotNull String name, @Nullable ATFType returnType, @Nullable ATFParameter... params) {
        return null;
    }
}
