package com.appurate.intellij.plugin.beanMapper.actions;

import com.intellij.lang.ASTNode;
import com.intellij.lang.FileASTNode;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.impl.source.tree.ElementType;
import com.intellij.psi.tree.TokenSet;

/**
 * Created by vmansoori on 12/29/2015.
 * // FIXME: 12/30/2015 This is a WIP.
 */
public class PSITestAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);
        PsiManager manager = PsiManager.getInstance(project);

        PsiFile psiFile = manager.findFile(e.getData(PlatformDataKeys.VIRTUAL_FILE));

        FileASTNode astNode = psiFile.getNode();
//        astNode.getChildren(new TokenSet());
        ASTNode childByType = astNode.findChildByType(ElementType.CLASS);


        Messages.showMessageDialog(project,childByType.getText(),psiFile.getName(),null);
    }
}
