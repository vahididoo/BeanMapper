package com.appurate.intellij.plugin.atf.editor.tree;

import com.appurate.intellij.plugin.atf.typesystem.ATFType;
import com.intellij.icons.AllIcons;
import com.intellij.ui.JBDefaultTreeCellRenderer;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * Created by vmansoori on 1/3/2016.
 */
public class ATFTreeCellRenderer extends JBDefaultTreeCellRenderer {

    public ATFTreeCellRenderer(@NotNull JTree tree) {
        super(tree);
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean
            leaf, int row, boolean hasFocus) {
        Component component = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
                row, hasFocus);

        if (value instanceof ATFTreeNode) {
            ATFType rootType = ((ATFTreeNode) value).getBasedOn();
            JLabel label = (JLabel) component;
            if (rootType.getChildren().length == 0) {
                label.setIcon(AllIcons.Nodes.Field);

            } else {
                label.setIcon(AllIcons.Nodes.Class);
                label.setEnabled(false);
                this.setEnabled(false);
            }
        }

        return component;
    }
}
