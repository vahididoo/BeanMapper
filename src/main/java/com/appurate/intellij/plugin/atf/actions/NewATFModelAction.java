package com.appurate.intellij.plugin.atf.actions;

import com.appurate.intellij.plugin.atf.ExecutionUtil;
import com.intellij.openapi.actionSystem.*;
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

        VirtualFile selection = e.getData(DataKeys.VIRTUAL_FILE);
        VirtualFile file = this.createATFModel(selection, project);

        if (file != null) {
            FileEditorManager.getInstance(e.getProject()).openFile(file, true);
        }
    }

//    private Mapping createBindingClass(Project project, VirtualFile parent, VirtualFile file) throws
//            JClassAlreadyExistsException {
//        PsiPackage aPackage = JavaDirectoryService.getInstance().getPackage(PsiDirectoryFactory.getInstance(project)
//                .createDirectory(parent));
//        return new Mapping(aPackage.getQualifiedName(), file.getNameWithoutExtension());
//
//    }

    private VirtualFile createATFModel(final VirtualFile selection, final Project project) {

        final NewATFDialog dialog = createAndShow();


        try {
            String modelName = dialog.getModelName();
            File file = new File(selection.getPath(), modelName + ".atf");
            file.getParentFile().mkdirs();
            VirtualFile dir = LocalFileSystem.getInstance().findFileByIoFile(file.getParentFile());
            dir.createChildData(this, file.getName());
            VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByIoFile(file);
            this._xmlModel = new ATFXMLModel(virtualFile,dialog.getSourceType(), dialog.getDestinationType());
            return _xmlModel.write();
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
