package com.appurate.intellij.plugin.atf.typesystem;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElementFactory;

/**
 * Created by vmansoori on 1/3/2016.
 */
public abstract class ATFTypeFactoryBase implements ATFTypeFactory {
    protected final Project project;

    protected ATFTypeFactoryBase(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return this.project;
    }


    @Override
    public boolean accept(VirtualFile file) {
        return file != null && accept(file.getExtension());
    }
}
