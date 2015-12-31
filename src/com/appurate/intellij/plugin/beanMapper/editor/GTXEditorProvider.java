package com.appurate.intellij.plugin.beanMapper.editor;

import com.appurate.intellij.plugin.common.editor.BaseEditorProvider;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

/**
 * Created by vmansoori on 12/27/2015.
 */
public class GTXEditorProvider extends BaseEditorProvider {

    public GTXEditorProvider(){
        super("GTXEditor", "GTX Model Editor");
    }

    protected boolean acceptImpl(@NotNull Project project, @NotNull VirtualFile virtualFile) {
        String extension = virtualFile.getExtension();
        return extension != null ? extension.equals("gtx") : false;
    }

    @Override
    protected FileEditor createEditorImpl(Project project, VirtualFile virtualFile) {
        return new GTXEditor(project, virtualFile, "GTXEditor");
    }


}
