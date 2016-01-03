package com.appurate.intellij.plugin.atf.actions;

import com.appurate.intellij.plugin.atf.editor.typesystem.ATFType;
import com.appurate.intellij.plugin.atf.editor.typesystem.ATFTypeAdapter;
import com.appurate.intellij.plugin.atf.editor.typesystem.ATFTypeManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;

/**
 * Created by vmansoori on 12/29/2015.
 * // FIXME: 12/30/2015 This is a WIP.
 */
public class PSITestAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);
        PsiManager manager = PsiManager.getInstance(project);

        VirtualFile virtualFile = e.getData(PlatformDataKeys.VIRTUAL_FILE);
        PsiFile psiFile = manager.findFile(virtualFile);


        ATFTypeAdapter adapter = ATFTypeManager.getInstance(project).getTypeAdapter("java");
        if (adapter.accept(virtualFile)) {
            ATFType ATFType = adapter.getGtxType(psiFile);
            StringBuilder sb = new StringBuilder(ATFType.getDisplayName()).append("\r\nnChildren:\r\n");
            createPresentationString(ATFType, sb);
            Messages.showMessageDialog(project, sb.toString(), psiFile.getName(), null);
        }


    }

    private void createPresentationString(ATFType ATFType, StringBuilder sb) {
        for (ATFType type : ATFType.getChildren()) {
            sb.append(type.getDisplayName()).append("\r\n");
            createPresentationString(type,sb);
        }
    }
}
