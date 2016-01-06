package com.appurate.intellij.plugin.atf.editor.tree;


import com.appurate.intellij.plugin.atf.typesystem.ATFType;
import com.guidewire.configcenter.metadata.DefaultTreeNode;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Vector;

/**
 * Created by vmansoori on 1/3/2016.
 */
public class ATFTreeNode extends DefaultTreeNode {

    private ATFType basedOn;

    public ATFTreeNode(ATFType basedOn) {
        this.basedOn = basedOn;
    }

    @Override
    protected Vector<? extends DefaultTreeNode> doInitChildren() {
        Vector<ATFTreeNode> children = new Vector();
        for (ATFType child : basedOn.getChildren()) {
            children.add(new ATFTreeNode(child));
        }
        return children;
    }

    @Override
    public String toString() {
        return basedOn.getDisplayName();
    }

    public ATFType getBasedOn() {
        return basedOn;
    }
}
