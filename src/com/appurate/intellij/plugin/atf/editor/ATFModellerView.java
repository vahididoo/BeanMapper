package com.appurate.intellij.plugin.atf.editor;

import com.appurate.intellij.plugin.atf.actions.ATFXMLModel;
import com.appurate.intellij.plugin.atf.editor.tree.ATFSingleSelecetionModel;
import com.appurate.intellij.plugin.atf.editor.tree.ATFTreeCellRenderer;
import com.appurate.intellij.plugin.atf.editor.tree.ATFTreeNode;
import com.appurate.intellij.plugin.atf.editor.tree.ATFTreeSelectionListener;
import com.appurate.intellij.plugin.atf.typesystem.ATFType;
import com.appurate.intellij.plugin.atf.typesystem.ATFTypeAdapter;
import com.appurate.intellij.plugin.atf.typesystem.ATFTypeManager;
import com.guidewire.configcenter.editor.StudioEditor;
import com.guidewire.configcenter.view.AbstractView;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.treeStructure.Tree;
import gw.lang.reflect.ITypeLoaderListener;
import gw.lang.reflect.RefreshRequest;
import gw.lang.reflect.module.IModule;
import gw.plugin.ij.filesystem.IDEAFile;
import gw.plugin.ij.util.ExecutionUtil;
import gw.plugin.ij.util.SafeCallable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;

/**
 * Created by vmansoori on 12/17/2015.
 */
public class ATFModellerView extends AbstractView implements ITypeLoaderListener {
    private IDEAFile file;
    private ATFXMLModel xmlModel;
    private JPanel mainPanel;
    private Tree sourceTree;
    private Tree destinationTree;
    private JButton button1;
    private JButton button2;
    private JButton button3;

    private ATFType destinationATFType;
    private ATFType sourceATFType;
    private JPanel destinationPanel;
    private JPanel actionPanel;
    private JPanel sourcePanel;


    public ATFModellerView(StudioEditor editor, VirtualFile file) {
        super(editor, false);
        this.file = new IDEAFile(file);
        try {
            xmlModel = ATFXMLModel.readFile(file);
            final IModule module = this.getModule();
            ExecutionUtil.execute(new SafeCallable(module) {
                public Object execute() {
                    ATFModellerView.this.init();
                    return null;
                }
            });
        } catch (Exception e) {
            Logger.getInstance(this.getClass()).error("Cannot create the XMLModel due to exception when marshalling " +
                    "the file", e.getMessage());
        }
    }

    @Override
    public void init() {
        super.init();


    }

    private void initializeModel() {
        String sourceType = xmlModel.getSourceType();
        String destinationType = xmlModel.getDestinationType();

        ATFTypeAdapter typeAdapter = ATFTypeManager.getInstance(this.getProject()).getTypeAdapter("java");

        sourceATFType = typeAdapter.getATFType(sourceType);
        destinationATFType = typeAdapter.getATFType(destinationType);
    }

    @Override
    protected JComponent generateMainPanel() {

        initializeModel();
        this.mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        createActionPanel();
        createSourcePanel();
        createDestinationPanel();

        this.mainPanel.add(this.sourcePanel, BorderLayout.EAST);
        this.mainPanel.add(this.actionPanel, BorderLayout.CENTER);
        this.mainPanel.add(this.destinationPanel, BorderLayout.WEST);

        registerModelListener();

        return this.mainPanel;
    }

    private void createDestinationPanel() {
        destinationPanel = new JPanel();
        this.destinationTree = new Tree(new ATFTreeNode(destinationATFType));
        this.destinationTree.setCellRenderer(new ATFTreeCellRenderer(this.destinationTree));
        this.destinationTree.setSelectionModel(new ATFSingleSelecetionModel());
        this.destinationTree.setRootVisible(true);
        this.destinationTree.setShowsRootHandles(false);
        destinationPanel.add(new JBScrollPane(this.destinationTree));
    }

    private void createSourcePanel() {
        sourcePanel = new JPanel();
        this.sourceTree = new Tree(new ATFTreeNode(sourceATFType));
        this.sourceTree.setCellRenderer(new ATFTreeCellRenderer(this.sourceTree));
        this.sourceTree.setRootVisible(true);
        this.sourceTree.setShowsRootHandles(false);
        sourcePanel.add(new JBScrollPane(this.sourceTree), "Center");
    }

    private void registerModelListener() {
        ATFTreeSelectionListener sourceSelectionListener = new ATFTreeSelectionListener();
        ATFTreeSelectionListener destinationSelectionListener = new ATFTreeSelectionListener();

        this.sourceTree.getSelectionModel().addTreeSelectionListener(sourceSelectionListener);
        this.destinationTree.getSelectionModel().addTreeSelectionListener(destinationSelectionListener);
    }


    private void createActionPanel() {
        actionPanel = new JPanel();
        actionPanel.add(button1);
        actionPanel.add(button2);
        actionPanel.add(button3);
    }

    @Override
    public void commitChanges() throws IOException {

    }

    @Override
    public void dispose() {

    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public Icon getIcon(int i) {
        return null;
    }

    @Override
    public void refreshedTypes(RefreshRequest refreshRequest) {

    }

    @Override
    public void refreshed() {

    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
