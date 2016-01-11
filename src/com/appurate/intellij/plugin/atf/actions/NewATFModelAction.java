package com.appurate.intellij.plugin.atf.actions;

import com.appurate.intellij.plugin.atf.ExecutionUtil;
import com.appurate.intellij.plugin.atf.typesystem.ATFTypeFactory;
import com.appurate.intellij.plugin.atf.typesystem.psi.ATFPsiClass;
import com.appurate.intellij.plugin.atf.typesystem.ATFTypeManager;
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
        VirtualFile file = this.createATFModel(selection, project);

        if (file != null) {
            FileEditorManager.getInstance(e.getProject()).openFile(file, true);
        }
    }

    private ATFPsiClass createBindingClass(Project project, VirtualFile file) {
        ATFTypeFactory typeFactory = ATFTypeManager.getInstance(project).getTypeFactory("java");
        ATFPsiClass psiClass = (ATFPsiClass) typeFactory.createType(file
                .getParent(), file
                .getNameWithoutExtension());

        return psiClass;
    }

    private VirtualFile createATFModel(final VirtualFile selection, final Project project) {

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
                    ATFPsiClass mappingClass = createBindingClass(project, virtualFile);
                    _xmlModel.setBindingClass(mappingClass);
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
