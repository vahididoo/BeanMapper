package com.appurate.intellij.plugin.atf.editor.tree;

import com.appurate.intellij.plugin.atf.typesystem.ATFType;

import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreePath;

/**
 * Created by vmansoori on 1/3/2016.
 */
public class ATFTreeSelecetionModel extends DefaultTreeSelectionModel {

    public ATFTreeSelecetionModel(boolean singleSelectionMode) {
        super();
        if (singleSelectionMode) {
            this.setSelectionMode(SINGLE_TREE_SELECTION);
        } else {
            this.setSelectionMode(DISCONTIGUOUS_TREE_SELECTION);
        }

    }

    @Override
    public void addSelectionPath(TreePath treePath) {

        if (((ATFTreeNode) treePath.getLastPathComponent()).getBasedOn().getChildren().length == 0) {
            super.addSelectionPath(treePath);
        } else {
            super.removeSelectionPath(treePath);
        }
    }

    @Override
    public void addSelectionPaths(TreePath[] treePaths) {
        if (treePaths.length > 0 && ((ATFTreeNode) treePaths[treePaths.length - 1].getLastPathComponent()).getBasedOn
                ().getChildren().length == 0) {
            super.addSelectionPaths(treePaths);
        } else {
            super.removeSelectionPaths(treePaths);
        }
    }


}
