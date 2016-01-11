package com.appurate.intellij.plugin.atf.typesystem;


import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by vmansoori on 1/5/2016.
 */
public interface ATFTypeFactory {

    boolean accept(String extension);

    boolean accept(VirtualFile file);

    boolean accept(PsiFile psiFile);

    ATFType getATFType(VirtualFile file);

    ATFType getATFType(PsiFile psiFile);

    ATFType getATFType(String qualifiedName);

    ATFType createType(VirtualFile parent, String name);

    ATFMethod createMethod(@NotNull ATFClass parent, @NotNull String name, @Nullable ATFProperty... params);

    ATFMethod createConstructor(@NotNull ATFClass parent, @Nullable Object... params);

    ATFProperty createProperty(@NotNull ATFClass parent, @NotNull Object param);

    ATFMethod createMethod(@NotNull ATFClass parent, @NotNull String name, @Nullable ATFType returnType,
                           @Nullable ATFProperty... params);
}
