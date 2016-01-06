package com.appurate.intellij.plugin.atf.actions;

import com.appurate.intellij.plugin.atf.ExecutionUtil;
import com.appurate.intellij.plugin.atf.mapping.MappingFactoryManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.ThrowableComputable;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.impl.file.PsiDirectoryFactory;
import com.intellij.psi.impl.file.PsiJavaDirectoryFactory;
import com.intellij.psi.impl.java.stubs.PsiClassStub;
import com.intellij.psi.impl.java.stubs.impl.PsiClassStubImpl;
import com.intellij.psi.impl.source.PsiClassImpl;
import com.intellij.psi.util.PsiClassUtil;
import com.intellij.psi.util.PsiUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by vmansoori on 12/15/2015.
 */
public class NewATFModelAction extends AnAction {

    protected static final Logger LOG = Logger.getInstance(NewATFModelAction.class);
    private ATFXMLModel _xmlModel;

    public NewATFModelAction() {
        super("NewATFModelAction");
    }

    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();

        VirtualFile selection = e.getData(PlatformDataKeys.VIRTUAL_FILE);
        VirtualFile file = this.createATFModel(selection, e);
        createBindingClass(project,file);
        if (file != null) {
            FileEditorManager.getInstance(e.getProject()).openFile(file, true);
        }
    }

    private void createBindingClass(Project project, VirtualFile file) {
        MappingFactoryManager.getInstance(project).getFactory("java").createMapping(file.getParent(),file.getNameWithoutExtension());
    }

    private VirtualFile createATFModel(final VirtualFile selection, AnActionEvent e) {

        final NewATFDialog dialog = createAndShow();

        this._xmlModel = new ATFXMLModel(dialog.getSourceType(), dialog.getDestinationType());

        try {
            return ExecutionUtil.execute(new ThrowableComputable() {
                @Override
                public VirtualFile compute() throws Throwable {
                    File file = new File(selection.getPath(), dialog.getModelName() + ".atf");
                    file.getParentFile().mkdirs();
                    VirtualFile dir = LocalFileSystem.getInstance().findFileByIoFile(file.getParentFile());
                    dir.createChildData(this, file.getName());
                    VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByIoFile(file);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    NewATFModelAction.this._xmlModel.writeTo(outputStream);
                    Document document = FileDocumentManager.getInstance().getDocument(virtualFile);
                    document.setText(outputStream.toString("UTF-8"));
                    return virtualFile;
                }
            });
        } catch (Throwable throwable) {
            Logger.getInstance(this.getClass()).error("Error while creating a new ATF model.", throwable);
            return null;
        }
    }

    private NewATFDialog createAndShow() {
        NewATFDialog dialog = new NewATFDialog();
        dialog.pack();
        dialog.setVisible(true);
        return dialog;
    }

     public void updateImpl(AnActionEvent e) {
        VirtualFile selection = (VirtualFile) e.getData(PlatformDataKeys.VIRTUAL_FILE);
        boolean enabled = true; // TODO: 12/26/2015 Update the logic with real implementation
        Presentation presentation = e.getPresentation();
        presentation.setVisible(enabled);
        presentation.setEnabled(enabled);
    }
}
