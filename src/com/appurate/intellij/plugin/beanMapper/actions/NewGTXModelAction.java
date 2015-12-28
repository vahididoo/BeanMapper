package com.appurate.intellij.plugin.beanMapper.actions;

import com.appurate.intellij.plugin.beanMapper.ExecutionUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.util.ThrowableComputable;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by vmansoori on 12/15/2015.
 */
public class NewGTXModelAction extends AnAction {

    protected static final Logger LOG = Logger.getInstance(NewGTXModelAction.class);
    private GTXXMLModel _xmlModel;

    public NewGTXModelAction() {
        super("NewGTXModelAction");
    }

    public void actionPerformed(AnActionEvent e) {
        VirtualFile selection = (VirtualFile) e.getData(PlatformDataKeys.VIRTUAL_FILE);
        VirtualFile file = this.createGTXModel(selection, e);
        if (file != null) {
            FileEditorManager.getInstance(e.getProject()).openFile(file, true);
        }

    }

    private VirtualFile createGTXModel(final VirtualFile selection, AnActionEvent e) {

        final NewGTXDialog dialog = createAndShow();

        this._xmlModel = new GTXXMLModel(dialog.getSourceType(), dialog.getDestinationType());

        try {
            return ExecutionUtil.execute(new ThrowableComputable() {
                @Override
                public VirtualFile compute() throws Throwable {
                    File file = new File(selection.getPath(), dialog.getModelName() + ".gtx");
                    file.getParentFile().mkdirs();
                    VirtualFile dir = LocalFileSystem.getInstance().findFileByIoFile(file.getParentFile());
                    dir.createChildData(this, file.getName());
                    VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByIoFile(file);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    NewGTXModelAction.this._xmlModel.writeTo(outputStream);
                    Document document = FileDocumentManager.getInstance().getDocument(virtualFile);
                    document.setText(outputStream.toString("UTF-8"));
                    return virtualFile;
                }
            });
        } catch (Throwable throwable) {
            Logger.getInstance(this.getClass()).error("Error while creating a new GTX model.", throwable);
            return null;
        }
    }

    private NewGTXDialog createAndShow() {
        NewGTXDialog dialog = new NewGTXDialog();
        dialog.pack();
        dialog.setVisible(true);
        return dialog;
    }

 /*   protected VirtualFile createGXModel(final VirtualFile selection, AnActionEvent e) {
//        NewGXModelDialog dlg = new NewGXModelDialog(module, selection);
//        dlg.setVisible(true);
//        if(dlg.isOk()) {
            final String modelName = "sample_model";
//            final IType entityType = dlg.getEntity();
            if(modelName != null *//*&& entityType != null*//*) {
                return (VirtualFile) ExecutionUtil.execute(7, new SafeCallable(module) {
                    public VirtualFile execute() throws Exception {
                        GTXModellerXMLModel visualModel = new GTXModellerXMLModel();
                        visualModel.setSource("enityt.Policy");
                        visualModel.setDestination("entity.Account");
                        File file = new File(selection.getPath(), modelName + ".gtx");
                        file.getParentFile().mkdirs();
                        VirtualFile dir = StudioUtilities.getVirtualFile(file.getParentFile());
                        dir.createChildData(this, file.getName());
                        VirtualFile virtualFile = StudioUtilities.getVirtualFile(file);
                        ByteArrayOutputStream stringWriter = new ByteArrayOutputStream();
                        visualModel.writeTo(stringWriter);
                        Document document = FileDocumentManager.getInstance().getDocument(virtualFile);
                        document.setText(stringWriter.toString("UTF-8"));
                        FileDocumentManager.getInstance().saveDocument(document);
                        return virtualFile;
                    }
                });
            }
//        }

        return null;
    }*/

    public void updateImpl(AnActionEvent e) {
        VirtualFile selection = (VirtualFile) e.getData(PlatformDataKeys.VIRTUAL_FILE);
        boolean enabled = true; // TODO: 12/26/2015 Update the logic with real implementation
        Presentation presentation = e.getPresentation();
        presentation.setVisible(enabled);
        presentation.setEnabled(enabled);
    }
}
