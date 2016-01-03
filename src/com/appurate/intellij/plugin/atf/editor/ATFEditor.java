package com.appurate.intellij.plugin.atf.editor;

import com.guidewire.configcenter.editor.StudioEditor;
import com.guidewire.configcenter.view.IView;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

/**
 * Created by vmansoori on 12/27/2015.
 */
public class ATFEditor extends StudioEditor {
    public ATFEditor(Project project, VirtualFile file, String name) {
        super(project, file, name);
    }

    @Override
    protected IView createView() {
        return new ATFModellerView(this,this.getVirtualFile());
    }
}