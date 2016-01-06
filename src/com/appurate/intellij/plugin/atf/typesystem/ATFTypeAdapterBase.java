package com.appurate.intellij.plugin.atf.typesystem;

import com.intellij.openapi.project.Project;

/**
 * Created by vmansoori on 1/3/2016.
 */
public abstract class ATFTypeAdapterBase implements ATFTypeAdapter {
    protected final Project project;

    protected ATFTypeAdapterBase(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return this.project;
    }
}
