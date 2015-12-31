package com.appurate.intellij.plugin.beanMapper.editor;

import com.guidewire.configcenter.editor.StudioEditor;
import com.guidewire.configcenter.view.IView;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

/**
 * Created by vmansoori on 12/27/2015.
 */
public class GTXEditor extends StudioEditor {
    public GTXEditor(Project project, VirtualFile file, String name) {
        super(project, file, name);
    }

    @Override
    protected IView createView() {
        return new GTXModellerView(this,this.getVirtualFile());
    }
}
