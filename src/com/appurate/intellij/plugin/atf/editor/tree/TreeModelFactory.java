package com.appurate.intellij.plugin.atf.editor.tree;

import com.appurate.intellij.plugin.atf.editor.typesystem.ATFType;
import org.jetbrains.annotations.NotNull;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * Created by vmansoori on 1/3/2016.
 */
public class TreeModelFactory {
    private static TreeModelFactory ourInstance = new TreeModelFactory();

    public static TreeModelFactory getInstance() {
        return ourInstance;
    }

    private TreeModelFactory() {
    }

    public DefaultTreeModel createTreeModel(ATFType rootType){
        DefaultMutableTreeNode rootNode = createTreeNode(rootType);
        DefaultTreeModel treeModel = new DefaultTreeModel(rootNode); // FIXME: 1/3/2016
        return treeModel;
    }

    @NotNull
    private DefaultMutableTreeNode createTreeNode(ATFType rootType) {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(rootType);

        return node;
    }
}
