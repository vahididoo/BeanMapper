package com.appurate.intellij.plugin.common.editor;


import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorPolicy;
import com.intellij.openapi.fileEditor.FileEditorProvider;
import com.intellij.openapi.fileEditor.FileEditorState;
import com.intellij.openapi.fileEditor.impl.text.PsiAwareTextEditorImpl;
import com.intellij.openapi.fileEditor.impl.text.TextEditorProvider;
import com.intellij.openapi.fileEditor.impl.text.TextEditorState;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.vfs.VirtualFile;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;

/**
 * Created by vmansoori on 12/21/2015.
 */
public abstract class BaseEditorProvider implements FileEditorProvider, DumbAware {

    private final String editorId;
    private final String editorName;


    public BaseEditorProvider(String editorId, String editorName) {
        this.editorId = editorId;
        this.editorName = editorName;
    }

    @Override
    public boolean accept(@NotNull Project project, @NotNull VirtualFile virtualFile) {
        return this.acceptImpl(project, virtualFile);
    }

    protected abstract boolean acceptImpl(Project project, VirtualFile virtualFile);

    @NotNull
    @Override
    public FileEditor createEditor(@NotNull Project project, @NotNull VirtualFile virtualFile) {
        FileEditor editor;
        try {
            editor = this.createEditorImpl(project, virtualFile);
        } catch (Throwable t) {
            Messages.showMessageDialog(project, t.getMessage(), "Error creating " + this.editorName, null);
            editor = new PsiAwareTextEditorImpl(project, virtualFile, new TextEditorProvider());
        }
        return editor;
    }

    protected abstract FileEditor createEditorImpl(Project project, VirtualFile virtualFile);

    @Override
    public void disposeEditor(@NotNull FileEditor fileEditor) {
        Disposer.dispose(fileEditor, true);
    }

    @NotNull
    @Override
    public FileEditorState readState(@NotNull Element element, @NotNull Project project, @NotNull VirtualFile virtualFile) {
        return new TextEditorState();
    }

    @Override
    public void writeState(@NotNull FileEditorState fileEditorState, @NotNull Project project, @NotNull Element element) {

    }

    @NotNull
    @Override
    public String getEditorTypeId() {
        return this.editorId;
    }

    @NotNull
    @Override
    public FileEditorPolicy getPolicy() {
        return FileEditorPolicy.HIDE_DEFAULT_EDITOR;
    }
}
