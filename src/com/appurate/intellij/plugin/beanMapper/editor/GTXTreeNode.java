package com.appurate.intellij.plugin.beanMapper.editor;

import com.appurate.intellij.plugin.beanMapper.editor.typesystem.GTXType;
import com.guidewire.configcenter.metadata.DefaultTreeNode;

import java.util.Vector;

/**
 * Created by vmansoori on 12/30/2015.
 */
public class GTXTreeNode extends DefaultTreeNode {

    private GTXType.GTXProperty _basedOn;

    public GTXTreeNode(GTXType.GTXProperty inputType) {
        super();
        this._basedOn = inputType;
    }

    @Override
    protected Vector<? extends DefaultTreeNode> doInitChildren() {

        return null;
    }
}
